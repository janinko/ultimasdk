package eu.janinko.andaria.ultimasdk.files;

import eu.janinko.andaria.ultimasdk.files.unifont.UniCharImg;
import eu.janinko.andaria.ultimasdk.utils.LitleEndianFile;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author janinko
 */
public class UniFonts implements UOFile<UniCharImg>  {

    private static final int CHARS_COUNT = 0x10000;
    private int lookupTable[] = new int[CHARS_COUNT];
    private List<UniCharImg> chars = new ArrayList<>();
    private int maxChar = 0;

    private UniFonts() {
    }

    public static UniFonts load(Path filePath) throws IOException {
        try (LitleEndianFile file = LitleEndianFile.readFile(filePath)) {
            ByteBuffer buffer = file.getBuffer();
            int[] positions = new int[CHARS_COUNT];
            buffer.asIntBuffer().get(positions);
            UniFonts ret = new UniFonts();
            for (int i = 0; i < positions.length; i++) {
                int position = positions[i];
                if (position > 0) {
                    buffer.position(position);
                    UniCharImg chr = new UniCharImg(buffer, i);
                    if (chr.getWidth() > 0 && chr.getHeight() > 0) {
                        ret.lookupTable[i] = ret.chars.size();
                        ret.chars.add(chr);
                        ret.maxChar = i;
                    }
                } else {
                    ret.lookupTable[i] = -1;
                }
            }
            return ret;
        }
    }

    @Override
    public UniCharImg get(int idx) throws IOException {
        if (lookupTable[idx] == -1) {
            return null;
        } else {
            return chars.get(lookupTable[idx]);
        }
    }

    @Override
    public int count() {
        return maxChar+1;
    }

}
