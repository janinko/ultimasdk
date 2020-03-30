package eu.janinko.Andaria.ultimasdk.files.tiledata;

import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataOutputStream;
import eu.janinko.Andaria.ultimasdk.utils.Utils;
import java.io.IOException;

/**
 * @author jbrazdil
 */
public final class LandData extends TileDatum {

    private short textureId;

    public LandData(LittleEndianDataInputStream in) throws IOException {
        this.setFlags(new TileFlags(in.readInt()));
        this.setTextureId(in.readShort());
        this.setName(Utils.readName(in));
    }

    public void save(LittleEndianDataOutputStream out) throws IOException {
        out.writeInt(this.getFlags().toInt());
        out.writeShort(this.getTextureId());
        Utils.writeName(out, this.getName());
    }

    public short getTextureId() {
        return textureId;
    }

    public void setTextureId(short textureId) {
        this.textureId = textureId;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "LandData{id=" + id + '}';
        }
        return "LandData{" + "id=" + id + ", flags=" + flags + ", textureId=" + textureId + ", name=" + name + '}';
    }

    public boolean isEmpty() {
        return flags.flags.isEmpty()
                && textureId == 0
                && name.trim().isEmpty();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.flags != null ? this.flags.hashCode() : 0);
        hash = 37 * hash + this.textureId;
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
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
        final LandData other = (LandData) obj;
        if (this.flags != other.flags && (this.flags == null || !this.flags.equals(other.flags))) {
            return false;
        }
        if (this.textureId != other.textureId) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
