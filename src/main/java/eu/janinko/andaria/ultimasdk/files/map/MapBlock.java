package eu.janinko.andaria.ultimasdk.files.map;

import java.io.IOException;

import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataOutputStream;

/**
 *
 * @author Honza Brázdil &lt;jbrazdil@redhat.com&gt;
 */
public class MapBlock {

    private final int xBlock;
    private final int yBlock;

    private final int header;
    private final MapTile[][] tiles = new MapTile[8][8];

    public MapBlock(int xBlock, int yBlock, LittleEndianDataInputStream dataStream) throws IOException {
        this.xBlock = xBlock;
        this.yBlock = yBlock;

        this.header = dataStream.readInt();
        for (int dy = 0; dy < 8; dy++) {
            for (int dx = 0; dx < 8; dx++) {
                tiles[dx][dy] = new MapTile(xBlock * 8 + dx, yBlock * 8 + dy, dataStream);
            }
        }
    }

    public void save(LittleEndianDataOutputStream out) throws IOException {
        out.writeInt(header);
        for (int dy = 0; dy < 8; dy++) {
            for (int dx = 0; dx < 8; dx++) {
                tiles[dx][dy].save(out);
            }
        }
    }

    public int getxBlock() {
        return xBlock;
    }

    public int getyBlock() {
        return yBlock;
    }

    public int getHeader() {
        return header;
    }

    public MapTile[][] getTiles() {
        return tiles;
    }
}
