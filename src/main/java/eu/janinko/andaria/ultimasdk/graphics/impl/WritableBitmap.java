package eu.janinko.andaria.ultimasdk.graphics.impl;

import eu.janinko.andaria.ultimasdk.files.hues.Hue;
import eu.janinko.andaria.ultimasdk.graphics.Bitmap;
import eu.janinko.andaria.ultimasdk.graphics.Color;

import java.awt.image.BufferedImage;

/**
 * @author janinko
 */
public class WritableBitmap extends BasicBitmap implements Bitmap {

    private Color[][] modBitmap;

    public WritableBitmap(WritableBitmap o) {
        super(o);
        if (o.modBitmap != null) {
            this.modBitmap = copy(o.modBitmap);
        }
    }

    public WritableBitmap(BasicBitmap o) {
        super(o);
    }

    public WritableBitmap(int width, int height) {
        super(width, height);
    }

    public WritableBitmap(BufferedImage image) {
        super(image);
    }


    @Override
    public void hue(Hue hue, boolean partial) {
        if (hue == null) {
            modBitmap = null;
            return;
        }
        if (modBitmap == null) {
            modBitmap = getBitmap();
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = modBitmap[x][y];
                if (color.isAlpha()) {
                    continue;
                }
                if (color.get5Red() == 0 && color.get5Green() == 0 && color.get5Blue() <= 1) {
                    continue;
                }

                if (partial && color.isGrayscale()) {
                    modBitmap[x][y] = hue.getColor(color.get5Red());
                } else if (!partial) {
                    modBitmap[x][y] = hue.getColor(color.get5Red());
                }
            }
        }
    }

    @Override
    public Point getPoint(int x, int y) {
        return new Point(x, y);
    }

    @Override
    public void setColor(int x, int y, Color color) {
        if (modBitmap == null) {
            modBitmap = getBitmap();
        }
        modBitmap[y][x] = color;
    }

    @Override
    public Color getColor(int x, int y) {
        if (modBitmap == null) {
            return super.getColor(x, y);
        }
        return modBitmap[x][y];
    }

    public class Point implements Bitmap.Point{

        private final int x;
        private final int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public Color getColor() {
            if (modBitmap == null) {
                return WritableBitmap.this.getColor(x, y);
            }
            return modBitmap[x][y];
        }

        @Override
        public void setColor(short color) {
            if (modBitmap == null) {
                modBitmap = WritableBitmap.this.getBitmap();
            }
            modBitmap[x][y] = Color.getInstance(color);
        }

        @Override
        public void setColor(Color color) {
            if (modBitmap == null) {
                modBitmap = WritableBitmap.this.getBitmap();
            }
            modBitmap[x][y] = color;
        }
    }
}
