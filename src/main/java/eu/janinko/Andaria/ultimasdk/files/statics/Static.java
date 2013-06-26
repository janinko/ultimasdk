package eu.janinko.Andaria.ultimasdk.files.statics;

/**
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Static {
	private short id;
	private byte x;
	private byte y;
	private byte z;
	private short unknown;

	private int xBlock;
	private int yBlock;

	public Static(int xBlock, int yBlock, byte[] data) {
		this.xBlock = xBlock;
		this.yBlock = yBlock;

		id = (short) ((0xff & data[0]) + ((0xff & data[1]) << 8));
		x = data[2];
		y = data[3];
		z = data[4];
		unknown = (short) ((0xff & data[5]) + ((0xff & data[6]) << 8));
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

	@Override
	public String toString() {
		return "Static{" + "id=" + id + ", x=" + getX() + ", y=" + getY() + ", z=" + z + ", unknown=" + unknown + '}';
	}
}
