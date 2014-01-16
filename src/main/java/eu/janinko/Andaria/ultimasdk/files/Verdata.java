
package eu.janinko.Andaria.ultimasdk.files;

import eu.janinko.Andaria.ultimasdk.files.tiledata.LandData;
import eu.janinko.Andaria.ultimasdk.files.verdata.Verdato;
import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author janinko
 */
public class Verdata {
	private ArrayList<Verdato> verdata = new ArrayList<Verdato>();
	private int count;

	public Verdata(InputStream is) throws IOException{
		LittleEndianDataInputStream in = new LittleEndianDataInputStream(is);

		count = in.readInt();
		verdata.ensureCapacity(count);

		for(int i=0; i<count; i++){
			verdata.add(new Verdato(in));
		}
	}

	public Verdato getVerdato(int i){
		return verdata.get(i);
	}

	public List<Verdato> getVerdata(){
		return Collections.unmodifiableList(verdata);
	}
}
