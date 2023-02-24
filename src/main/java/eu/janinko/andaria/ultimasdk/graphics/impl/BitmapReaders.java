package eu.janinko.andaria.ultimasdk.graphics.impl;

import eu.janinko.andaria.ultimasdk.graphics.Color;
import eu.janinko.andaria.ultimasdk.graphics.impl.BitmapReader;
import java.nio.ByteBuffer;

/**
 *
 * @author janinko
 */
public enum BitmapReaders implements BitmapReader {
    SCANLINE {
        @Override
        public void read(Color[][] bitmap, ByteBuffer data) {
            int width = bitmap.length;
            int height = bitmap[0].length;

            int bytes = (width - 1) / 8 + 1;
            for (int y = 0; y < height; y++) {
                int x = 0;
                for (int t = 0; t < bytes; t++) {
                    byte block = data.get();
                    for (int m = 0x80; x < width && m > 0; m >>= 1, x++) {
                        if ((m & block) == 0) {
                            bitmap[x][y] = Color.ALPHA;
                        } else {
                            bitmap[x][y] = Color.BLACK;
                        }
                    }
                }
            }
        }
    },
    COLOR_LINES {
        @Override
        public void read(Color[][] bitmap, ByteBuffer data) {
            int width = bitmap.length;
            int height = bitmap[0].length;

            int[] starts = new int[height];

            for (int i = 0; i < height; i++) {
                starts[i] = data.getInt() * 4;
            }

            for (int y = 0; y < height; y++) {
                int x = 0;
                data.position(starts[y]);
                while (x < width) {
                    short color = data.getShort();
                    int repeat = readUnsignedShort(data);
                    for (int i = 0; i < repeat; i++) {
                        bitmap[x++][y] = Color.getInstance(color);
                    }
                }
            }
        }
    },
    COLOR_CHUNKS {
        @Override
        public void read(Color[][] bitmap, ByteBuffer data) {
            int height = bitmap[0].length;

            int[] starts = new int[height];

            for (int i = 0; i < height; i++) {
                starts[i] = readUnsignedShort(data) * 2;
            }
            int dstart = data.position();

            int y = 0;
            int x = 0;

            data.position(starts[y] + dstart);
            while (y < height) {
                int offset = readUnsignedShort(data);
                int run = readUnsignedShort(data);

                if (offset + run != 0) {
                    x += offset;
                    for (int i = 0; i < run; i++) {
                        bitmap[x++][y] = Color.getInstance(data.getShort());
                    }
                } else {
                    x = 0;
                    y++;
                    if (y < height) {
                        data.position(starts[y] + dstart);
                    }
                }
            }
        }
    };
    
    private static int readUnsignedShort(ByteBuffer data) {
        return data.getShort() & 0xffff;
    }
}
