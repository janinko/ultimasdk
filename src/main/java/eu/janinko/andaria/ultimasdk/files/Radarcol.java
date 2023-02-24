package eu.janinko.andaria.ultimasdk.files;

import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.andaria.ultimasdk.graphics.Color;
import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * @author janinko
 */
public class Radarcol implements UOFile<Color> {

    private static final int COLORS_COUNT = 0x10000;
    private ArrayList<Color> colors = new ArrayList<>(COLORS_COUNT);

    private Radarcol(InputStream is) throws IOException {
        LittleEndianDataInputStream in = new LittleEndianDataInputStream(is);

        for (int i = 0; i < COLORS_COUNT; i++) {
            colors.add(Color.getInstance(in.readShort()));
        }
    }

    public static Radarcol load(Path file) throws IOException {
        try (InputStream input = Files.newInputStream(file)) {
            return new Radarcol(input);
        }
    }

    @Override
    public Color get(int idx) {
        return colors.get(idx);
    }

    @Override
    public int count() {
        return colors.size();
    }

    public Color getMapColor(int index) {
        return get(index);
    }

    public Color getStaticColor(int index) {
        return get(index + 0x4000);
    }

    public void save(OutputStream os) throws IOException {
        LittleEndianDataOutputStream out = new LittleEndianDataOutputStream(os);

        for (int i = 0; i < COLORS_COUNT; i++) {
            out.writeShort(colors.get(i).getColor());
        }
    }
}
