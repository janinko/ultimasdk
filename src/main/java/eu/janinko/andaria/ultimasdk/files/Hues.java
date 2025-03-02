package eu.janinko.andaria.ultimasdk.files;

import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.andaria.ultimasdk.files.hues.Hue;
import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * Note: Hues are indexed from 1 as in game hue number 0 is representing no hue at all.
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Hues implements UOFile<Hue>{
    private static final int COLORS_COUNT = 3000;
    private final ArrayList<Hue> hues = new ArrayList<>(COLORS_COUNT);
    private final ArrayList<Integer> hueHeaders = new ArrayList<>(375);

    private Hues(InputStream is) throws IOException {
        LittleEndianDataInputStream in = new LittleEndianDataInputStream(is);

        for(int i=0; i<COLORS_COUNT; i++){
            if((i & 0x07) == 0){ // 0x0f = 7 = 0000 0111
                hueHeaders.add(in.readInt());
            }

            Hue hue = new Hue(in);
            hue.setId(i + 1);
            hues.add(hue);
        }
    }

    public static Hues load(Path file) throws IOException {
        try (InputStream input = Files.newInputStream(file)) {
            return new Hues(input);
        }
    }

    public void save(OutputStream os) throws IOException{
        try (LittleEndianDataOutputStream out = new LittleEndianDataOutputStream(os)) {

            for (int i = 0; i < COLORS_COUNT; i++) {
                if ((i & 0x07) == 0) { // 0x0f = 7 = 0000 0111
                    out.writeInt(hueHeaders.get(i >> 3));
                }

                hues.get(i).save(out);
            }
        }
    }

    public List<Hue> getHues(){
        return Collections.unmodifiableList(hues);
    }

    public void setHue(int i, Hue hue) {
        hues.set(i-1, hue);
    }

    /**
     * Gets UO asset - hue - from file. Because in UO hue 0 means no hue applied, the indexing here respect that and
     * indexing starts with number 1. That means that the last valid index is count() and not count()-1.
     * @param idx Index of the hue (starts with 1).
     */
    @Override
    public Hue get(int idx) {
        return hues.get(idx-1);
    }

    @Override
    public int count() {
        return COLORS_COUNT;
    }
}
