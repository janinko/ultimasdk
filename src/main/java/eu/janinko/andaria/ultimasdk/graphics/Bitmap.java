package eu.janinko.andaria.ultimasdk.graphics;

import eu.janinko.andaria.ultimasdk.files.hues.Hue;
import java.awt.image.BufferedImage;

/**
 *
 * @author janinko
 */
public interface Bitmap {

    int getWidth();

    int getHeight();

    Color getColor(int x, int y);

    Point getPoint(int x, int y);

    void hue(Hue hue, boolean partial);

    void setColor(int x, int y, Color color);

    BufferedImage getImage();

    public interface Point {

        Color getColor();

        void setColor(short color);

        void setColor(Color color);
    }
}
