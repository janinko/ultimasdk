package eu.janinko.andaria.ultimasdk.files;

import eu.janinko.andaria.ultimasdk.files.index.IdxFile;
import eu.janinko.andaria.ultimasdk.files.index.FileIndex;
import eu.janinko.andaria.ultimasdk.files.arts.Art;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import eu.janinko.andaria.ultimasdk.files.index.FileIndex.DataPack;
import eu.janinko.andaria.ultimasdk.utils.RandomAccessLEDataOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author janinko
 */
public class Arts extends IdxFile<Art> {

    public static final int ARTS_COUNT = 0x10007;
    public static final int MAP_COUNT = 0x4000;
    public static final int STATIC_COUNT = 0x4000;

    private Arts(InputStream artidx, File artmul) throws IOException {
        super(artidx, artmul, ARTS_COUNT);
    }

    public static Arts open(Path idxFile, Path mulFile) throws IOException{
        try(InputStream idxStream = Files.newInputStream(mulFile)){
            return new Arts(idxStream, mulFile.toFile());
        }
    }

    public Art get(int index) throws IOException {
        FileIndex.DataPack data = fileIndex.getData(index);
        if (data == null) {
            return null;
        }

        Art art = new Art(data.getNewStream());

        return art;
    }

    private void set(int index, Art art) throws IOException {
        RandomAccessLEDataOutputStream data = new RandomAccessLEDataOutputStream();
        art.save(data);
        int extra = 0;

        DataPack dp = new DataPack(data.getArray(), extra);
        fileIndex.set(index, dp);
    }

    private void clear(int index) throws IOException {
        fileIndex.set(index, null);
    }

    public Art getMap(int index) throws IOException {
        return get(index & 0xFFFF);
    }

    public Art getStatic(int index) throws IOException {
        return get((index + 0x4000) & 0xFFFF);
    }

    public boolean isStatic(int index) {
        int idx = (index + 0x4000) & 0xFFFF;
        return fileIndex.isData(idx);
    }

    public void setStatic(int index, Art art) throws IOException {
        set((index + 0x4000) & 0xFFFF, art);
    }

    public void clearStatic(int index) throws IOException {
        clear((index + 0x4000) & 0xFFFF);
    }
}
