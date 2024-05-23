package eu.janinko.andaria.ultimasdk.files;

import eu.janinko.andaria.ultimasdk.files.index.FileIndex;
import eu.janinko.andaria.ultimasdk.files.index.IdxFile;
import eu.janinko.andaria.ultimasdk.files.sounds.SoundSample;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Sounds extends IdxFile<SoundSample> {

    private Sounds(InputStream gumpidx, File gumpmul) throws IOException {
        super(gumpidx, gumpmul, 1000);
    }

    public static Sounds open(Path idxFile, Path mulFile) throws IOException {
        try (InputStream idxStream = Files.newInputStream(idxFile)) {
            return new Sounds(idxStream, mulFile.toFile());
        }
    }

    @Override
    public SoundSample get(int idx) throws IOException {
        FileIndex.DataPack data =  fileIndex.getData(idx);
        if(data == null) return null;
        int index = (data.getExtra() >> 16) & 0xFFFF;
        int reserved = data.getExtra() & 0xFFFF;

        int dataLenghth = data.getData().length - 32;

        return new SoundSample(index, reserved, data.getNewStream(), dataLenghth);
    }
}
