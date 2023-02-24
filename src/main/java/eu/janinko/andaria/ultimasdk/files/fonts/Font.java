package eu.janinko.andaria.ultimasdk.files.fonts;

import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 *
 * @author janinko
 */
public class Font {
    private static final int NOPRINT_CHARS = 32;
    private static final int CHARS_COUNT = 256 - NOPRINT_CHARS;
    private byte header;
    private final ArrayList<CharImg> chars = new ArrayList<>(CHARS_COUNT);
    
    public Font(InputStream is) throws IOException {
        LittleEndianDataInputStream in = new LittleEndianDataInputStream(is);

        header = in.readByte();

        for (int i = 0; i < CHARS_COUNT; i++) {
            CharImg font = new CharImg(in, i+32);
            font.setId((char) (i + NOPRINT_CHARS));
            chars.add(font);
        }
    }
    
    public void save(OutputStream os) throws IOException {
        LittleEndianDataOutputStream out = new LittleEndianDataOutputStream(os);

        out.writeByte(header);
        for (int i = 0; i < CHARS_COUNT; i++) {
            chars.get(i).save(out);
        }
    }

    public CharImg get(char ch) throws IOException {
        if(ch < NOPRINT_CHARS){
            return null;
        }
        return chars.get(ch - NOPRINT_CHARS);
    }

    public void set(char idx, CharImg font) {
        chars.set(idx - NOPRINT_CHARS, font);
    }
}
