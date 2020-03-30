package eu.janinko.Andaria.ultimasdk.files;

import java.io.IOException;

/**
 *
 * @author Honza Brázdil &lt;jbrazdil@redhat.com&gt;
 * @param <T> Type of the UO asset.
 */
public interface UOFile<T> {

    /**
     * Gets UO asset from file.
     * @param idx index of the asset.
     * @return The UO asset or null if it's not present.
     * @throws IOException When there was problems reading the resassetource from file.
     */
    T get(int idx) throws IOException;

    /**
     * Gets the total number of available positions in the UO file.
     * @return number of available positions.
     */
    int count();
}
