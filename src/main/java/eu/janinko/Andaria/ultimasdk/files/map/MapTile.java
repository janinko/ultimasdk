
package eu.janinko.Andaria.ultimasdk.files.map;

import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;
import java.io.IOException;

/**
 * @author janinko
 */
public class MapTile {
	private int id;
	private int x;
	private int y;
	private byte alt;

	public MapTile(int x, int y, LittleEndianDataInputStream data) throws IOException {
		this.x = x;
		this.y = y;
		this.id = data.readUnsignedShort();
		this.alt = data.readByte();
	}

	public int getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public byte getAlt() {
		return alt;
	}

}
