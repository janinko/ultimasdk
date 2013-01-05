package eu.janinko.Andaria.ultimasdk.files;

import eu.janinko.Andaria.ultimasdk.LittleEndianDataInputStream;
import eu.janinko.Andaria.ultimasdk.files.hues.Hue;
import java.io.IOException;
import java.io.InputStream;
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

	public Hue getHue(int i){
		return hues.get(i);
	}

	public List<Hue> getHues(){
		return Collections.unmodifiableList(hues);
	}
}
