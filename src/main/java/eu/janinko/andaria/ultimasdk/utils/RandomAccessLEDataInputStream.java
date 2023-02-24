
package eu.janinko.andaria.ultimasdk.utils;

import java.io.InputStream;

/**
 * @author janinko
 */
public class RandomAccessLEDataInputStream extends LittleEndianDataInputStream{

    public RandomAccessLEDataInputStream(byte[] data) {
        super(new RandomAccessInputStream(data));
    }

    public void seek(int pos){
        if(pos < 0 || pos >= ((RandomAccessInputStream) this.in).data.length)
            throw new ArrayIndexOutOfBoundsException();
        ((RandomAccessInputStream) this.in).pos = pos;
    }

    public int getPosition(){
        return ((RandomAccessInputStream) this.in).pos;
    }

    private static class RandomAccessInputStream extends InputStream{
        private int pos = 0;
        private byte[] data;

        public RandomAccessInputStream(byte[] data) {
            this.data = data;
        }

        @Override
        public int read(){
            if(pos >= data.length) return -1;
            return data[pos++] & 0xff;
        }

        @Override
        public int read(byte[] b, int off, int len){
            if(pos >= data.length) return -1;
            if(pos + len > data.length){
                len = data.length - pos;
            }
            for(int end = off + len; off < end; off++){
                b[off] = data[pos++];
            }
            return len;
        }

    }
}
