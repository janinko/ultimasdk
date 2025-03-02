
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
import lombok.Getter;

/**
 * Frame alignment:<br>
 * center (+) - center of the game square<br>
 * centX (&lt;  &gt;) - From left of frame to center. Positive if left of frame is left of center. I.e. how much should
 * the frame be shifted left.<br>
 * centY (^ v) - From bottom of frame to center. Positive if bottom of frame is above center. I.e. how much should the
 * frame be shifted up.<br>
 * <pre>
 *  _____________
 * |             |
 * |             |
 * |             |
 * |             |
 * |  centX      |
 * |&lt;  &gt;+        |
 * |    ^        |
 * |     centY   |
 * |    v        |
 * |_____________|
 * </pre>
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Frame extends Image{
    private final int centX;
    private final int centY;
    @Getter
    private final int width;
    @Getter
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

    /**
     * Compute left padding so the frame is placed in image with given width. Placed means is that the frame is aligned
     * to it's game square. The game square being in the middle of the image.
     */
    public int leftPadding(int width){
        return width / 2 - centX;
    }

    /**
     * Compute top padding so the frame is placed in image with game square centered {@code centerShift} pixels from the
     * top. Placed means is that the frame is aligned to it's game square.
     */
    public int topPadding(int centerShift) {
        return centerShift - centY - height;
    }

    /**
     * Compute how big portion of the frame is below center of the game square.
     */
    public int biasDown(){
        return Math.max(0, -centY);
    }

    /**
     * Compute how big portion of the frame is above center of the game square.
     */
    public int biasUp(){
        return Math.max(0, height + centY);
    }

    /**
     * Compute minimum width of image with placed frame.
     */
    public int getViewWidth() {
        int left = centX;
        int right = width - centX + 1;
        int horizontal = Math.max(left, right);
        return horizontal * 2;
    }

    /**
     * Computes minimum height of image with placed frame. Placed means is that the frame is aligned to it's game
     * square. Returns height with at least the whole of the square being visible on the image.
     */
    public int getViewHeight() {
        int bottomPadding = centY + 21;
        return Math.max(height, height + bottomPadding);
    }

    public int getXOffset() {
        int left = centX;
        int right = width - centX + 1;
        int horizontal = Math.max(left, right);
        return horizontal - left;
        // A: left - left = 0
        // B: right - left = width - centX + 1 - centX = 1 + width - 2*centX
    }

    public int getYOffset() {
        return 0;
    }

    private BufferedImage centeredImage(final BasicBitmap bitmap1) {
        int left = centX;
        int right = width - centX + 1;
        int down = centY + 21;
        int up = height + centY;
                
        int horizontal = Math.max(left, right);
        int w = horizontal*2;
        int h = Math.max(height, height + down);
        
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
