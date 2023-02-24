
package eu.janinko.andaria.ultimasdk.files;

import eu.janinko.andaria.ultimasdk.files.verdata.Verdatum;
import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author janinko
 */
public class Verdata implements UOFile<Verdatum> {
    private final ArrayList<Verdatum> verdata = new ArrayList<>();
    private final int count;

    private Verdata(InputStream is) throws IOException {
        LittleEndianDataInputStream in = new LittleEndianDataInputStream(is);

        count = in.readInt();
        verdata.ensureCapacity(count);

        for(int i=0; i<count; i++){
            verdata.add(new Verdatum(in));
        }
    }

    public static Verdata load(Path file) throws IOException {
        try (InputStream input = Files.newInputStream(file)) {
            return new Verdata(input);
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
