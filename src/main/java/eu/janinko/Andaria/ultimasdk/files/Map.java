package eu.janinko.Andaria.ultimasdk.files;

import eu.janinko.Andaria.ultimasdk.files.index.FileIndex;
import eu.janinko.Andaria.ultimasdk.files.map.MapTile;
import eu.janinko.Andaria.ultimasdk.utils.RandomAccessLEDataInputStream;

import java.io.File;
import java.io.IOException;

import eu.janinko.Andaria.ultimasdk.files.index.IdxFile;
import eu.janinko.Andaria.ultimasdk.files.map.MapBlock;

/**
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Map extends IdxFile<MapBlock>{
    private static final int MAP_COUNT = 0x70000;
    private static final int MAP_BLOCK_SIZE = 3*64+4;
    private static final int HEIGHT_BLOCKS = 512;

	public Map(File map) throws IOException{
        super(MAP_BLOCK_SIZE, map, MAP_COUNT);
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
}
