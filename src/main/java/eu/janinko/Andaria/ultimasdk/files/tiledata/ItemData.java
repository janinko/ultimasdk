package eu.janinko.Andaria.ultimasdk.files.tiledata;

import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataOutputStream;
import eu.janinko.Andaria.ultimasdk.utils.Utils;
import java.io.IOException;

/**
 *
 * @author jbrazdil
 */
public final class ItemData {
	private int id;
	private TileFlags flags;
	private byte weight;
	private byte quality;
	private short unknown1;
	private byte unknown2;
	private byte quantity;
	private short animation;
	private byte unknown3;
	private byte hue;
	private byte unknown4;
	private byte value; // Unknown5
	private byte height;
	private String name;

	public ItemData(LittleEndianDataInputStream in) throws IOException {
		this.setFlags(new TileFlags(in.readInt()));
		this.setWeight(in.readByte());
		this.setQuality(in.readByte());
		this.setUnknown1(in.readShort());
		this.setUnknown2(in.readByte());
		this.setQuantity(in.readByte());
		this.setAnimation(in.readShort());
		this.setUnknown3(in.readByte());
		this.setHue(in.readByte());
		this.setUnknown4(in.readByte());
		this.setValue(in.readByte()); // Unknown5
		this.setHeight(in.readByte());
		this.setName(Utils.readName(in));
	}

	public ItemData(ItemData d) {
		this.setFlags(new TileFlags(d.flags.toInt()));
		this.setWeight(d.weight);
		this.setQuality(d.quality);
		this.setUnknown1(d.unknown1);
		this.setUnknown2(d.unknown2);
		this.setQuantity(d.quantity);
		this.setAnimation(d.animation);
		this.setUnknown3(d.unknown3);
		this.setHue(d.hue);
		this.setUnknown4(d.unknown4);
		this.setValue(d.value); // Unknown5
		this.setHeight(d.height);
		this.setName(d.name);
	}

	public void save(LittleEndianDataOutputStream out) throws IOException{
		out.writeInt(this.getFlags().toInt());
		out.writeByte(this.getWeight());
		out.writeByte(this.getQuality());
		out.writeShort(this.getUnknown1());
		out.writeByte(this.getUnknown2());
		out.writeByte(this.getQuantity());
		out.writeShort(this.getAnimation());
		out.writeByte(this.getUnknown3());
		out.writeByte(this.getHue());
		out.writeByte(this.getUnknown4());
		out.writeByte(this.getValue()); // Unknown5
		out.writeByte(this.getHeight());
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

	public byte getWeight() {
		return weight;
	}

	public void setWeight(byte weight) {
		this.weight = weight;
	}

	public byte getQuality() {
		return quality;
	}

	public void setQuality(byte quality) {
		this.quality = quality;
	}

	public byte getQuantity() {
		return quantity;
	}

	public void setQuantity(byte quantity) {
		this.quantity = quantity;
	}

	public byte getValue() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}

	public byte getHeight() {
		return height;
	}

	public void setHeight(byte height) {
		this.height = height;
	}

	public short getAnimation() {
		return animation;
	}

	public void setAnimation(short animation) {
		this.animation = animation;
	}

	public short getUnknown1() {
		return unknown1;
	}

	public void setUnknown1(short unknown1) {
		this.unknown1 = unknown1;
	}

	public byte getUnknown2() {
		return unknown2;
	}

	public void setUnknown2(byte unknown2) {
		this.unknown2 = unknown2;
	}

	public byte getUnknown3() {
		return unknown3;
	}

	public void setUnknown3(byte unknown3) {
		this.unknown3 = unknown3;
	}

	public byte getHue() {
		return hue;
	}

	public void setHue(byte hue) {
		this.hue = hue;
	}

	public byte getUnknown4() {
		return unknown4;
	}

	public void setUnknown4(byte unknown4) {
		this.unknown4 = unknown4;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ItemData{" + "id=" + id + ", flags=" + flags + ", weight=" + weight + ", quality=" + quality + ", unknown1=" + unknown1 + ", unknown2=" + unknown2 + ", quantity=" + quantity + ", animation=" + animation + ", unknown3=" + unknown3 + ", hue=" + hue + ", unknown4=" + unknown4 + ", value=" + value + ", height=" + height + ", name=" + name + '}';
	}
}
