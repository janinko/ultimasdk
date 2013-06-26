package eu.janinko.Andaria.ultimasdk.files.gumps;

import eu.janinko.Andaria.ultimasdk.files.graphics.Bitmap;
import java.awt.image.BufferedImage;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Gump {
	private int width;
	private int height;
	private Bitmap bitmap;

	public Gump(int width, int height, byte[] data) {
		bitmap = new Bitmap(width, height);
		this.width = width;
		this.height = height;

		bitmap.readGraphics(data);
	}


	public BufferedImage getImage(){
		return bitmap.getImage();
	}
}
