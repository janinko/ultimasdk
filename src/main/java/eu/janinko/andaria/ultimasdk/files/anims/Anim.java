package eu.janinko.andaria.ultimasdk.files.anims;

import eu.janinko.andaria.ultimasdk.graphics.Color;
import eu.janinko.andaria.ultimasdk.utils.RandomAccessLEDataInputStream;

import java.io.IOException;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
@EqualsAndHashCode
public class Anim{
    private Color[] palette;
    private Frame[] frames;

    public Anim(RandomAccessLEDataInputStream data) throws IOException {
        palette = new Color[256];
        for(int i=0; i<256; i++){
            palette[i] = Color.getInstance(data.readShort());
        }
        int start = data.getPosition();
        int frameCount = data.readInt();
        int[] offsets = new int[frameCount];
        for(int i=0; i<frameCount; i++){
            offsets[i] = data.readInt() + start;
        }
        frames = new Frame[frameCount];
        for(int i=0; i<frameCount; i++){
            data.seek(offsets[i]);
            try{
                frames[i] = new Frame(data, palette);
            }catch(IllegalArgumentException ex){
                System.out.println("Bad frame: " + ex.getMessage());
            }
        }
    }
    
    public int frameCount(){
        return frames.length;
    }
    
    public Frame getFrame(int i){
        return frames[i];
    }
}
