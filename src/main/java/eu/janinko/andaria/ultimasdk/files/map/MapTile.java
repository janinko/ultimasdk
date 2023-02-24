
package eu.janinko.andaria.ultimasdk.files.map;

import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataOutputStream;
import java.io.IOException;
import lombok.Getter;
import lombok.ToString;

/**
 * @author janinko
 */
@Getter
@ToString
public class MapTile {
    private int id;
    private int x;
    private int y;
    private byte alt;

    public MapTile(int id, byte alt, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.alt = alt;
    }

    public MapTile(int x, int y, LittleEndianDataInputStream data) throws IOException {
        this.x = x;
        this.y = y;
        this.id = data.readUnsignedShort();
        this.alt = data.readByte();
    }

    public void save(LittleEndianDataOutputStream out) throws IOException {
        out.writeShort(id);
        out.writeByte(alt);
    }

}
