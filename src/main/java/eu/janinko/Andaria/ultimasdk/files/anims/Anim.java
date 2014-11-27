package eu.janinko.Andaria.ultimasdk.files.anims;

import eu.janinko.Andaria.ultimasdk.files.graphics.Color;
import eu.janinko.Andaria.ultimasdk.utils.RandomAccessLEDataInputStream;
import java.io.IOException;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Anim{
	private Color[] palette;
	private int frameCount;
	private int[] offsets;
	private Frame[] frames;

	public Anim(RandomAccessLEDataInputStream data) throws IOException {
		palette = new Color[256];
		for(int i=0; i<256; i++){
			palette[i] = Color.getInstance(data.readShort());
		}
		int start = data.getPosition();
		frameCount = data.readInt();
		offsets = new int[frameCount];
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
		return frameCount;
	}
	
	public Frame getFrame(int i){
		return frames[i];
	}
}
