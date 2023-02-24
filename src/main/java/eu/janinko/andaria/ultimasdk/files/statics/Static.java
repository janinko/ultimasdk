package eu.janinko.andaria.ultimasdk.files.statics;

import static eu.janinko.andaria.ultimasdk.files.statics.StaticsBlock.BLOCK_SIZE;
import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataOutputStream;
import java.io.IOException;
import lombok.Getter;

/**
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
@Getter
public class Static {
    private int id;
    private int dx;
    private int dy;
    private byte z;
    private int color;

    private int xBlock;
    private int yBlock;
        
        public Static(int id, int x, int y, byte z, int color){
            this.id = id;
            this.dx = x % BLOCK_SIZE;
            this.dy = y % BLOCK_SIZE;
            this.z = z;
            this.color = color;
            this.xBlock = x / BLOCK_SIZE;
            this.yBlock = y / BLOCK_SIZE;
        }

    public Static(int xBlock, int yBlock, LittleEndianDataInputStream data) throws IOException {
        this.xBlock = xBlock;
        this.yBlock = yBlock;

        id = data.readUnsignedShort();
        dx = data.readUnsignedByte();
        dy = data.readUnsignedByte();
        z = data.readByte();
        color = data.readUnsignedShort();
    }

    public void save(LittleEndianDataOutputStream out) throws IOException {
        out.writeShort(id);
        out.writeByte(dx);
        out.writeByte(dy);
        out.writeByte(z);
        out.writeShort(color);
    }

    public int getX(){
        return xBlock * 8 + dx;
    }

    public int getY(){
        return yBlock * 8 + dy;
    }

    @Override
    public String toString() {
        return "Static{" + "id=" + id + ", x=" + getX() + ", y=" + getY() + ", z=" + z + ", color=" + color + '}';
    }

    public boolean equalsStatic(Static other) {
        if(this.id != other.id){
            return false;
        }
        if (this.dx != other.dx) {
            return false;
        }
        if (this.dy != other.dy) {
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
