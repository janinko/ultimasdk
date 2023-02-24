package eu.janinko.andaria.ultimasdk.files.anims;

/**
 *
 * @author Honza Brázdil &lt;jbrazdil@redhat.com&gt;
 */
public class BodyLow extends Body {
    public static enum ActionLow implements Action{
        WALK(0),
        RUN(1),
        STAY(2),
        EAT(3),
        UNKNOWN1(4),
        ATTACK1(5),
        ATTACK2(6),
        HIT(7), // Attack?
        DIE1(8),
        IDDLE1(9),
        IDDLE2(10),
        MISC(11),
        DIE2(12);
        
        private final int offset;

        private ActionLow(int offset) {
            this.offset = offset;
        }

        @Override
        public int getOffset() {
            return offset;
        }
    }

    @Override
    public ActionLow[] getActions() {
        return ActionLow.values();
    }
    
    public BodyLow(){
        this.anims = new Anim[ActionLow.values().length][Direction.values().length];
    }
    
    public Anim setAnim(Anim anim, ActionLow action, Direction dir){
        return anims[action.offset][dir.getOffset()] = anim;
    }
}
