package eu.janinko.andaria.ultimasdk.files.defs;

import eu.janinko.andaria.ultimasdk.files.Anims;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BodyConvEntry implements DefEntry{
    private int animID;
    private Anims.AnimFile animFile;
    private int indexInFile;

    @Override
    public String asString() {
        int idx = switch (animFile){
            case ANIM2 -> 0;
            case ANIM3 -> 1;
            case ANIM4 -> 2;
            case ANIM5 -> 3;
            default -> throw new IllegalStateException("Unexpected value: " + animFile);
        };

        String ret = animID + "";
        for(int i=0; i< 4; i++){
            if(i == idx){
                ret += "\t" + indexInFile;
            }else {
                ret += "\t-1";
            }
        }
        return ret;
    }
}
