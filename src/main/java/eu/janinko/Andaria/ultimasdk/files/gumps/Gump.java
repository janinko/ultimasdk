package eu.janinko.Andaria.ultimasdk.files.gumps;

import eu.janinko.Andaria.ultimasdk.utils.RandomAccessLEDataInputStream;
import eu.janinko.Andaria.ultimasdk.files.graphics.Bitmap;
import eu.janinko.Andaria.ultimasdk.files.graphics.Image;
import java.io.IOException;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Gump extends Image{
	private int width;
	private int height;

	public Gump(int width, int height, RandomAccessLEDataInputStream data) throws IOException {
		bitmap = new Bitmap(width, height);
		this.width = width;
		this.height = height;

		bitmap.readColorLines(data);
	}

	public Gump(Bitmap b) {
		this.width = b.getWidth();
		this.height = b.getHeight();
		this.bitmap = b;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
