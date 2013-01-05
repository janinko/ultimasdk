package eu.janinko.Andaria.ultimasdk.files.hues;

import eu.janinko.Andaria.ultimasdk.LittleEndianDataInputStream;
import eu.janinko.Andaria.ultimasdk.Utils;
import eu.janinko.Andaria.ultimasdk.files.TileData;
import eu.janinko.Andaria.ultimasdk.files.tiledata.TileFlags;
import java.io.IOException;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public final class Hue {
	private int id;
	private int[] colors = new int[34];
	private String name;


	public Hue(LittleEndianDataInputStream in) throws IOException {
		for(int i=0; i<34; i++){
			colors[i] = in.readShort();
		}
		this.setName(Utils.readName(in));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int[] getColors() {
		return colors;
	}

	public void setColors(int[] colors) {
		this.colors = colors;
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
			if(((colors[i] & 0x8000) >>> 15) == 1){ // alpha
				sb.append('A');
			}else{
				sb.append(' ');
			}
			sb.append(' ');
			sb.append(Utils.convertColor5to8((colors[i] & 0x7c00) >>> 10)); // red
			sb.append(' ');
			sb.append(Utils.convertColor5to8((colors[i] & 0x3e0) >>> 5)); // green
			sb.append(' ');
			sb.append(Utils.convertColor5to8(colors[i] & 0x1f)); // blue
			sb.append("] ");
		}
		return sb.toString();
	}
}
