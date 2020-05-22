package eu.janinko.Andaria.ultimasdk.files.fonts;

import eu.janinko.Andaria.ultimasdk.graphics.BasicBitmap;
import eu.janinko.Andaria.ultimasdk.graphics.Bitmap;
import eu.janinko.Andaria.ultimasdk.graphics.BitmapWriter;
import eu.janinko.Andaria.ultimasdk.graphics.Image;
import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataOutputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import lombok.Data;

/**
 *
 * @author janinko
 */
@Data
public class CharImg extends Image{

    private char id;

    private byte width;
    private byte height;
    private byte header;
    
    public CharImg(LittleEndianDataInputStream in, int id) throws IOException {
        width = in.readByte();
        height = in.readByte();
        header = in.readByte();
        bitmap = BasicBitmap.readColumns(width, height, in);
    }

	public CharImg(BufferedImage image, byte header, char id) {
        if(image.getWidth() > Byte.MAX_VALUE || image.getHeight() > Byte.MAX_VALUE){
            throw new IllegalArgumentException("Image is too large");
        }
		this.header = header;
        this.id = id;
        bitmap = new Bitmap(image);
        width = (byte) image.getWidth();
        height = (byte) image.getHeight();
	}
    
    public void save(LittleEndianDataOutputStream out) throws IOException {
        out.writeByte(width);
        out.writeByte(height);
        out.writeByte(header);
        out.write(BitmapWriter.writeColorColumns(bitmap));
    }
}
