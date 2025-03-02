package eu.janinko.andaria.ultimasdk.files.defs;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BodyEntry implements DefEntry{
    private int animID;
    private int replacementAnimID;
    private int color;

    @Override
    public String asString() {
        return animID + " {" + replacementAnimID + "} " + color;
    }
}
