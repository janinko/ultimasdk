package eu.janinko.andaria.ultimasdk.files.unifont;

import eu.janinko.andaria.ultimasdk.graphics.impl.BasicBitmap;
import eu.janinko.andaria.ultimasdk.graphics.impl.BitmapReaders;
import eu.janinko.andaria.ultimasdk.graphics.Image;
import java.nio.ByteBuffer;
import lombok.Data;

/**
 *
 * @author janinko
 */
@Data
public class UniCharImg extends Image {
    private int id;

    private byte xOffset;
    private byte yOffset;
    private byte width;
    private byte height;

    public UniCharImg(ByteBuffer data, int id) {
        this.id = id;
        xOffset = data.get();
        yOffset = data.get();
        width = data.get();
        height = data.get();
        if (width > 0 && height > 0) {
            bitmap = BasicBitmap.readBitmap(BitmapReaders.SCANLINE, width, height, data);
        } else {
            bitmap = BasicBitmap.EMPTY;
        }
    }
}
