package eu.janinko.Andaria.ultimasdk.files;

import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.Andaria.ultimasdk.utils.RandomAccessLEDataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
class FileIndex {
	private ArrayList<Entry3D> index;

	private RandomAccessFile mulData;

	public FileIndex(InputStream idxStream, File mulFile, int length) throws IOException{
		index = new ArrayList<Entry3D>(length);

		LittleEndianDataInputStream idxData = new LittleEndianDataInputStream(idxStream);
		mulData= new RandomAccessFile(mulFile,"r");

		boolean reading = true;
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

	public FileIndex(int blockSize, File mulFile, int length) throws IOException{
		index = new ArrayList<Entry3D>(length);

		mulData= new RandomAccessFile(mulFile,"r");

		for(int i=0; i < length; i++){
			index.add(new Entry3D(i*blockSize, blockSize, 0));
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

	@Override
	public String toString() {
		int i = 0;
		StringBuilder sb = new StringBuilder();
		for(Entry3D e : index){
			sb.append(i++).append(": ").append(e).append('\n');
		}
		return sb.toString();
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

		public RandomAccessLEDataInputStream getNewStream(){
			return new RandomAccessLEDataInputStream(data);
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

		@Override
		public String toString() {
			return "Entry3D{" + "offset=" + offset + ", length=" + length + ", extra=" + extra + '}';
		}
	}

}
