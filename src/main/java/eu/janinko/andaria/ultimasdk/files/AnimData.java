package eu.janinko.andaria.ultimasdk.files;

import eu.janinko.andaria.ultimasdk.files.animdata.AnimDatum;
import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 *
 * @author janinko
 */
public class AnimData implements UOFile<AnimDatum> {

    private static final int ITEM_COUNT = 0x8000;

    private final ArrayList<AnimDatum> animData = new ArrayList<>(ITEM_COUNT);
    private final ArrayList<Integer> animHeaders = new ArrayList<>(0x800);


    private AnimData(InputStream is) throws IOException {
        LittleEndianDataInputStream in = new LittleEndianDataInputStream(is);

        for (int i = 0; i < ITEM_COUNT; i++) {
            if ((i & 0x07) == 0) { // 0x07 = 7 = 0000 0111
                animHeaders.add(in.readInt());
            }
            AnimDatum animDatum = new AnimDatum(in);
            animData.add(animDatum);
        }
    }

    public static AnimData load(Path file) throws IOException {
        try (InputStream input = Files.newInputStream(file)) {
            return new AnimData(input);
        }
    }

    public void save(OutputStream os) throws IOException {
        try (LittleEndianDataOutputStream out = new LittleEndianDataOutputStream(os)) {

            for (int i = 0; i < ITEM_COUNT; i++) {
                if ((i & 0x07) == 0) { // 0x07 = 7 = 0000 0111
                    out.writeInt(animHeaders.get(i / 8));
                }
                animData.get(i).save(out);
            }
        }
    }

    @Override
    public int count() {
        return ITEM_COUNT;
    }

    @Override
    public AnimDatum get(int idx) throws IOException {
        return animData.get(idx);
    }

}
