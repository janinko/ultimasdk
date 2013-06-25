package eu.janinko.Andaria.ultimasdk.files;

import eu.janinko.Andaria.ultimasdk.LittleEndianDataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class FileIndex {
	ArrayList<Entry3D> index;
	File idxFile;
	File mulFile;

	RandomAccessFile mulData;

	public FileIndex(File idxFile, File mulFile, int length) throws FileNotFoundException, IOException{
		index = new ArrayList<Entry3D>(length);
		this.idxFile = idxFile;
		this.mulFile = mulFile;

		LittleEndianDataInputStream idxData = new LittleEndianDataInputStream(new FileInputStream(idxFile));
		mulData= new RandomAccessFile(mulFile,"r");

		boolean reading = true;
		int i=0;
		while(reading){
			try{
				int offset = idxData.readInt();
				int len = idxData.readInt();
				int extra = idxData.readInt();
				index.add(new Entry3D(offset, len, extra));
			}catch(EOFException ex){
				reading = false;
			}
		}
	}

	public DataPack getData(int i) throws IOException{
		Entry3D entry = index.get(i);
		if(entry.length <= 0) return null;
		byte[] data = new byte[entry.length];
		mulData.seek(entry.offset);
		mulData.readFully(data);
		return new DataPack(data, entry.extra);
	}

	public static class DataPack{
		private byte[] data;
		private int extra;

		public DataPack(byte[] data, int extra) {
			this.data = data;
			this.extra = extra;
		}

		public byte[] getData() {
			return data;
		}

		public int getExtra() {
			return extra;
		}
	}

	private static class Entry3D{
		private int offset;
		private int length;
		private int extra;

		public Entry3D(int offset, int length, int extra) {
			this.offset = offset;
			this.length = length;
			this.extra = extra;
		}
	}

}
