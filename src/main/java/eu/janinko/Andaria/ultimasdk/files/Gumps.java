package eu.janinko.Andaria.ultimasdk.files;

import java.io.File;
import java.io.IOException;
import eu.janinko.Andaria.ultimasdk.files.FileIndex.DataPack;
import eu.janinko.Andaria.ultimasdk.files.gumps.Gump;
import java.io.InputStream;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Gumps {
	FileIndex fileIndex;

	public Gumps(InputStream gumpidx, File gumpmul) throws IOException{
		fileIndex = new FileIndex(gumpidx, gumpmul, 0x10000);
	}

	public Gump getGump(int index) throws IOException{
		DataPack data =  fileIndex.getData(index);
		if(data == null) return null;
		int width = (data.getExtra() >> 16) & 0xFFFF;
		int height = data.getExtra() & 0xFFFF;

		Gump gump = new Gump(width, height, data.getNewStream());

		return gump;
	}
}
