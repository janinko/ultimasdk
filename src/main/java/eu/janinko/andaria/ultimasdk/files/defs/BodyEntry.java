package eu.janinko.andaria.ultimasdk.files.defs;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BodyEntry {
    private int animID;
    private int replacementAnimID;
    private int color;
}
