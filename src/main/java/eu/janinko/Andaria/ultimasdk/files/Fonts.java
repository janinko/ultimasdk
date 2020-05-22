package eu.janinko.Andaria.ultimasdk.files;

import eu.janinko.Andaria.ultimasdk.files.fonts.CharImg;
import eu.janinko.Andaria.ultimasdk.files.fonts.Font;
import eu.janinko.Andaria.ultimasdk.files.hues.Hue;
import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 *
 * @author janinko
 */
public class Fonts implements UOFile<Font> {

    private static final int FONTS_COUNT = 10; // TODO: Variable amount
    private final ArrayList<Font> fonts = new ArrayList<>(FONTS_COUNT);

    public Fonts(InputStream is) throws IOException {
        LittleEndianDataInputStream in = new LittleEndianDataInputStream(is);

        for (int i = 0; i < FONTS_COUNT; i++) {
            Font font = new Font(in);
            fonts.add(font);
        }
    }

    public void save(OutputStream os) throws IOException {
        LittleEndianDataOutputStream out = new LittleEndianDataOutputStream(os);

        for (Font font : fonts) {
            font.save(os);
        }
    }

    public void setChar(int font, char idx, CharImg chr){
        fonts.get(font).set(idx, chr);
    }
    
    @Override
    public int count() {
        return FONTS_COUNT;
    }

    @Override
    public Font get(int idx) throws IOException {
        return fonts.get(idx);
    }

}
