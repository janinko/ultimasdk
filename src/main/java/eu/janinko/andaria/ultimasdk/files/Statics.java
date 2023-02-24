package eu.janinko.andaria.ultimasdk.files;

import eu.janinko.andaria.ultimasdk.files.index.FileIndex;
import eu.janinko.andaria.ultimasdk.files.statics.Static;
import eu.janinko.andaria.ultimasdk.utils.RandomAccessLEDataInputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import eu.janinko.andaria.ultimasdk.files.index.IdxFile;
import eu.janinko.andaria.ultimasdk.files.statics.StaticsBlock;
import eu.janinko.andaria.ultimasdk.utils.RandomAccessLEDataOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Statics extends IdxFile<StaticsBlock>{
    private static final int STATICS_COUNT = 0x60000;
    private static final int HEIGHT_BLOCKS = 512;

    private Statics(InputStream staidx, File statics) throws IOException {
        super(staidx, statics, STATICS_COUNT);
    }

    public static Statics open(Path idxFile, Path mulFile) throws IOException {
        try (InputStream idxStream = Files.newInputStream(mulFile)) {
            return new Statics(idxStream, mulFile.toFile());
        }
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

    public void setStatics(int x, int y, List<Static> statics) throws IOException {
        int xBlock = x / 8;
        int yBlock = y / 8;
        int blockNumber = xBlock * HEIGHT_BLOCKS + yBlock;

        StaticsBlock block = getBlock(xBlock, yBlock);
        block.clearStaticsAt(x, y);
        for (Static aStatic : statics) {
            Static insert = new Static(aStatic.getId(), x, y, aStatic.getZ(), aStatic.getColor());
            block.addStatic(insert);
        }

        RandomAccessLEDataOutputStream data = new RandomAccessLEDataOutputStream();
        block.save(data);

        FileIndex.DataPack oldData = fileIndex.getData(blockNumber);
        int extra = oldData == null ? 0 : oldData.getExtra();
        FileIndex.DataPack dp = new FileIndex.DataPack(data.getArray(), extra);
        fileIndex.set(blockNumber, dp);
    }

    public void setBlock(int xBlock, int yBlock, StaticsBlock block) throws IOException {
        int blockNumber = xBlock * HEIGHT_BLOCKS + yBlock;

        RandomAccessLEDataOutputStream data = new RandomAccessLEDataOutputStream();
        block.save(data);

        FileIndex.DataPack oldData = fileIndex.getData(blockNumber);
        int extra = oldData == null ? 0 : oldData.getExtra();
        FileIndex.DataPack dp = new FileIndex.DataPack(data.getArray(), extra);
        fileIndex.set(blockNumber, dp);
    }
}
