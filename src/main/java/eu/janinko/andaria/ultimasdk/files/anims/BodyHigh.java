package eu.janinko.andaria.ultimasdk.files.anims;

import java.util.Arrays;

/**
 *
 * @author Honza Brázdil &lt;jbrazdil@redhat.com&gt;
 */
public class BodyHigh extends Body {
    public static enum ActionHigh implements Action{
        WALK(0),
        STAY(1),
        DIE1(2),
        DIE2(3),
        ATTACK1(4),
        ATTACK2(5),
        ATTACK3(6),
        UNKNOWN3(7),
        UNKNOWN4(8),
        UNKNOWN5(9),
        HIT1(10), // stumble
        MISC1(11),
        MISC2(12),
        UNKNOWN9(13),
        UNKNOWN10(14),
        HIT2(15),
        HIT3(16),
        IDDLE1(17),
        IDDLE2(18),
        FLY(19),
        LAND(20),
        DIEFLY(21);
        
        private final int offset;

        private ActionHigh(int offset) {
            this.offset = offset;
        }

        @Override
        public int getOffset() {
            return offset;
        }
    }

    @Override
    public ActionHigh[] getActions() {
        return ActionHigh.values();
    }
    
    public BodyHigh(){
        this.anims = new Anim[ActionHigh.values().length][Direction.values().length];
    }
    
    public Anim setAnim(Anim anim, ActionHigh action, Direction dir){
        return anims[action.offset][dir.getOffset()] = anim;
    }
    
    /*
    
    public static enum Action{
        WALK(0),
        RUN(1),
        STAY(2),
        IDLE(3),
        UNKNOWN1(4),
        ATTACK(5),
        UNKNOWN2(6),
        UNKNOWN3(7),
        UNKNOWN4(8),
        UNKNOWN5(9),
        UNKNOWN6(10),
        UNKNOWN7(11),
        UNKNOWN8(12);
        
        private int offset;

        private Action(int offset) {
            this.offset = offset;
        }

        public int getOffset() {
            return offset;
        }
    }
    public static enum Direction{
        DOWN(0),
        SOUTH(1),
        LEFT(2),
        WEST(3),
        UP(4);
        
        private int offset;

        private Direction(int offset) {
            this.offset = offset;
        }

        public int getOffset() {
            return offset;
        }
    }
    */
}
