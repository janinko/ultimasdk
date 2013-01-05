package eu.janinko.Andaria.ultimasdk.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import eu.janinko.Andaria.ultimasdk.files.FileIndex.DataPack;
import eu.janinko.Andaria.ultimasdk.files.gumps.Gump;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Gumps {
	FileIndex fileIndex;

	public Gumps(File gumpIdx, File gumpMul) throws FileNotFoundException, IOException{
		fileIndex = new FileIndex(gumpIdx, gumpMul, 0x10000);
	}

	public Gump getGump(int index) throws IOException{
		DataPack data =  fileIndex.getData(index);
		if(data == null) return null;
		int width = (data.getExtra() >> 16) & 0xFFFF;
		int height = data.getExtra() & 0xFFFF;

		//System.out.println("w: " + width + " h: "+ height + " d:" + Arrays.toString(data.getData()));

		Gump gump = new Gump(width, height, data.getData());

		return gump;
		//BufferedImage image = new BufferedImage(width, height, index);
	}
}
