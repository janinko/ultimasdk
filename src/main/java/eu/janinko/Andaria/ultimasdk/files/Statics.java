package eu.janinko.Andaria.ultimasdk.files;

import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.Andaria.ultimasdk.files.statics.Static;
import eu.janinko.Andaria.ultimasdk.utils.RandomAccessLEDataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Statics {
	private FileIndex fileIndex;

	public Statics(InputStream staidx, File statics) throws IOException{
		fileIndex = new FileIndex(staidx, statics, 0x60000);
	}

	public List<Static> getStatics(int x, int y) throws IOException{
		int xBlock = x / 8;
		int yBlock = y / 8;

		List<Static> statics = getStaticsOnBlock(xBlock, yBlock);
		Iterator<Static> it = statics.iterator();
		while(it.hasNext()){
			Static s = it.next();
			if(s.getX() != x || s.getY() != y){
				it.remove();
			}
		}

		return statics;
	}

	public List<Static> getStaticsOnBlock(int xBlock, int yBlock) throws IOException{
		int blockNumber = xBlock * 512 + yBlock;

		FileIndex.DataPack data = fileIndex.getData(blockNumber);
		if(data == null) return new ArrayList<Static>();

		int numberOfItems = data.getData().length / 7;
		List<Static> statics = new ArrayList<Static>(numberOfItems);
		RandomAccessLEDataInputStream dataStream = data.getNewStream();
		for(int i=0; i < numberOfItems; i++){
			statics.add(new Static(xBlock, yBlock, dataStream));
		}

		return statics;
	}
}
