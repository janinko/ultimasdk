package eu.janinko.Andaria.ultimasdk.files;

import java.io.File;
import java.io.IOException;
import eu.janinko.Andaria.ultimasdk.files.FileIndex.DataPack;
import eu.janinko.Andaria.ultimasdk.files.gumps.Gump;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Gumps {
	FileIndex fileIndex;
	File gumpmul;

	public Gumps(InputStream gumpidx, File gumpmul) throws IOException{
		fileIndex = new FileIndex(gumpidx, gumpmul, 0x10000);
		this.gumpmul = gumpmul;
	}
	
	public void save(OutputStream gumpidx, OutputStream gumpmul) throws IOException{
		fileIndex.save(gumpidx, gumpmul);
	}

	public Gump getGump(int index) throws IOException{
		DataPack data =  fileIndex.getData(index);
		if(data == null) return null;
		int width = (data.getExtra() >> 16) & 0xFFFF;
		int height = data.getExtra() & 0xFFFF;

		Gump gump = new Gump(width, height, data.getNewStream());

		return gump;
	}

	@Override
	public String toString() {
		return fileIndex.toString();
	}

	public void setGump(int i, Gump gump, OutputStream gumpidx) throws IOException {
		File f = new File(gumpmul.getAbsolutePath() + ".new");
		byte[] data = gump.getBitmap().writeColorLines();
		int extra = (gump.getHeight() & 0xFFFF) | ((gump.getWidth() & 0xFFFF) << 16);
		DataPack dp = new DataPack(data, extra);
		fileIndex.save(gumpidx, new FileOutputStream(f) , i, dp);
		System.out.println("Renaming: " + f.renameTo(gumpmul));
	}
}
