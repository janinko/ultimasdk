package eu.janinko.Andaria.ultimasdk.files.graphics;

import eu.janinko.Andaria.ultimasdk.Utils;
import eu.janinko.Andaria.ultimasdk.files.hues.Hue;
import java.awt.image.BufferedImage;

/**
 * @author janinko
 */
public class Bitmap {
	private int width;
	private int height;
	private short[][] bitmap;
	
	public Bitmap(int width, int height){
		if(width < 0) throw new IllegalArgumentException("Width can't be less then zero, is " + width);
		if(height < 0) throw new IllegalArgumentException("Height can't be less then zero, is " + height);
		
		this.bitmap = new short[width][height];
		this.width = width;
		this.height = height;

		for(int x=0; x<width; x++) for(int y=0; y<height; y++){
			bitmap[x][y] = (short) 0x8000;
		}
	}

	public void readGraphics(byte[] data){
		int pos = 0;
		int[] starts = new int[height];

		for(int i=0; i<height; i++){
			starts[i] = ((0xff & data[pos++]) + ((0xff & data[pos++]) << 8) + ((0xff & data[pos++]) << 16) + ((0xff & data[pos++]) << 24))*4;
			//System.out.println("starts[" + i + "] = " + starts[i]);
		}

		for(int y = 0; y < height; y++){
			int x = 0;
			pos = starts[y];
			//System.out.println("pos: " + pos);
			while(x < width){
				int color = (0xff & data[pos++]) + ((0xff & data[pos++]) << 8);
				int repeat = (0xff & data[pos++]) + ((0xff & data[pos++]) << 8);
				//System.out.println("color: " + color + " repeat:" + repeat);
				for(int i=0; i < repeat; i++){
					bitmap[x++][y] = (short) color;
				}
			}
		}
	}

	public void readGraphics(int pos, byte[] data){
		int[] starts = new int[height];

		for(int i=0; i<height; i++){
			starts[i] = ((0xff & data[pos++]) + ((0xff & data[pos++]) << 8))*2;
			//System.out.println("starts[" + i + "] = " + starts[i]);
		}
		int dstart = pos;

		int y = 0;
		int x = 0;

		pos = starts[y] + dstart;
		while(y < height){
			short offset = (short) ((0xff & data[pos++]) + ((0xff & data[pos++]) << 8));
			short run = (short) ((0xff & data[pos++]) + ((0xff & data[pos++]) << 8));

			if(offset + run != 0){
				x += offset;
				for(int i=0; i<run; i++){
					bitmap[x++][y] = (short) ((0xff & data[pos++]) + ((0xff & data[pos++]) << 8));
				}
			}else{
				x = 0;
				y++;
				if(y < height){
					pos = starts[y] + dstart;
				}
			}
		}
	}

	public BufferedImage getImage(){
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		for(int x=0; x<width; x++){
			for(int y=0; y<height; y++){
				int alpha = 255 - ((bitmap[x][y] & 0x8000) >>> 15)*255;
				int red = Utils.convertColor5to8((bitmap[x][y] & 0x7c00) >>> 10);
				int green = Utils.convertColor5to8((bitmap[x][y] & 0x3e0) >>> 5);
				int blue = Utils.convertColor5to8(bitmap[x][y] & 0x1f);
				int argb = (alpha << 24) + (red << 16) + (green << 8) + blue;
				image.setRGB(x, y, argb);
			}
		}
		return image;
	}

	public void hue(Hue hue, boolean partial){
		for(int x=0; x<width; x++){
			for(int y=0; y<height; y++){
				int alpha = (bitmap[x][y] & 0x8000) >>> 15;
				if(alpha == 1) continue;
				int red = (bitmap[x][y] & 0x7c00) >>> 10;
				int green = (bitmap[x][y] & 0x3e0) >>> 5;
				int blue = (bitmap[x][y] & 0x1f);
				
				if(partial && red == green && green == blue){
					bitmap[x][y] = (short) hue.getColors()[red];
				}else if(!partial){
					int value = (red + green + blue) / 3;
					bitmap[x][y] = (short) hue.getColors()[value];
				}
			}
		}
	}

	public Point getPoint(int x, int y){
		return new Point(x,y);
	}

	public class Point{
		int x;
		int y;

		private Point(int x, int y){
			this.x = x;
			this.y = y;
		}

		public short getColor(){
			return bitmap[x][y];
		}

		public void setColor(short color){
			bitmap[x][y] = color;
		}
	}
}
