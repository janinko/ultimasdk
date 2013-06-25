
package eu.janinko.Andaria.ultimasdk.files;

import eu.janinko.Andaria.ultimasdk.files.arts.Art;
import eu.janinko.Andaria.ultimasdk.files.gumps.Gump;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author janinko
 */
public class Arts {
	private FileIndex fileIndex;

	public Arts(File artidx, File artmul) throws FileNotFoundException, IOException{
		fileIndex = new FileIndex(artidx, artmul, 0x10000);
	}

	public Art getArt(int index) throws IOException{
		FileIndex.DataPack data =  fileIndex.getData(index);
		if(data == null) return null;


		Art art = new Art(data.getData());

		return art;
		//BufferedImage image = new BufferedImage(width, height, index);
	}
}
