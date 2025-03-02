package eu.janinko.andaria.ultimasdk.graphics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorTest {


    @Test
    public void testRgbHsvConversion() {

        for (short r = 0; r <= 31; r++) {
            for (short g = 0; g <= 31; g++) {
                for (short b = 0; b <= 31; b++) {
                    Color.Hsv hsv = Color.rgb2hsv(r, g, b);
                    //System.out.println(r + ", " + g + ", " + b + " -> " +hsv);
                    Color color = Color.hsv2rgb(hsv);

                    assertEquals(r, color.get5Red(), "Couldn't match color for " + r + ", " + g + ", " + b);
                    assertEquals(g, color.get5Green(), "Couldn't match color for " + r + ", " + g + ", " + b);
                    assertEquals(b, color.get5Blue(), "Couldn't match color for " + r + ", " + g + ", " + b);
                }
            }
        }
    }


    @Test
    public void testMaxHue(){
        Color.hsv2rgb(360.0, 1, 1);
    }
}