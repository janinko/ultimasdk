package eu.janinko.Andaria.ultimasdk.files;

import eu.janinko.Andaria.ultimasdk.files.map.MapTile;
import eu.janinko.Andaria.ultimasdk.utils.RandomAccessLEDataInputStream;
import java.io.File;
import java.io.IOException;

/**
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Map {
	private FileIndex fileIndex;

	public Map(File map) throws IOException{
		fileIndex = new FileIndex(3*64+4, map, 0x60000);
	}

	public MapTile getTile(int x, int y) throws IOException{
		int xBlock = x / 8;
		int yBlock = y / 8;

		MapTile[][] tiles = getTilesOnBlock(xBlock, yBlock);

		return tiles[x - xBlock*8][y - yBlock*8];
	}

	public MapTile[][] getTilesOnBlock(int xBlock, int yBlock) throws IOException{
		int blockNumber = xBlock * 512 + yBlock;

		FileIndex.DataPack data = fileIndex.getData(blockNumber);

		MapTile[][] tiles = new MapTile[8][8];
		RandomAccessLEDataInputStream dataStream = data.getNewStream();
		int header = dataStream.readInt();
		for(int dy=0; dy<8; dy++){
			for(int dx=0; dx<8; dx++){
				tiles[dx][dy] = new MapTile(xBlock * 8 + dx, yBlock * 8 + dy, dataStream);
			}
		}

		return tiles;
	}
}
