package eu.janinko.Andaria.ultimasdk.files;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import eu.janinko.Andaria.ultimasdk.files.cliloc.CliLoc;
import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataOutputStream;

/**
 *
 * @author Honza Brázdil &lt;jbrazdil@redhat.com&gt;
 */
public class CliLocs implements UOFile<CliLoc>{

    private final int header1;
    private final short header2;

    private final HashMap<Integer, CliLoc> entries = new HashMap<>();
    private final int count;

    public CliLocs(InputStream is) throws IOException {
        LittleEndianDataInputStream in = new LittleEndianDataInputStream(is);

        int largest = -1;
        header1 = in.readInt();
        header2 = in.readShort();
        while (in.available() > 0) {
            CliLoc cliLoc = new CliLoc(in);
            final int number = cliLoc.getNumber();
            if(entries.containsKey(number)){
                throw new IllegalArgumentException("Multiple entries with same number: " + number);
            }
            if(largest < number) {
                largest = number;
            }
            System.out.println(number);
            entries.put(number, cliLoc);
        }
        count = largest;
    }

    @Override
    public CliLoc get(int idx) {
        return entries.get(idx);
    }

    @Override
    public int count() {
        return count;
    }
    
    public java.util.Map<Integer, CliLoc> getEntries() {
        return entries;
    }

	public void save(OutputStream os) throws IOException{

		LittleEndianDataOutputStream out = new LittleEndianDataOutputStream(os);

        out.writeInt(header1);
        out.writeShort(header2);

        Iterator<CliLoc> it = entries.entrySet().stream()
                .sorted(Comparator.comparing(java.util.Map.Entry::getKey))
                .map(java.util.Map.Entry::getValue)
                .iterator();
        while(it.hasNext()) {
            it.next().save(out);
        }


    }

    public void set(CliLoc c) {
        entries.put(c.getNumber(), c);
    }

}
