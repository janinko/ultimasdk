package eu.janinko.Andaria.ultimasdk.graphics;

import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 *
 * @author janinko
 */
public abstract class Image {
	protected BasicBitmap bitmap;

	public Bitmap getBitmap(){
		return new Bitmap(bitmap);
	}

	public BufferedImage getImage(){
		return bitmap.getImage();
	}

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.bitmap);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Image other = (Image) obj;
        if (!Objects.equals(this.bitmap, other.bitmap)) {
            return false;
        }
        return true;
    }
}
