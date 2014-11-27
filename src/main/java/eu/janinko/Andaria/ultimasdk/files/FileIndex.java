package eu.janinko.Andaria.ultimasdk.files;

import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataOutputStream;
import eu.janinko.Andaria.ultimasdk.utils.RandomAccessLEDataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class FileIndex {
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

	public void save(OutputStream idxStream, OutputStream mulFile) throws IOException {
		LittleEndianDataOutputStream out = new LittleEndianDataOutputStream(idxStream);
		int offset = 0;
		for(int i=0; i<index.size(); i++){
			offset += saveDatum(i, offset, out, mulFile);
		}
	}
	
	private int saveDatum(int i, int offset, LittleEndianDataOutputStream out, OutputStream mulFile) throws IOException {
		Entry3D entry = index.get(i);
		if (entry.length == 0 || entry.length == -1) {
				if(-1 != entry.offset){
					System.out.println(offset + " : " + entry.offset);
				}
			out.writeInt(-1);
		} else {
				if(offset != entry.offset){
					System.out.println(offset + " : " + entry.offset);
				}
			out.writeInt(offset);
		}
		out.writeInt(entry.length);
		out.writeInt(entry.extra);
		DataPack data = getData(i);
		if (data == null) {
			return 0;
		}
		mulFile.write(data.data);
		return data.data.length;
	}

	public void save(OutputStream idxStream, OutputStream mulFile, int id, DataPack data) throws IOException {
		LittleEndianDataOutputStream out = new LittleEndianDataOutputStream(idxStream);
		int offset = 0;
		for(int i=0; i<index.size(); i++){
			if(i == id){
				if(data.data.length == 0){
					out.writeInt(-1);
					out.writeInt(0);
					out.writeInt(0);
				}else{
					out.writeInt(offset);
					out.writeInt(data.data.length);
					out.writeInt(data.extra);
					mulFile.write(data.data);
					offset += data.data.length;
				}
			}else{
				offset += saveDatum(i, offset, out, mulFile);
			}
		}
		
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
