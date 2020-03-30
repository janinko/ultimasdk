package eu.janinko.Andaria.ultimasdk.files;

import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.Andaria.ultimasdk.graphics.Color;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author janinko
 */
public class Radarcol implements UOFile<Color> {
	private ArrayList<Color> colors = new ArrayList<>(0x10000);

	public Radarcol(InputStream is) throws IOException {
		LittleEndianDataInputStream in = new LittleEndianDataInputStream(is);

		for(int i = 0; i < 0x10000; i++){
			colors.add(Color.getInstance(in.readShort()));
		}
	}

    @Override
    public Color get(int idx) {
		return colors.get(idx);
    }

    @Override
    public int count() {
        return colors.size();
    }

	public Color getMapColor(int index) {
		return get(index);
	}

	public Color getStaticColor(int index) {
		return get(index + 0x4000);
	}
}
