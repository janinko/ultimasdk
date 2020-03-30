package eu.janinko.Andaria.ultimasdk.files.tiledata;

import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataOutputStream;
import eu.janinko.Andaria.ultimasdk.utils.Utils;
import java.io.IOException;

/**
 *
 * @author jbrazdil
 */
public final class ItemData extends TileDatum {

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

    public void save(LittleEndianDataOutputStream out) throws IOException {
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
        Utils.writeName(out, this.getName());
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

    public boolean isEmpty() {
        return flags.flags.isEmpty()
                && weight == 0
                && quality == 0
                && unknown1 == 0
                && unknown2 == 0
                && quantity == 0
                && animation == 0
                && unknown3 == 0
                && hue == 0
                && unknown4 == 0
                && value == 0
                && height == 0
                && name.trim().isEmpty();
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "ItemData{id=" + id + '}';
        }
        return "ItemData{" + "id=" + id + ", flags=" + flags + ", weight=" + weight + ", quality=" + quality + ", unknown1=" + unknown1 + ", unknown2=" + unknown2 + ", quantity=" + quantity + ", animation=" + animation + ", unknown3=" + unknown3 + ", hue=" + hue + ", unknown4=" + unknown4 + ", value=" + value + ", height=" + height + ", name=" + name + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.flags != null ? this.flags.hashCode() : 0);
        hash = 89 * hash + this.weight;
        hash = 89 * hash + this.quality;
        hash = 89 * hash + this.unknown1;
        hash = 89 * hash + this.unknown2;
        hash = 89 * hash + this.quantity;
        hash = 89 * hash + this.animation;
        hash = 89 * hash + this.unknown3;
        hash = 89 * hash + this.hue;
        hash = 89 * hash + this.unknown4;
        hash = 89 * hash + this.value;
        hash = 89 * hash + this.height;
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItemData other = (ItemData) obj;
        if (this.flags != other.flags && (this.flags == null || !this.flags.equals(other.flags))) {
            return false;
        }
        if (this.weight != other.weight) {
            return false;
        }
        if (this.quality != other.quality) {
            return false;
        }
        if (this.unknown1 != other.unknown1) {
            return false;
        }
        if (this.unknown2 != other.unknown2) {
            return false;
        }
        if (this.quantity != other.quantity) {
            return false;
        }
        if (this.animation != other.animation) {
            return false;
        }
        if (this.unknown3 != other.unknown3) {
            return false;
        }
        if (this.hue != other.hue) {
            return false;
        }
        if (this.unknown4 != other.unknown4) {
            return false;
        }
        if (this.value != other.value) {
            return false;
        }
        if (this.height != other.height) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
