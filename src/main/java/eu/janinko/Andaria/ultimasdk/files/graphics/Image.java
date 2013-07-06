package eu.janinko.Andaria.ultimasdk.files.graphics;

import java.awt.image.BufferedImage;

/**
 *
 * @author janinko
 */
public abstract class Image {
	protected Bitmap bitmap;

	public Bitmap getBitmap(){
		return new Bitmap(bitmap);
	}

	public BufferedImage getImage(){
		return bitmap.getImage();
	}
}
