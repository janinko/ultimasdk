package eu.janinko.Andaria.ultimasdk.graphics;

import eu.janinko.Andaria.ultimasdk.files.hues.Hue;

import java.awt.image.BufferedImage;

/**
 * @author janinko
 */
public class Bitmap extends BasicBitmap {
	private Color[][] modBitmap;

	public Bitmap(Bitmap o){
        super(o);
		if(o.modBitmap != null){
			this.modBitmap = copy(o.modBitmap);
		}
	}

	public Bitmap(BasicBitmap o){
        super(o);
	}
    
	public Bitmap(int width, int height){
        super(width, height);
	}
    
    public Bitmap(BufferedImage image){
        super(image);
    }
    
	public BufferedImage getPaddedImage(int w, int h, int leftPadding, int topPadding){
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		for(int x=0; x<width; x++){
            if(x+leftPadding >= w) continue;
			for(int y=0; y<height; y++){
                if(y+topPadding >= h) continue;
				image.setRGB(x+leftPadding, y+topPadding, getColor(x, y).getAGBR());
			}
		}
		return image;
	}

	public void hue(Hue hue, boolean partial){
        if(hue == null){
            modBitmap = null;
            return;
        }
		if(modBitmap == null){
			modBitmap = getBitmap();
		}
		for(int x=0; x<width; x++){
			for(int y=0; y<height; y++){
				Color color = modBitmap[x][y];
				if(color.isAlpha()) continue;
				if(color.get5Red() == 0 && color.get5Green() == 0 && color.get5Blue() <= 1) continue;
				
				if(partial && color.isGrayscale()){
					modBitmap[x][y] = hue.getColor(color.get5Red());
				}else if(!partial){
					modBitmap[x][y] = hue.getColor(color.get5Red());
				}
			}
		}
	}

	public Point getPoint(int x, int y){
		return new Point(x,y);
	}

	public void setColor(int x, int y, Color color){
		if(modBitmap == null){
			modBitmap = getBitmap();
		}
		modBitmap[y][x] = color;
	}

    @Override
    public Color getColor(int x, int y) {
        if(modBitmap == null) return super.getColor(x, y);
        return modBitmap[x][y];
    }
    
    

	public class Point{
		int x;
		int y;

		private Point(int x, int y){
			this.x = x;
			this.y = y;
		}

		public Color getColor(){
            if(modBitmap == null)
                return Bitmap.this.getColor(x, y);
			return modBitmap[x][y];
		}

		public void setColor(short color){
            if(modBitmap == null){
                modBitmap = Bitmap.this.getBitmap();
            }
			modBitmap[x][y] = Color.getInstance(color);
		}

		public void setColor(Color color){
            if(modBitmap == null){
                modBitmap = Bitmap.this.getBitmap();
            }
			modBitmap[x][y] = color;
		}
	}
}
