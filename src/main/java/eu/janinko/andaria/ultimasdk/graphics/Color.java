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

    public static final Color ALPHA = new Color((short) 0x0000);
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

    public byte get5Green() {
        return (byte) ((color & 0x3e0) >>> 5);
    }

    public int getGreen() {
        return convertColor5to8(get5Green());
    }

    public byte get5Blue() {
        return (byte) (color & 0x1f);
    }

    public int getRed() {
        return convertColor5to8(get5Red());
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

    public static int convertColor5to8(int c) {
        switch (c) {
            case 31: return 0xff; // 255
            case 30: return 0xf7; // 247
            case 29: return 0xef; // 239
            case 28: return 0xe7; // 231
            case 27: return 0xde; // 222
            case 26: return 0xd6; // 214
            case 25: return 0xce; // 206
            case 24: return 0xc6; // 198
            case 23: return 0xbd; // 189
            case 22: return 0xb5; // 181
            case 21: return 0xad; // 173
            case 20: return 0xa5; // 165
            case 19: return 0x9c; // 156
            case 18: return 0x94; // 148
            case 17: return 0x8c; // 140
            case 16: return 0x84; // 132
            case 15: return 0x7b; // 123
            case 14: return 0x73; // 115
            case 13: return 0x6b; // 107
            case 12: return 0x63; // 99
            case 11: return 0x5a; // 90
            case 10: return 0x52; // 82
            case 9: return 0x4a; // 74
            case 8: return 0x42; // 66
            case 7: return 0x39; // 57
            case 6: return 0x31; // 49
            case 5: return 0x29; // 41
            case 4: return 0x21; // 33
            case 3: return 0x18; // 24
            case 2: return 0x10; // 16
            case 1: return 0x8; // 8
            case 0: return 0x0; // 0
            default: throw new IllegalArgumentException("Number to convert can be only in range 0-31. It is " + c);
        }
    }

    public static short convertColor8to5(int c) {
        return (short) (c / 8);
    }

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
