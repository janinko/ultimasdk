package eu.janinko.Andaria.ultimasdk.files.statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;

/**
 *
 * @author Honza Brázdil &lt;jbrazdil@redhat.com&gt;
 */
public class StaticsBlock {

    private final int xBlock;
    private final int yBlock;

    private final List<Static> statics;

    public StaticsBlock(int xBlock, int yBlock){
        this.xBlock = xBlock;
        this.yBlock = yBlock;

        this.statics = Collections.emptyList();
    }

    public StaticsBlock(int xBlock, int yBlock, int numberOfItems, LittleEndianDataInputStream dataStream) throws IOException {
        this.xBlock = xBlock;
        this.yBlock = yBlock;

		statics = new ArrayList<>(numberOfItems);
		for(int i=0; i < numberOfItems; i++){
			statics.add(new Static(xBlock, yBlock, dataStream));
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

}
