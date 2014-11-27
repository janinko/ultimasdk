
package eu.janinko.Andaria.ultimasdk.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * @author janinko
 */
public class RandomAccessLEDataOutputStream extends LittleEndianDataOutputStream{

	public RandomAccessLEDataOutputStream() {
		super(new RandomAccessOutputStream());
	}

	public void seek(int pos){
		if(pos < 0)
			throw new ArrayIndexOutOfBoundsException();
		((RandomAccessOutputStream) this.out).pos = pos;
	}

	public int getPosition(){
		return ((RandomAccessOutputStream) this.out).pos;
	}

	public byte[] getArray() {
		ArrayList<Byte> data = ((RandomAccessOutputStream) this.out).data;
		byte[] out = new byte[data.size()];
		for(int i=0; i< data.size(); i++){
			out[i] = data.get(i);
		}
		return out;
	}

	private static class RandomAccessOutputStream extends OutputStream{
		private int pos = 0;
		private ArrayList<Byte> data = new ArrayList<Byte>();

		@Override
		public void write(int b) throws IOException {
			if(pos >= data.size()){
				data.ensureCapacity(pos+1);
				while(pos >= data.size()){
					data.add((byte) 0);
				}
			}
			data.set(pos++, (byte) (b & 0xff));
		}
	}
}
