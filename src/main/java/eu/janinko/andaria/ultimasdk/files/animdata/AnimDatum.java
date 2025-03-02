package eu.janinko.andaria.ultimasdk.files.animdata;

import eu.janinko.andaria.ultimasdk.files.fonts.CharImg;
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
public class AnimDatum {

    private static final int NOPRINT_CHARS = 32;
    private static final int CHARS_COUNT = 256 - NOPRINT_CHARS;
    byte[] frameData = new byte[64]; // This could be less than 64 bytes, I recall seeing some garbage data near the end.
    byte unknown;
    byte frameCount;
    byte frameInterval; // Move to the next frame every (frameInterval * 50) milliseconds.
    byte frameStart; // It is believed this is the delay before the animation starts (frameStart * 50ms).
    private final ArrayList<CharImg> chars = new ArrayList<>(CHARS_COUNT);

    public AnimDatum(InputStream is) throws IOException {
        LittleEndianDataInputStream in = new LittleEndianDataInputStream(is);

        for (int i = 0; i < 64; i++) {
            frameData[i] = in.readByte();
        }
        unknown = in.readByte();
        frameCount = in.readByte();
        frameInterval = in.readByte();
        frameStart = in.readByte();
    }

    public void save(OutputStream os) throws IOException {
        LittleEndianDataOutputStream out = new LittleEndianDataOutputStream(os);

        for (int i = 0; i < 64; i++) {
            out.writeByte(frameData[i]);
        }
        out.writeByte(unknown);
        out.writeByte(frameCount);
        out.writeByte(frameInterval);
        out.writeByte(frameStart);
    }

}
