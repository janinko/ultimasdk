
package eu.janinko.Andaria.ultimasdk.files.graphics;

/**
 * @author janinko
 */
public class Color{
	private short color;
	public static final Color ALPHA = new Color((short) 0x8000);

	private Color(short color) {
		this.color = color;
	}

	public static Color getInstance(short color){
		return new Color(color);
	}

	public short getColor() {
		return color;
	}

	public boolean isAlpha(){
		return (color & 0x8000) == 0x8000;
	}

	public boolean isGrayscale(){
		return getBlue() == getGreen() && getGreen() == getRed();
	}

	public int getAlpha(){
		return 255 - ((color & 0x8000) >>> 15)*255;
	}

	public byte get5Blue(){
		return (byte) ((color & 0x7c00) >>> 10);
	}

	public int getBlue(){
		return convertColor5to8(get5Blue());
	}

	public byte get5Green(){
		return (byte) ((color & 0x3e0) >>> 5);
	}

	public int getGreen(){
		return convertColor5to8(get5Green());
	}

	public byte get5Red(){
		return (byte) (color & 0x1f);
	}

	public int getRed(){
		return convertColor5to8(get5Red());
	}

	public int get5Average(){
		return (get5Blue() + get5Green() + get5Red()) / 3;
	}

	public int getAverage(){
		return convertColor5to8(get5Average());
	}

	public int getAGBR(){
		return (getAlpha() << 24) + (getBlue() << 16) + (getGreen() << 8) + getRed();
	}

	public static int convertColor5to8(int c){
		return Math.round(((float) c) * 255.0f / 31.0f);
	}

	public static int convertColor8to5(int c){
		return c / 8;
	}
}
