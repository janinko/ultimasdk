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

    public static final int MAP_COUNT = 0x4000;
    public static final int STATIC_COUNT = 0x10000;
    public static final int ARTS_COUNT = MAP_COUNT + STATIC_COUNT;

    private Arts(InputStream artidx, File artmul) throws IOException {
        super(artidx, artmul, ARTS_COUNT);
    }

    public static Arts open(Path idxFile, Path mulFile) throws IOException {
        try (InputStream idxStream = Files.newInputStream(idxFile)) {
            return new Arts(idxStream, mulFile.toFile());
        }
    }

    public Art get(int index) throws IOException {
        if (index >= ARTS_COUNT) {
            throw new IllegalArgumentException("Index " + index + " too high.");
        }
        FileIndex.DataPack data = fileIndex.getData(index);
        if (data == null) {
            return null;
        }

        Art art = new Art(data.getNewStream(), index < MAP_COUNT);

        return art;
    }

    private void set(int index, Art art) throws IOException {
        if (index >= ARTS_COUNT) {
            throw new IllegalArgumentException("Index " + index + " too high.");
        }
        RandomAccessLEDataOutputStream data = new RandomAccessLEDataOutputStream();
        art.save(data, index < MAP_COUNT);
        int extra = 0;

        DataPack dp = new DataPack(data.getArray(), extra);
        fileIndex.set(index, dp);
    }

    public void clear(int index) throws IOException {
        if (index >= ARTS_COUNT) {
            throw new IllegalArgumentException("Index " + index + " too high.");
        }
        fileIndex.set(index, null);
    }

    public Art getMap(int index) throws IOException {
        if (index >= MAP_COUNT) {
            throw new IllegalArgumentException("Map index " + index + " too high.");
        }
        return get(index);
    }

    public Art getStatic(int index) throws IOException {
        if (index >= STATIC_COUNT) {
            throw new IllegalArgumentException("Static index " + index + " too high.");
        }
        return get(index + 0x4000);
    }

    public boolean isStatic(int index) {
        if (index >= STATIC_COUNT) {
            throw new IllegalArgumentException("Static index " + index + " too high.");
        }
        int idx = index + 0x4000;
        return fileIndex.isData(idx);
    }

    public void setStatic(int index, Art art) throws IOException {
        if (index >= STATIC_COUNT) {
            throw new IllegalArgumentException("Static index " + index + " too high.");
        }
        set(index + 0x4000, art);
    }

    public void clearMap(int index) throws IOException {
        if (index >= MAP_COUNT) {
            throw new IllegalArgumentException("Map index " + index + " too high.");
        }
        clear(index);
    }

    public void clearStatic(int index) throws IOException {
        if (index >= STATIC_COUNT) {
            throw new IllegalArgumentException("Static index " + index + " too high.");
        }
        clear(index + 0x4000);
    }
}
