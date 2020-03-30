package eu.janinko.Andaria.ultimasdk.graphics;

import java.io.IOException;

import eu.janinko.Andaria.ultimasdk.utils.RandomAccessLEDataOutputStream;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class BitmapWriter {
    
    public static byte[] writeColorLines(BasicBitmap bitmap) throws IOException{
		RandomAccessLEDataOutputStream data = new RandomAccessLEDataOutputStream();
		
		int start = 4*bitmap.getHeight();
		for(int y=0; y<bitmap.getHeight(); y++){
			data.seek(y*4);
			data.writeInt(start / 4);
			data.seek(start);
			Color previous = null;
			int repeat = 1;
			for(int x = 0; x < bitmap.getWidth(); x++){
				Color act = bitmap.getColor(x, y);
				if(!act.equals(previous)){
					if(previous != null){
						data.writeShort(repeat);
						start += 2;
					}
					previous = act;
					data.writeShort(act.getColor());
					start += 2;
					repeat = 1;
				}else{
					repeat++;
				}
			}
			data.writeShort(repeat);
			start += 2;
		}
		return data.getArray();
	}
    
    public static byte[] writeColorChunks(BasicBitmap bitmap) throws IOException{
		RandomAccessLEDataOutputStream data = new RandomAccessLEDataOutputStream();
        int dstart = bitmap.getHeight() * 2;
        data.seek(dstart);
        for(int y=0; y<bitmap.getHeight(); y++){
            int pos = data.getPosition();
            int start = (pos - dstart) / 2;
            if(start > 0xffff) throw new RuntimeException("Picture is too big to save");
            data.seek(y*2);
            data.writeShort(start);
            data.seek(pos);
            
            
            int offset=0;
            int run=0;
            boolean writing = false;
            boolean offseting = true;
            for(int x=0; x<bitmap.getWidth(); x++){
                if(bitmap.getColor(x, y).isAlpha() || Color.WHITE.equals(bitmap.getColor(x, y))){
                    if(!offseting){
                        int pos2 = data.getPosition();
                        data.seek(pos);
                        data.writeShort(run);
                        data.seek(pos2);
                        run=0;
                        writing = false;
                        offseting = true;
                    }
                }else{
                    if(!writing){
                        data.writeShort(offset);
                        pos = data.getPosition();
                        data.seek(pos + 2);
                        offset=0;
                        writing = true;
                        offseting = false;
                    }
                }
                if(offseting){
                    offset++;
                }
                if(writing){
                    run++;
                    data.writeShort(bitmap.getColor(x, y).getColor());
                }
            }
            if (writing) {
                int pos2 = data.getPosition();
                data.seek(pos);
                data.writeShort(run);
                data.seek(pos2);
            }
            data.writeShort(0);
            data.writeShort(0);
        }
        return data.getArray();
    }
}
