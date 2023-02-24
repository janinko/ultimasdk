package eu.janinko.andaria.ultimasdk.graphics.impl;

import eu.janinko.andaria.ultimasdk.graphics.Color;
import java.nio.ByteBuffer;

/**
 *
 * @author janinko
 */
public interface BitmapReader {

    public void read(Color[][] bitmap, ByteBuffer data);

}
