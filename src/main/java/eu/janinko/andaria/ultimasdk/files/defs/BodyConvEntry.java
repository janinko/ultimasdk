package eu.janinko.andaria.ultimasdk.files.defs;

import eu.janinko.andaria.ultimasdk.files.Anims;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BodyConvEntry {
    private int animID;
    private Anims.AnimFile animFile;
    private int indexInFile;
}
