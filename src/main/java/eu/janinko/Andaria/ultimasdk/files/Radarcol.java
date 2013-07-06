package eu.janinko.Andaria.ultimasdk.files;

import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.Andaria.ultimasdk.files.graphics.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author janinko
 */
public class Radarcol {
	private ArrayList<Color> colors = new ArrayList<Color>(0x10000);

	public Radarcol(InputStream is) throws IOException {
		LittleEndianDataInputStream in = new LittleEndianDataInputStream(is);

		for(int i = 0; i < 0x10000; i++){
			colors.add(Color.getInstance(in.readShort()));
		}
	}

	public Color getMapColor(int index) {
		return colors.get(index);
	}

	public Color getStaticColor(int index) {
		return colors.get(index + 0x4000);
	}
}
