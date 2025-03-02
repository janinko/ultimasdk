package eu.janinko.andaria.ultimasdk.files.defs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EquipConvEntry implements DefEntry{
    private int bodyID;
    private int originalID;
    private int replacementID;
    private int gump;
    private int color;

    public EquipConvKey getKey(){
        return new EquipConvKey(bodyID, originalID);
    }

    @Override
    public String asString() {
        return bodyID + "\t" + originalID + "\t" + replacementID + "\t" + gump + "\t" + color;
    }

    public record EquipConvKey(int bodyID, int originalID){}
}
