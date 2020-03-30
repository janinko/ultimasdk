package eu.janinko.Andaria.ultimasdk.files.statics;


import java.util.Comparator;

import eu.janinko.Andaria.ultimasdk.files.TileData;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class StaticPositionComparator implements Comparator<Static> {
    private final StaticHeightComparator comp;

    public StaticPositionComparator(TileData tiledata) {
        this.comp = new StaticHeightComparator(tiledata);
    }

    @Override
    public int compare(Static o1, Static o2) {
        final int vert = (o1.getX() + o1.getY()) - (o2.getX() + o2.getY());
        if(vert != 0){
            return vert;
        }
        return comp.compare(o1, o2);
    }
    
}
