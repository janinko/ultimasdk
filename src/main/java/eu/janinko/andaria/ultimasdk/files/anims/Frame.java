
package eu.janinko.andaria.ultimasdk.files.anims;

import java.awt.image.BufferedImage;

import eu.janinko.andaria.ultimasdk.graphics.impl.WritableBitmap;
import eu.janinko.andaria.ultimasdk.graphics.Color;
import eu.janinko.andaria.ultimasdk.graphics.Image;
import eu.janinko.andaria.ultimasdk.utils.RandomAccessLEDataInputStream;

import java.io.IOException;
import java.util.Arrays;

import eu.janinko.andaria.ultimasdk.files.hues.Hue;
import eu.janinko.andaria.ultimasdk.graphics.impl.BasicBitmap;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Frame extends Image{
    private final int centX;
    private final int centY;
    private final int width;
    private final int height;
    private Color[][] data;

    Frame(RandomAccessLEDataInputStream data, Color[] palette) throws IOException {
        centX = data.readShort();
        centY = data.readShort();
        width = data.readShort();
        height = data.readShort();
        bitmap = BasicBitmap.readAnimFrame(width, height, data, centX, centY, palette);
    }
    
    public BufferedImage getCenteredImage(){
        return centeredImage(bitmap);
    }

    public BufferedImage getCenteredImage(Hue hue){
        WritableBitmap editableBitmap = new WritableBitmap(this.bitmap);
        editableBitmap.hue(hue, false);
        return centeredImage(editableBitmap);
    }

    private BufferedImage centeredImage(final BasicBitmap bitmap1) {
        int left = centX;
        int right = width - centX + 1;
        int down = centY + 21;
        int up = height + centY;
                
        int horizontal = (left > right ? left : right);
        int w = horizontal*2;
        int h = height + down;
        
        System.out.print("centX=" + centX + ", centY=" + centY + ", width=" + width + ", height=" + height);
        System.out.print(", right=" + right + ", left=" + left + ", down=" + down + ", up=" + up);
        System.out.println(", horizontal=" + horizontal + ", w=" + w + ", h=" + h);
        return bitmap1.getPaddedImage(w, h, horizontal-left, 0);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.centX;
        hash = 17 * hash + this.centY;
        hash = 17 * hash + this.width;
        hash = 17 * hash + this.height;
        hash = 17 * hash + Arrays.deepHashCode(this.data);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Frame other = (Frame) obj;
        if (this.centX != other.centX) {
            return false;
        }
        if (this.centY != other.centY) {
            return false;
        }
        if (this.width != other.width) {
            return false;
        }
        if (this.height != other.height) {
            return false;
        }
        if (!Arrays.deepEquals(this.data, other.data)) {
            return false;
        }
        return true;
    }

}
