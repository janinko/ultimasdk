package eu.janinko.andaria.ultimasdk.graphics;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

/**
 * @author janinko
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Color {

    private static final int[] EIGHT_BIT_INT = {
            0x0, 0x8, 0x10, 0x18, 0x21, 0x29, 0x31, 0x39, 0x42, 0x4a, 0x52, 0x5a, 0x63, 0x6b, 0x73, 0x7b, 0x84, 0x8c,
            0x94, 0x9c, 0xa5, 0xad, 0xb5, 0xbd, 0xc6, 0xce, 0xd6, 0xde, 0xe7, 0xef, 0xf7, 0xff
    };

    private static final byte[] EIGHT_BIT_BYTE = {
            0x0, 0x8, 0x10, 0x18, 0x21, 0x29, 0x31, 0x39, 0x42, 0x4a, 0x52, 0x5a, 0x63, 0x6b, 0x73, 0x7b, (byte) 0x84,
            (byte) 0x8c, (byte) 0x94, (byte) 0x9c, (byte) 0xa5, (byte) 0xad, (byte) 0xb5, (byte) 0xbd, (byte) 0xc6,
            (byte) 0xce,(byte) 0xd6, (byte) 0xde, (byte) 0xe7, (byte) 0xef, (byte) 0xf7, (byte) 0xff
    };

    /**
     * Transparent is represented in Ultima as 0x0000.
     */
    public static final Color ALPHA = new Color((short) 0x0000);
    /**
     * Black is represented in Ultima as 0x8000, so it's distinct from transparent 0x0000.
     */
    public static final Color BLACK = new Color((short) 0x8000);
    public static final Color WHITE = new Color((short) 0x7FFF);

    @Getter
    private final short color;

    public static Color getInstance(short color) {
        return new Color(color);
    }

    public static Color getInstance(short red, short green, short blue) {
        if (blue < 0 || blue > 31) {
            throw new IllegalArgumentException("Color can be only in range 0 - 31. Blue: " + blue);
        }
        if (green < 0 || green > 31) {
            throw new IllegalArgumentException("Color can be only in range 0 - 31. Green: " + green);
        }
        if (red < 0 || red > 31) {
            throw new IllegalArgumentException("Color can be only in range 0 - 31. Red: " + red);
        }
        if (red == 0 && green == 0 && blue == 0) {
            return BLACK;
        }
        short color = (short) (blue | (green << 5) | (red << 10));
        return getInstance(color);
    }

    public static Color from8bit(int red, int green, int blue, int alfa) {
        if (alfa == 0) {
            return Color.ALPHA;
        }
        if (blue < 0 || blue > 255) {
            throw new IllegalArgumentException("Color can be only in range 0 - 255. Blue: " + blue);
        }
        if (green < 0 || green > 255) {
            throw new IllegalArgumentException("Color can be only in range 0 - 255. Green: " + green);
        }
        if (red < 0 || red > 255) {
            throw new IllegalArgumentException("Color can be only in range 0 - 255. Red: " + red);
        }
        return getInstance(convertColor8to5(red), convertColor8to5(green), convertColor8to5(blue));
    }

    public static Color from8bit(int red, int green, int blue) {
        return from8bit(red, green, blue, 255);
    }

    public static Color fromARGBint(int argb) {
        int alpha = (argb >> 24) & 0xff;
        int red = (argb >> 16) & 0xff;
        int green = (argb >> 8) & 0xff;
        int blue = argb & 0xff;
        return from8bit(red, green, blue, alpha);
    }

    public boolean isAlpha() {
        return (color & 0xffff) == 0x0000;
    }

    public boolean isGrayscale() {
        return getBlue() == getGreen() && getGreen() == getRed();
    }

    public int getAlpha() {
        return (isAlpha() ? 0 : 255);
    }

    public byte get5Red() {
        return (byte) ((color & 0x7c00) >>> 10);
    }

    public int getBlue() {
        return convertColor5to8(get5Blue());
    }

    public byte getBlueByte() {
        return convertColor5to8Byte(get5Blue());
    }

    public byte get5Green() {
        return (byte) ((color & 0x3e0) >>> 5);
    }

    public int getGreen() {
        return convertColor5to8(get5Green());
    }

    public byte getGreenByte() {
        return convertColor5to8Byte(get5Green());
    }

    public byte get5Blue() {
        return (byte) (color & 0x1f);
    }

    public int getRed() {
        return convertColor5to8(get5Red());
    }

    public byte getRedByte() {
        return convertColor5to8Byte(get5Red());
    }

    public int get5Average() {
        return (get5Blue() + get5Green() + get5Red()) / 3;
    }

    public int getAverage() {
        return convertColor5to8(get5Average());
    }

    public int getAGBR() {
        return (getAlpha() << 24) + (getRed() << 16) + (getGreen() << 8) + getBlue();
    }

    public int getAGBRPre() {
        if (isAlpha()) {
            return 0;
        }
        return (getRed() << 16) + (getGreen() << 8) + getBlue();
    }

    public int getBGRA() {
        return (getBlue() << 24) + (getGreen() << 16) + (getRed() << 8) + getAlpha();
    }

    public int getBGRAPre() {
        if (isAlpha()) {
            return 0;
        }
        return (getBlue() << 24) + (getGreen() << 16) + (getRed() << 8) + 0xff;
    }

    public Hsv getHsv() {
        return rgb2hsv(get5Red(), get5Green(), get5Blue());
    }

    public static int convertColor5to8(int c) {
        if(c < 0 || c > 31){
            throw new IllegalArgumentException("Number to convert can be only in range 0-31. It is " + c);
        }
        return EIGHT_BIT_INT[c];
    }

    public static byte convertColor5to8Byte(int c) {
        if(c < 0 || c > 31){
            throw new IllegalArgumentException("Number to convert can be only in range 0-31. It is " + c);
        }
        return EIGHT_BIT_BYTE[c];
    }

    public static short convertColor8to5(int c) {
        return (short) (c / 8);
    }

    public static Hsv rgb2hsv(short red, short green, short blue) {
        double h, s, v;

        short min = red, max = red;
        if (green > max) max = green;
        if (blue > max) max = blue;
        if (green < min) min = green;
        if (blue < min) min = blue;
        int delta = max - min;

        if (delta == 0) {
            return new Hsv(0, 0, max / 31.0);
        }

        if (max == red) {
            h = (double) (green - blue) / delta % 6;
        } else if (max == green) {
            h = (double) (blue - red) / delta + 2;
        } else {
            h = (double) (red - green) / delta + 4;
        }
        h *= 60;

        if(h < 0){
            h = 360 + h;
        }

        if (max == 0) {
            s = 0;
        } else {
            s = (double) delta / max;
        }

        v = max / 31.0;

        return new Hsv(h, s, v);
    }

    public static Color hsv2rgb(double h, double s, double v) {
        double r, g, b;
        if(h >= 360.0) {h -= 360.0;}

        double hh = h / 60;
        int i = ((int) hh) % 6;

        double f = hh - i;
        double p = v * (1 - s);
        double q = v * (1 - f * s);
        double t = v * (1 - (1 - f) * s);

        switch (i) {
            case 0:
                r = v; g = t; b = p; break;
            case 1:
                r = q; g = v; b = p; break;
            case 2:
                r = p; g = v; b = t; break;
            case 3:
                r = p; g = q; b = v; break;
            case 4:
                r = t; g = p; b = v; break;
            case 5:
                r = v; g = p; b = q; break;
            default:
                throw new IllegalStateException();
        }

        return getInstance((short) Math.round(r*31), (short) Math.round(g*31), (short) Math.round(b*31));
    }

    public static Color hsv2rgb(Hsv hsv) {
        return hsv2rgb(hsv.h, hsv.s, hsv.v);
    }

    public record Hsv(double h, double s, double v){}

    public double distance(Color other) {
        int rmean = (this.getRed() + other.getRed()) / 2;
        int r = this.getRed() - other.getRed();
        int g = this.getGreen() - other.getGreen();
        int b = this.getBlue() - other.getBlue();
        final int rpart = ((512 + rmean) * r * r) >> 8;
        final int gpart = 4 * g * g;
        final int bpart = ((767 - rmean) * b * b) >> 8;
        return Math.sqrt(rpart + gpart + bpart);
    }

    @Override
    public String toString() {
        return "Color{" + "r=" + get5Red() + " g=" + get5Green() + " b=" + get5Blue() + " a=" + isAlpha() + '}';
    }

}
