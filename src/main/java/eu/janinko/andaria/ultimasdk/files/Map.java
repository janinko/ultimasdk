package eu.janinko.andaria.ultimasdk.files;

import eu.janinko.andaria.ultimasdk.files.index.FileIndex;
import eu.janinko.andaria.ultimasdk.files.map.MapTile;
import eu.janinko.andaria.ultimasdk.utils.RandomAccessLEDataInputStream;

import java.io.File;
import java.io.IOException;

import eu.janinko.andaria.ultimasdk.files.index.IdxFile;
import eu.janinko.andaria.ultimasdk.files.map.MapBlock;
import eu.janinko.andaria.ultimasdk.utils.RandomAccessLEDataOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Map extends IdxFile<MapBlock>{
    private static final int MAP_COUNT = 0x70000;
    private static final int MAP_BLOCK_SIZE = 3*64+4;
    private static final int HEIGHT_BLOCKS = 512;

    private Map(File map) throws IOException {
        super(MAP_BLOCK_SIZE, map, MAP_COUNT);
    }

    public static Map open(Path mapFile) throws IOException {
        return new Map(mapFile.toFile());
    }

    @Override
    public MapBlock get(int idx) throws IOException {
        int xBlock = idx / HEIGHT_BLOCKS;
        int yBlock = idx % HEIGHT_BLOCKS;
        return getBlock(xBlock, yBlock);
    }

    public MapTile getTile(int x, int y) throws IOException{
        int xBlock = x / 8;
        int yBlock = y / 8;

        MapTile[][] tiles = getBlock(xBlock, yBlock).getTiles();

        return tiles[x - xBlock*8][y - yBlock*8];
    }

    public void save(OutputStream mul) throws IOException {
        super.save(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            }
        }, mul);
    }

    @Override
    public void save(OutputStream idx, OutputStream mul) throws IOException {
        save(mul);
    }


    /**
     * Returns the 8x8 block of map tiles on given block coordinates.
     * @param xBlock x (left->right) coordinate of the block.
     * @param yBlock y (top->down) coordinate of the block.
     * @return Block of 8x8 tiles.
     * @throws IOException
     */
    public MapBlock getBlock(int xBlock, int yBlock) throws IOException {
        int blockNumber = xBlock * HEIGHT_BLOCKS + yBlock;

        FileIndex.DataPack data = fileIndex.getData(blockNumber);
        RandomAccessLEDataInputStream dataStream = data.getNewStream();

        return new MapBlock(xBlock, yBlock, dataStream);
    }

    public void setTile(int x, int y, MapTile tile) throws IOException {
        if(tile.getX() != x || tile.getY() != y){
            tile = new MapTile(tile.getId(), tile.getAlt(), x, y); 
       }
        
        int xBlock = x / 8;
        int yBlock = y / 8;
        int blockNumber = xBlock * HEIGHT_BLOCKS + yBlock;

        int xt = x - xBlock * 8;
        int yt = y - yBlock * 8;

        MapBlock block = getBlock(xBlock, yBlock);
        block.getTiles()[xt][yt] = tile;

        RandomAccessLEDataOutputStream data = new RandomAccessLEDataOutputStream();
        block.save(data);

        FileIndex.DataPack oldData = fileIndex.getData(blockNumber);
        FileIndex.DataPack dp = new FileIndex.DataPack(data.getArray(), oldData.getExtra());
        fileIndex.set(blockNumber, dp);
    }
}
