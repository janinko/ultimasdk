package eu.janinko.Andaria.ultimasdk.files;

import eu.janinko.Andaria.ultimasdk.files.index.FileIndex;
import eu.janinko.Andaria.ultimasdk.files.statics.Static;
import eu.janinko.Andaria.ultimasdk.utils.RandomAccessLEDataInputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import eu.janinko.Andaria.ultimasdk.files.index.IdxFile;
import eu.janinko.Andaria.ultimasdk.files.statics.StaticsBlock;

/**
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Statics extends IdxFile<StaticsBlock>{
    private static final int STATICS_COUNT = 0x60000;
    private static final int HEIGHT_BLOCKS = 512;

	public Statics(InputStream staidx, File statics) throws IOException{
        super(staidx, statics, STATICS_COUNT);
	}

    @Override
    public StaticsBlock get(int idx) throws IOException {
        int xBlock = idx / HEIGHT_BLOCKS;
        int yBlock = idx % HEIGHT_BLOCKS;
		return getBlock(xBlock, yBlock);
    }

	public List<Static> getStatics(int x, int y) throws IOException{
		int xBlock = x / 8;
		int yBlock = y / 8;

		return getBlock(xBlock, yBlock).getStaticsAt(x, y);
	}

	public StaticsBlock getBlock(int xBlock, int yBlock) throws IOException{
		int blockNumber = xBlock * HEIGHT_BLOCKS + yBlock;

		FileIndex.DataPack data = fileIndex.getData(blockNumber);
		if(data == null) return new StaticsBlock(xBlock, yBlock);

		RandomAccessLEDataInputStream dataStream = data.getNewStream();

		int numberOfItems = data.getData().length / 7;
        return new StaticsBlock(xBlock, yBlock, numberOfItems, dataStream);
    }
}
