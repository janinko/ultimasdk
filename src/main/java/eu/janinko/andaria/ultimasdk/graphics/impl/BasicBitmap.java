package eu.janinko.andaria.ultimasdk.graphics.impl;

import eu.janinko.andaria.ultimasdk.graphics.Color;
import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataInputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import eu.janinko.andaria.ultimasdk.utils.RandomAccessLEDataInputStream;
import java.nio.ByteBuffer;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class BasicBitmap {

    public static Color[][] copy(Color[][] input) {
        Color[][] target = new Color[input.length][];
        for (int i = 0; i < input.length; i++) {
            target[i] = Arrays.copyOf(input[i], input[i].length);
        }
        return target;
    }

    public static BasicBitmap readColorLines(int width, int height, RandomAccessLEDataInputStream data) throws IOException {
        BasicBitmap b = new BasicBitmap(width, height);

        int[] starts = new int[height];

        for (int i = 0; i < height; i++) {
            starts[i] = data.readInt() * 4;
        }

        for (int y = 0; y < height; y++) {
            int x = 0;
            data.seek(starts[y]);
            while (x < width) {
                short color = data.readShort();
                int repeat = data.readUnsignedShort();
                for (int i = 0; i < repeat; i++) {
                    b.bitmap[x++][y] = Color.getInstance(color);
                }
            }
        }
        return b;
    }

    public static BasicBitmap readBitmap(BitmapReader reader, int width, int height, ByteBuffer data) {
        BasicBitmap b = new BasicBitmap(width, height);
        reader.read(b.bitmap, data);
        return b;
    }

    public static BasicBitmap readColorChunks(int width, int height, RandomAccessLEDataInputStream data) throws IOException {
        BasicBitmap b = new BasicBitmap(width, height);

        int[] starts = new int[height];

        for (int i = 0; i < height; i++) {
            starts[i] = data.readUnsignedShort() * 2;
        }
        int dstart = data.getPosition();

        int y = 0;
        int x = 0;

        data.seek(starts[y] + dstart);
        while (y < height) {
            int offset = data.readUnsignedShort();
            int run = data.readUnsignedShort();

            if (offset + run != 0) {
                x += offset;
                for (int i = 0; i < run; i++) {
                    b.bitmap[x++][y] = Color.getInstance(data.readShort());
                }
            } else {
                x = 0;
                y++;
                if (y < height) {
                    data.seek(starts[y] + dstart);
                }
            }
        }
        return b;
    }

    public static BasicBitmap readRaw(RandomAccessLEDataInputStream data) throws IOException {
        BasicBitmap b = new BasicBitmap(44, 44);

        for (int x = 0; x < 22; x++) {
            for (int y = 21 - x; y <= 22 + x; y++) {
                b.bitmap[x][y] = Color.getInstance(data.readShort());
            }
        }
        for (int x = 22; x < 44; x++) {
            for (int y = x - 22; y <= 65 - x; y++) {
                b.bitmap[x][y] = Color.getInstance(data.readShort());
            }
        }
        return b;
    }

    public static BasicBitmap readColumns(int width, int height, LittleEndianDataInputStream data) throws IOException {
        BasicBitmap b = new BasicBitmap(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                b.bitmap[x][y] = Color.getInstance(data.readShort());
            }
        }
        return b;
    }

    public static BasicBitmap readAnimFrame(int width, int height, RandomAccessLEDataInputStream data, int cx, int cy, Color[] palette) throws IOException {
        BasicBitmap b = new BasicBitmap(width, height);

        int ln = 0;
        int xBase = cx - 512;
        int yBase = cy + height - 512;
        int delta = width;
        ln += xBase;
        ln += yBase * delta;
        while (true) { // not konec
            int rowHeader = data.readInt();

            if (rowHeader == 0x7fff7fff) {
                break;  // end
            }
            int runLength = rowHeader & 0xfff;
            int lineNum = ((rowHeader >> 12) & 0x3ff) ^ 0x200;
            int rowOfs = ((rowHeader >> 22) & 0x3ff) ^ 0x200;
            int pos = lineNum * delta + rowOfs + ln;
            int x = pos % delta;
            int y = pos / delta;
            if (y >= 0) {
                if (y >= height) {
                    break;
                }
                for (int i = 0; i < runLength; i++) {
                    int p = data.readUnsignedByte();
                    b.bitmap[x + i][y] = palette[p];
                }
            } else {
                data.seek(data.getPosition() + runLength);
            }
        }
        return b;
    }

    private final Color[][] bitmap;
    protected final int height;
    protected final int width;

    public BasicBitmap(BasicBitmap o) {
        this.width = o.width;
        this.height = o.height;
        this.bitmap = copy(o.bitmap);
    }

    public BasicBitmap(int width, int height) {
        if (width <= 0) {
            throw new IllegalArgumentException("Width can't be less then one, is " + width);
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Height can't be less then one, is " + height);
        }

        this.bitmap = new Color[width][height];
        this.width = width;
        this.height = height;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bitmap[x][y] = Color.ALPHA;
            }
        }
    }

    public BasicBitmap(Color[][] colors) {
        this.bitmap = copy(colors);
        this.width = colors.length;
        this.height = colors[0].length;
    }

    public BasicBitmap(BufferedImage image) {
        this(image.getWidth(), image.getHeight());
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = image.getRGB(x, y);
                bitmap[x][y] = Color.fromARGBint(pixel);
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public BufferedImage getImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, getColor(x, y).getAGBR());
            }
        }
        return image;
    }

    public BufferedImage getPaddedImage(int w, int h, int leftPadding, int topPadding) {
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        for (int x = 0; x < width; x++) {
            if (x + leftPadding >= w) {
                continue;
            }
            for (int y = 0; y < height; y++) {
                if (y + topPadding >= h) {
                    continue;
                }
                image.setRGB(x + leftPadding, y + topPadding, getColor(x, y).getAGBR());
            }
        }
        return image;
    }

    public Color[][] getBitmap() {
        return copy(bitmap);
    }

    public Color getColor(int x, int y) {
        return bitmap[x][y];
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Arrays.deepHashCode(this.bitmap);
        hash = 47 * hash + this.height;
        hash = 47 * hash + this.width;
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
        final BasicBitmap other = (BasicBitmap) obj;
        if (this.height != other.height) {
            return false;
        }
        if (this.width != other.width) {
            return false;
        }
        if (!Arrays.deepEquals(this.bitmap, other.bitmap)) {
            return false;
        }
        return true;
    }

}
