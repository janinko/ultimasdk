package eu.janinko.Andaria.ultimasdk.files.gumps;

import eu.janinko.Andaria.ultimasdk.utils.RandomAccessLEDataInputStream;
import eu.janinko.Andaria.ultimasdk.graphics.Bitmap;
import eu.janinko.Andaria.ultimasdk.graphics.Image;

import java.io.IOException;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Gump extends Image{
	private int width;
	private int height;

	public Gump(int width, int height, RandomAccessLEDataInputStream data) throws IOException {
		bitmap = Bitmap.readColorLines(width, height, data);
		this.width = width;
		this.height = height;

        
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

    @Override
    public String toString() {
        return "Gump{" + "width=" + width + ", height=" + height + '}';
    }
}
