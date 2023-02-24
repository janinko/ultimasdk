package eu.janinko.andaria.ultimasdk.files.fonts;

import eu.janinko.andaria.ultimasdk.graphics.impl.BasicBitmap;
import eu.janinko.andaria.ultimasdk.graphics.impl.WritableBitmap;
import eu.janinko.andaria.ultimasdk.graphics.impl.BitmapWriter;
import eu.janinko.andaria.ultimasdk.graphics.Image;
import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataOutputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 *
 * @author janinko
 */
@EqualsAndHashCode(callSuper = true)
public class CharImg extends Image {

    @Getter
    private char id;
    private byte width;
    private byte height;
    @Getter
    private byte header;

    public CharImg(LittleEndianDataInputStream in, int id) throws IOException {
        width = in.readByte();
        height = in.readByte();
        header = in.readByte();
        bitmap = BasicBitmap.readColumns(width, height, in);
    }

    public CharImg(BufferedImage image, byte header, char id) {
        if (image.getWidth() > Byte.MAX_VALUE || image.getHeight() > Byte.MAX_VALUE) {
            throw new IllegalArgumentException("Image is too large");
        }
        this.header = header;
        this.id = id;
        bitmap = new WritableBitmap(image);
        width = (byte) image.getWidth();
        height = (byte) image.getHeight();
    }

    public void save(LittleEndianDataOutputStream out) throws IOException {
        out.writeByte(width);
        out.writeByte(height);
        out.writeByte(header);
        out.write(BitmapWriter.writeColorColumns(bitmap));
    }
    
    void setId(char id ){
        this.id = id;
    }
}
