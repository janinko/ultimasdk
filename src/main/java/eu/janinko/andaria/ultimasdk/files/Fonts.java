package eu.janinko.andaria.ultimasdk.files;

import eu.janinko.andaria.ultimasdk.files.fonts.CharImg;
import eu.janinko.andaria.ultimasdk.files.fonts.Font;
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
public class Fonts implements UOFile<Font> {

    private static final int FONTS_COUNT = 10; // TODO: Variable amount
    private final ArrayList<Font> fonts = new ArrayList<>(FONTS_COUNT);

    private Fonts(InputStream is) throws IOException {
        LittleEndianDataInputStream in = new LittleEndianDataInputStream(is);

        for (int i = 0; i < FONTS_COUNT; i++) {
            Font font = new Font(in);
            fonts.add(font);
        }
    }

    public static Fonts load(Path file) throws IOException {
        try (InputStream input = Files.newInputStream(file)) {
            return new Fonts(input);
        }
    }

    public void save(OutputStream os) throws IOException {
        try (LittleEndianDataOutputStream out = new LittleEndianDataOutputStream(os)) {
            for (Font font : fonts) {
                font.save(out);
            }
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
