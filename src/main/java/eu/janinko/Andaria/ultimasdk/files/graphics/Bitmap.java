package eu.janinko.Andaria.ultimasdk.files.graphics;

import eu.janinko.Andaria.ultimasdk.utils.RandomAccessLEDataInputStream;
import eu.janinko.Andaria.ultimasdk.files.hues.Hue;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author janinko
 */
public class Bitmap {
	private final int width;
	private final int height;
	private Color[][] bitmap;
	private Color[][] unhued;

	public Bitmap(Bitmap o){
		this.width = o.width;
		this.height = o.height;
		this.bitmap = o.bitmap.clone();
		if(o.unhued != null){
			this.unhued = o.unhued.clone();
		}
	}
	
	public Bitmap(int width, int height){
		if(width <= 0) throw new IllegalArgumentException("Width can't be less then zero, is " + width);
		if(height <= 0) throw new IllegalArgumentException("Height can't be less then zero, is " + height);
		
		this.bitmap = new Color[width][height];
		this.width = width;
		this.height = height;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				bitmap[x][y] = Color.ALPHA;
			}
		}
	}

	public void readColorLines(RandomAccessLEDataInputStream data) throws IOException{
		int[] starts = new int[height];

		for(int i=0; i<height; i++){
			starts[i] = data.readInt()*4;
		}

		for(int y = 0; y < height; y++){
			int x = 0;
			data.seek(starts[y]);
			while(x < width){
				short color = data.readShort();
				int repeat = data.readUnsignedShort();
				for(int i=0; i < repeat; i++){
					bitmap[x++][y] = Color.getInstance(color);
				}
			}
		}
	}

	public void readColorChunks(RandomAccessLEDataInputStream data) throws IOException{
		int[] starts = new int[height];

		for(int i=0; i<height; i++){
			try{
				starts[i] = data.readUnsignedShort() * 2;
			}catch(Exception ex){
				System.out.println("ex");
				return;
			}
		}
		int dstart = data.getPosition();

		int y = 0;
		int x = 0;

		data.seek(starts[y] + dstart);
		while(y < height){
			int offset = data.readUnsignedShort();
			int run = data.readUnsignedShort();

			if(offset + run != 0){
				x += offset;
				for(int i=0; i<run; i++){
					bitmap[x++][y] = Color.getInstance(data.readShort());
				}
			}else{
				x = 0;
				y++;
				if(y < height){
					data.seek(starts[y] + dstart);
				}
			}
		}
	}

	public BufferedImage getImage(){
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		for(int x=0; x<width; x++){
			for(int y=0; y<height; y++){
				image.setRGB(x, y, bitmap[x][y].getAGBR());
			}
		}
		return image;
	}

	public void hue(Hue hue, boolean partial){
		if(unhued == null){
			unhued = bitmap.clone();
		}
		if(hue == null){
			bitmap = unhued.clone();
			return;
		}
		for(int x=0; x<width; x++){
			for(int y=0; y<height; y++){
				Color color = unhued[x][y];
				if(color.isAlpha()) continue;
				if(color.get5Red() == 0 && color.get5Green() == 0 && color.get5Blue() <= 1) continue;
				
				if(partial && color.isGrayscale()){
					bitmap[x][y] = hue.getColor(color.get5Red());
				}else if(!partial){
					bitmap[x][y] = hue.getColor(color.get5Red());
				}
			}
		}
	}

	public Point getPoint(int x, int y){
		return new Point(x,y);
	}

	public void setColor(int x, int y, Color color){
		bitmap[y][x] = color;
		if(unhued != null){
			unhued[y][x] = color;
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public class Point{
		int x;
		int y;

		private Point(int x, int y){
			this.x = x;
			this.y = y;
		}

		public Color getColor(){
			return bitmap[x][y];
		}

		public void setColor(short color){
			bitmap[x][y] = Color.getInstance(color);
		}

		public void setColor(Color color){
			bitmap[x][y] = color;
		}
	}
}
