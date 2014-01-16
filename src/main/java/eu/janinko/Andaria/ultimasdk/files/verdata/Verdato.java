
package eu.janinko.Andaria.ultimasdk.files.verdata;

import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;
import java.io.IOException;

/**
 * @author janinko
 */
public final class Verdato {
	VerdataType type;
	int block;
	int position;
	int size;
	int various;

	public Verdato(LittleEndianDataInputStream in) throws IOException {
		this.setType(VerdataType.get(in.readInt()));
		this.setBlock(in.readInt());
		this.setPosition(in.readInt());
		this.setSize(in.readInt());
		this.setVarious(in.readInt());
	}

	public VerdataType getType() {
		return type;
	}

	public void setType(VerdataType type) {
		this.type = type;
	}

	public int getBlock() {
		return block;
	}

	public void setBlock(int block) {
		this.block = block;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getVarious() {
		return various;
	}

	public void setVarious(int various) {
		this.various = various;
	}

	@Override
	public String toString() {
		return "Verdato{" + "type=" + type + ", block=" + block + ", position=" + position + ", size=" + size + ", various=" + various + '}';
	}

}
