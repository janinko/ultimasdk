
package eu.janinko.Andaria.ultimasdk.files.anims;

import eu.janinko.Andaria.ultimasdk.files.graphics.Bitmap;
import eu.janinko.Andaria.ultimasdk.files.graphics.Color;
import eu.janinko.Andaria.ultimasdk.files.graphics.Image;
import eu.janinko.Andaria.ultimasdk.utils.RandomAccessLEDataInputStream;
import java.io.IOException;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Frame extends Image{
	private int centX;
	private int centY;
	private int width;
	private int height;
	private Color[][] data;

	Frame(RandomAccessLEDataInputStream data, Color[] palette) throws IOException {
		centX = data.readShort();
		centY = data.readShort();
		width = data.readShort();
		height = data.readShort();
		bitmap = new Bitmap(width, height);
		
		bitmap.readAnimFrame(data, centX, centY, palette);
	}
}
