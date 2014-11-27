package eu.janinko.Andaria.ultimasdk.files;

import eu.janinko.Andaria.ultimasdk.files.FileIndex.DataPack;
import eu.janinko.Andaria.ultimasdk.files.anims.Anim;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Anims {
	public FileIndex fileIndex;
	File gumpmul;
	
	public static final int WALK=0;
	public static final int RUN=1;
	public static final int STAY=2;
	public static final int IDLE=3;
	public static final int ATTACK=5;
	
	public static final int DOWN = 0;
	public static final int SOUTH = 1;
	public static final int LEFT = 2;
	public static final int WEST = 3;
	public static final int UP = 4;

	public Anims(InputStream animidx, File animmul) throws IOException{
		fileIndex = new FileIndex(animidx, animmul, 0x10000);
		this.gumpmul = animmul;
	}
	
	public void save(OutputStream animidx, OutputStream animmul) throws IOException{
		fileIndex.save(animidx, animmul);
	}

	public Anim getAnim(int index) throws IOException{
		DataPack data =  fileIndex.getData(index);
		if(data == null) return null;

		Anim anim = new Anim(data.getNewStream());

		return anim;
	}

	public Anim getAnim1(int body, int action, int dir) throws IOException {
		int index;
		if (body < 200) {
			index = body * 110;
		} else if (body < 400) {
			index = 22000 + ((body - 200) * 65);
		} else {
			index = 35000 + ((body - 400) * 175);
		}
		index += action * 5;
		index += dir;
		return getAnim(index);
	}

	@Override
	public String toString() {
		return fileIndex.toString();
	}
}
