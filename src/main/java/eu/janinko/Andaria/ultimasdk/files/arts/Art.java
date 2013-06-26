package eu.janinko.Andaria.ultimasdk.files.arts;

import eu.janinko.Andaria.ultimasdk.files.graphics.Bitmap;

/**
 * @author janinko
 */
public class Art {
	private int flag;
	private short width;
	private short height;
	private Bitmap bitmap;

	public Art(byte[] data) {
		flag = (0xff & data[0]) + ((0xff & data[1]) << 8) + ((0xff & data[2]) << 16) + ((0xff & data[3]) << 24);
		if(flag > 0xFFFF || flag == 0){ // raw
			loadRaw(data);
		}else{ // run
			loadRun(data);
		}
	}

	private void loadRaw(byte[] data) {
		width = 44;
		height = 44;
		bitmap = new Bitmap(width, height);

		int i = 4;
		for(int x=0; x<width; x++) for(int y=0; y<height; y++){
			bitmap.getPoint(x, y).setColor((short) 0x8000);
		}
		for(int x=0; x < 22; x++){
			for(int y=21-x; y <= 22+x; y++){
				bitmap.getPoint(x, y).setColor((short) ((0xff & data[i++]) + ((0xff & data[i++]) << 8)));
			}
		}
		for(int x=22; x < 44; x++){
			for(int y=x-22; y <= 65-x; y++){
				bitmap.getPoint(x, y).setColor((short) ((0xff & data[i++]) + ((0xff & data[i++]) << 8)));
			}
		}
	}

	private void loadRun(byte[] data) {
		width = (short) ((0xff & data[4]) + ((0xff & data[5]) << 8));
		height = (short) ((0xff & data[6]) + ((0xff & data[7]) << 8));
		bitmap = new Bitmap(width, height);
		bitmap.readGraphics(8, data);
	}

	public Bitmap getImage(){
		return bitmap;
	}
}
