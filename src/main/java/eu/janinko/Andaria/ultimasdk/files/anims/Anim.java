package eu.janinko.Andaria.ultimasdk.files.anims;

import eu.janinko.Andaria.ultimasdk.graphics.Color;
import eu.janinko.Andaria.ultimasdk.utils.RandomAccessLEDataInputStream;

import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Arrays.deepHashCode(this.palette);
        hash = 89 * hash + Arrays.deepHashCode(this.frames);
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
        final Anim other = (Anim) obj;
        if (!Arrays.deepEquals(this.palette, other.palette)) {
            return false;
        }
        if (!Arrays.deepEquals(this.frames, other.frames)) {
            return false;
        }
        return true;
    }
}
