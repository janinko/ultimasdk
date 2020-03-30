package eu.janinko.Andaria.ultimasdk.files.statics;

import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;
import java.io.IOException;

/**
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Static {
	private int id;
	private int x;
	private int y;
	private byte z;
	private int color;

	private int xBlock;
	private int yBlock;

	public Static(int xBlock, int yBlock, LittleEndianDataInputStream data) throws IOException {
		this.xBlock = xBlock;
		this.yBlock = yBlock;

		id = data.readUnsignedShort();
		x = data.readUnsignedByte();
		y = data.readUnsignedByte();
		z = data.readByte();
		color = data.readUnsignedShort();
	}

	public int getX(){
		return xBlock * 8 + x;
	}

	public int getY(){
		return yBlock * 8 + y;
	}

	public int getZ(){
		return z;
	}

	public int getId(){
		return id;
	}
    
    public int getColor(){
        return color;
    }

	@Override
	public String toString() {
		return "Static{" + "id=" + id + ", x=" + getX() + ", y=" + getY() + ", z=" + z + ", color=" + color + '}';
	}

    public boolean equalsStatic(Static other) {
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.z != other.z) {
            return false;
        }
        if (this.color != other.color) {
            return false;
        }
        return true;
    }
}
