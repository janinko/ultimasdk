package eu.janinko.andaria.ultimasdk.files.anims;

import java.util.Arrays;

/**
 *
 * @author Honza Brázdil &lt;jbrazdil@redhat.com&gt;
 */
public abstract class Body {

    public static enum Direction {
        DOWN(0), SOUTH(1), LEFT(2), WEST(3), UP(4);
        private int offset;

        private Direction(int offset) {
            this.offset = offset;
        }

        public int getOffset() {
            return offset;
        }
    }
    
    public static interface Action{
        public int getOffset();
    }
    
    public Anim[] getAction(Action action){
        return anims[action.getOffset()];
    }
    
    public Anim getAnim(Action action, Direction direction){
        return anims[action.getOffset()][direction.getOffset()];
    }
    
    public abstract Action[] getActions();
    
    protected Anim[][] anims;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Arrays.deepHashCode(this.anims);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Body other = (Body) obj;
        if (!Arrays.deepEquals(this.anims, other.anims)) {
            return false;
        }
        return true;
    }
}
