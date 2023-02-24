package eu.janinko.andaria.ultimasdk.files.anims;

/**
 *
 * @author Honza Brázdil &lt;jbrazdil@redhat.com&gt;
 */
public class BodyChar extends Body {
    public static enum ActionChar implements Action{
        WALK_UNARMED(0),
        WALK_ARMED(1),
        RUN_UNARMED(2),
        RUN_ARMED(3),
        STAND(4),
        IDLE1(5),
        IDLE2(6),
        STAND_ONEHANDED(7),
        STAND_TWOHANDED(8),
        ATTACK_ONEHANDED(9),
        ATTACK_UNARMED1(10),
        ATTACK_UNARMED2(11),
        ATTACK_TWOHANDED1(12),
        ATTACK_TWOHANDED2(13),
        ATTACK_TWOHANDED3(14),
        WALK_WARMODE(15),
        CAST_DIRECTED(16),
        CAST_SUMMON(17),
        ATTACK_BOW(18),
        ATTACK_CROSBOW(19),
        HIT(20),
        DIE1(20),
        DIE2(22),
        RIDE_SLOW(23),
        RIDE_FAST(24),
        RIDE_STAND(25),
        RIDE_ATTACK(26),
        RIDE_BOW(27),
        RIDE_CROSBOW(28),
        RIDE_SLAP(29),
        TURN(30),
        ATTACK_UNARMED3(31),
        BOW(32),
        SALUTE(33),
        IDLE3(34);
        
        private final int offset;

        private ActionChar(int offset) {
            this.offset = offset;
        }

        @Override
        public int getOffset() {
            return offset;
        }
    }

    @Override
    public ActionChar[] getActions() {
        return ActionChar.values();
    }
    
    public BodyChar(){
        this.anims = new Anim[ActionChar.values().length][Direction.values().length];
    }
    
    public Anim setAnim(Anim anim, ActionChar action, Direction dir){
        return anims[action.offset][dir.getOffset()] = anim;
    }
}
