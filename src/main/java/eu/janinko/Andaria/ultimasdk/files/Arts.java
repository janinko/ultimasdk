
package eu.janinko.Andaria.ultimasdk.files;

import eu.janinko.Andaria.ultimasdk.files.arts.Art;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author janinko
 */
public class Arts {
	private FileIndex fileIndex;

	public Arts(InputStream artidx, File artmul) throws IOException{
		fileIndex = new FileIndex(artidx, artmul, 0x10007);
	}

	private Art getArt(int index) throws IOException{
		FileIndex.DataPack data =  fileIndex.getData(index);
		if(data == null) return null;

		Art art = new Art(data.getNewStream());

		return art;
	}

	public Art getMap(int index) throws IOException{
		return getArt(index & 0xFFFF);
	}

	public Art getStatic(int index) throws IOException{
		return getArt((index + 0x4000) & 0xFFFF);
	}

	@Override
	public String toString() {
		return fileIndex.toString();
	}
}
