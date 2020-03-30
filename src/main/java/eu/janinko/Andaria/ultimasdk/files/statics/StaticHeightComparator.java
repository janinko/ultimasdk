package eu.janinko.Andaria.ultimasdk.files.statics;

import eu.janinko.Andaria.ultimasdk.files.TileData;

import java.util.Comparator;
import java.util.Objects;

import eu.janinko.Andaria.ultimasdk.files.tiledata.ItemData;
import eu.janinko.Andaria.ultimasdk.files.tiledata.TileFlag;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class StaticHeightComparator implements Comparator<Static> {
    private final TileData tiledata;

    public StaticHeightComparator(TileData tiledata) {
        this.tiledata = Objects.requireNonNull(tiledata);
    }

    @Override
    public int compare(Static o1, Static o2) {
        if (o1.getZ() != o2.getZ()) {
            return o1.getZ() - o2.getZ();
        }
        ItemData t1 = tiledata.getItem(o1.getId());
        ItemData t2 = tiledata.getItem(o2.getId());
        if (t1 == null || t2 == null) return 0;
        int t1b = t1.getFlags().contains(TileFlag.Background) ? 0 : 1;
        int t2b = t2.getFlags().contains(TileFlag.Background) ? 0 : 1;
        return t1b - t2b;
    }
    
}
