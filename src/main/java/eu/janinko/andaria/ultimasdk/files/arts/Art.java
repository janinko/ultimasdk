package eu.janinko.andaria.ultimasdk.files.arts;

import java.awt.image.BufferedImage;

import eu.janinko.andaria.ultimasdk.utils.RandomAccessLEDataInputStream;
import eu.janinko.andaria.ultimasdk.graphics.impl.WritableBitmap;
import eu.janinko.andaria.ultimasdk.graphics.Image;

import java.io.IOException;
import java.util.Objects;

import eu.janinko.andaria.ultimasdk.graphics.impl.BasicBitmap;
import eu.janinko.andaria.ultimasdk.graphics.impl.BitmapWriter;
import eu.janinko.andaria.ultimasdk.utils.RandomAccessLEDataOutputStream;

/**
 * @author janinko
 */
public class Art extends Image{
    private int flag;
    private int width;
    private int height;

    public Art(RandomAccessLEDataInputStream data, boolean terrain) throws IOException {
        if (terrain) {
            loadRaw(data);
        } else {
            flag = data.readInt();
            if (flag > 0xFFFF || flag == 0) {
                loadRaw(data);
            } else {
                loadRun(data);
            }
        }
    }
    
    public Art(BufferedImage image) {
        flag = 1234;
        bitmap = new WritableBitmap(image);
        width = image.getWidth();
        height = image.getHeight();
    }

    private void loadRaw(RandomAccessLEDataInputStream data) throws IOException {
        width = 44;
        height = 44;
        bitmap = BasicBitmap.readRaw(data);
    }

    private void loadRun(RandomAccessLEDataInputStream data) throws IOException {
        width = data.readUnsignedShort();
        height = data.readUnsignedShort();
        bitmap = BasicBitmap.readColorChunks(width, height, data);
    }

    public void save(RandomAccessLEDataOutputStream data, boolean terrain) throws IOException{
        if (terrain) {
            throw new UnsupportedOperationException();
        } else {
            data.writeInt(flag);
            data.writeShort(width);
            data.writeShort(height);
            data.write(BitmapWriter.writeColorChunks(bitmap));
        }
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public int getFlag() {
        return flag;
    }

    @Override
    public String toString() {
        return "Art{" + "flag=" + flag + ", width=" + width + ", height=" + height + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.flag;
        hash = 43 * hash + this.width;
        hash = 43 * hash + this.height;
        hash = 43 * hash + Objects.hashCode(this.bitmap);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Art other = (Art) obj;
        if (this.flag != other.flag) {
            return false;
        }
        if (this.width != other.width) {
            return false;
        }
        if (this.height != other.height) {
            return false;
        }
        if (!Objects.equals(this.bitmap, other.bitmap)) {
            return false;
        }
        return true;
    }

}
