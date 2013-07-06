package eu.janinko.Andaria.ultimasdk.files.hues;

import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.Andaria.ultimasdk.utils.Utils;
import eu.janinko.Andaria.ultimasdk.files.graphics.Color;
import java.io.IOException;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public final class Hue {
	private int id;
	private Color[] colors = new Color[32];
	private short tableStart;
	private short tableEnd;
	private String name;


	public Hue(LittleEndianDataInputStream in) throws IOException {
		for(int i=0; i<32; i++){
			colors[i] = Color.getInstance(in.readShort());
		}
		tableStart = in.readShort();
		tableEnd = in.readShort();
		this.setName(Utils.readName(in));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Color[] getColors() {
		return colors;
	}

	public void setColors(Color[] colors) {
		this.colors = colors;
	}

	public Color getColor(int id) {
		return colors[id];
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Hue ");
		sb.append(id);
		sb.append(": ");
		sb.append(name);
		sb.append(' ');
		for(int i=0; i<32; i++){
			sb.append('[');
			if(colors[i].isAlpha()){ // alpha
				sb.append('A');
			}else{
				sb.append(' ');
			}
			sb.append(' ');
			sb.append(colors[i].getRed()); // red
			sb.append(' ');
			sb.append(colors[i].getGreen()); // green
			sb.append(' ');
			sb.append(colors[i].getBlue()); // blue
			sb.append("] ");
		}
		return sb.toString();
	}
}
