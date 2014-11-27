package eu.janinko.Andaria.ultimasdk.files;

import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.Andaria.ultimasdk.files.hues.Hue;
import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Hues {
	private ArrayList<Hue> hues = new ArrayList<Hue>(3000);
	private ArrayList<Integer> hueHeaders = new ArrayList<Integer>(375);

	public Hues(InputStream is) throws IOException{
		LittleEndianDataInputStream in = new LittleEndianDataInputStream(is);

		for(int i=0; i<3000; i++){
			if((i & 0x07) == 0){ // 0x0f = 7 = 0000 0111
				hueHeaders.add(in.readInt());
			}

			Hue hue = new Hue(in);
			hue.setId(i);
			hues.add(hue);
		}
	}

	public void save(OutputStream os) throws IOException{
		LittleEndianDataOutputStream out = new LittleEndianDataOutputStream(os);

		for(int i=0; i<3000; i++){
			if((i & 0x07) == 0){ // 0x0f = 7 = 0000 0111
				out.writeInt(hueHeaders.get(i >> 3));
			}

			hues.get(i).save(out);
		}
	}

	public Hue getHue(int i){
		return hues.get(i-1);
	}

	public List<Hue> getHues(){
		return Collections.unmodifiableList(hues);
	}

	public void setHue(int i, Hue hue) {
		hues.set(i-1, hue);
	}
}
