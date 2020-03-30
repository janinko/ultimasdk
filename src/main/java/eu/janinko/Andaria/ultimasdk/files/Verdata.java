
package eu.janinko.Andaria.ultimasdk.files;

import eu.janinko.Andaria.ultimasdk.files.verdata.Verdatum;
import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author janinko
 */
public class Verdata implements UOFile<Verdatum> {
	private final ArrayList<Verdatum> verdata = new ArrayList<>();
	private final int count;

	public Verdata(InputStream is) throws IOException{
		LittleEndianDataInputStream in = new LittleEndianDataInputStream(is);

		count = in.readInt();
		verdata.ensureCapacity(count);

		for(int i=0; i<count; i++){
			verdata.add(new Verdatum(in));
		}
	}

    @Override
    public Verdatum get(int idx) {
        return verdata.get(idx);
    }

    @Override
    public int count() {
        return count;
    }

	public List<Verdatum> getVerdata(){
		return Collections.unmodifiableList(verdata);
	}
}
