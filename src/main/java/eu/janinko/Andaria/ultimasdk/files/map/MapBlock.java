package eu.janinko.Andaria.ultimasdk.files.map;

import java.io.IOException;

import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;

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
