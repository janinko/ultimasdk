package eu.janinko.andaria.ultimasdk.graphics;

import eu.janinko.andaria.ultimasdk.graphics.impl.WritableBitmap;
import eu.janinko.andaria.ultimasdk.graphics.impl.BasicBitmap;
import java.awt.image.BufferedImage;
import lombok.EqualsAndHashCode;

/**
 *
 * @author janinko
 */
@EqualsAndHashCode
public abstract class Image {
    protected BasicBitmap bitmap;

    public Bitmap getBitmap(){
        return new WritableBitmap(bitmap);
    }

    public BufferedImage getImage(){
        return bitmap.getImage();
    }
}
