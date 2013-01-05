package eu.janinko.Andaria.ultimasdk.files.tiledata;

import eu.janinko.Andaria.ultimasdk.LittleEndianDataInputStream;
import eu.janinko.Andaria.ultimasdk.LittleEndianDataOutputStream;
import eu.janinko.Andaria.ultimasdk.Utils;
import java.io.IOException;

/**
 * @author jbrazdil
 */
public final class LandData {
	private int id;
	private TileFlags flags;
	private short textureId;
	private String name;

	public LandData(LittleEndianDataInputStream in) throws IOException {
		this.setFlags(new TileFlags(in.readInt()));
		this.setTextureId(in.readShort());
		this.setName(Utils.readName(in));
	}

	public void save(LittleEndianDataOutputStream out) throws IOException{
		out.writeInt(this.getFlags().toInt());
		out.writeShort(this.getTextureId());
		Utils.writeName(out,this.getName());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TileFlags getFlags() {
		return flags;
	}

	public void setFlags(TileFlags flags) {
		this.flags = flags;
	}

	public short getTextureId() {
		return textureId;
	}

	public void setTextureId(short textureId) {
		this.textureId = textureId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "LandData{" + "name=" + name + ", flags=" + flags + '}';
	}
}
