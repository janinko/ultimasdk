package eu.janinko.Andaria.ultimasdk.files.arts;

import eu.janinko.Andaria.ultimasdk.utils.RandomAccessLEDataInputStream;
import eu.janinko.Andaria.ultimasdk.files.graphics.Bitmap;
import eu.janinko.Andaria.ultimasdk.files.graphics.Color;
import eu.janinko.Andaria.ultimasdk.files.graphics.Image;
import java.io.IOException;

/**
 * @author janinko
 */
public class Art extends Image{
	private int flag;
	private int width;
	private int height;

	public Art(RandomAccessLEDataInputStream data) throws IOException {
		flag = data.readInt();
		if(flag > 0xFFFF || flag == 0){
			loadRaw(data);
		}else{
			loadRun(data);
		}
	}

	private void loadRaw(RandomAccessLEDataInputStream data) throws IOException {
		width = 44;
		height = 44;
		bitmap = new Bitmap(width, height);

		for(int x=0; x<width; x++) for(int y=0; y<height; y++){
			bitmap.getPoint(x, y).setColor(Color.ALPHA);
		}
		for(int x=0; x < 22; x++){
			for(int y=21-x; y <= 22+x; y++){
				bitmap.getPoint(x, y).setColor(data.readShort());
			}
		}
		for(int x=22; x < 44; x++){
			for(int y=x-22; y <= 65-x; y++){
				bitmap.getPoint(x, y).setColor(data.readShort());
			}
		}
	}

	private void loadRun(RandomAccessLEDataInputStream data) throws IOException {
		width = data.readUnsignedShort();
		height = data.readUnsignedShort();
		bitmap = new Bitmap(width, height);
		bitmap.readColorChunks(data);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
