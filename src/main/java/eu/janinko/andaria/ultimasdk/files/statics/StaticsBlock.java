package eu.janinko.andaria.ultimasdk.files.statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataOutputStream;
import java.util.Iterator;

/**
 *
 * @author Honza Brázdil &lt;jbrazdil@redhat.com&gt;
 */
public class StaticsBlock {
    public static final int BLOCK_SIZE = 8;

    private final int xBlock;
    private final int yBlock;

    private final List<Static> statics;

    public StaticsBlock(int xBlock, int yBlock){
        this.xBlock = xBlock;
        this.yBlock = yBlock;

        this.statics = new ArrayList<>();
    }

    public StaticsBlock(int xBlock, int yBlock, int numberOfItems, LittleEndianDataInputStream dataStream) throws IOException {
        this.xBlock = xBlock;
        this.yBlock = yBlock;

        statics = new ArrayList<>(numberOfItems);
        for(int i=0; i < numberOfItems; i++){
            statics.add(new Static(xBlock, yBlock, dataStream));
        }
    }

    public void save(LittleEndianDataOutputStream out) throws IOException {
        for (Static aStatic : statics) {
            aStatic.save(out);
        }
    }

    public int getxBlock() {
        return xBlock;
    }

    public int getyBlock() {
        return yBlock;
    }

    public List<Static> getStatics() {
        return statics;
    }

    public List<Static> getStaticsAt(int x, int y){
        return statics.stream()
                .filter(s -> s.getX() == x && s.getY() == y)
                .collect(Collectors.toList());
    }

    public void clearStaticsAt(int x, int y) {
        Iterator<Static> it = statics.iterator();
        while (it.hasNext()) {
            Static next = it.next();
            if (next.getX() == x && next.getY() == y) {
                it.remove();
            }
        }
    }

    public void addStatic(Static aStatic) {
        if (aStatic.getXBlock() != xBlock || aStatic.getYBlock() != yBlock) {
            throw new IllegalArgumentException("Static " + aStatic + "is not at block " + xBlock + "," + yBlock);
        }
        this.statics.add(aStatic);
    }

}
