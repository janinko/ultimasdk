package eu.janinko.Andaria.ultimasdk;

import eu.janinko.Andaria.ultimasdk.files.Statics;
import eu.janinko.Andaria.ultimasdk.files.TileData;
import eu.janinko.Andaria.ultimasdk.files.statics.Static;
import eu.janinko.Andaria.ultimasdk.files.tiledata.ItemData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class StaticCounter {
	public static final String uopath="/home/jbrazdil/Ultima/hra/";
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		File tiledatamul = new File(uopath + "tiledata.mul");
		File staidx0 = new File(uopath + "staidx0.mul");
		File statics0 = new File(uopath + "statics0.mul");

		TileData tiledata = new TileData(new FileInputStream(tiledatamul));
		Statics statics = new Statics(staidx0, statics0);
		
		int startX = 192*8;
		int startY = 256*8;
		int stopX = 195*8;
		int stopY = 260*8;

		List<Static> sts = new ArrayList<Static>();
		for (int x = startX / 8; x < stopX / 8; x++){
			for(int y = startY / 8; y < stopY / 8; y++){
				sts.addAll(statics.getStaticsOnBlock(192, 256));
			}
		}

		HashMap<ItemData, Integer> items = new HashMap<ItemData, Integer>();
		for(Static s : sts){
			if(s.getX() < startX || s.getY() < startY || s.getX() >= stopX || s.getY() >= stopY){
				continue;
			}
			ItemData item = tiledata.getItem(s.getId());
			System.out.println(s.getId() + "\t" + s.getX() + "\t" + s.getY() + "\t" + s.getZ() + "\t" + item.getName() );
			Integer i = items.get(item);
			if(i == null){
				items.put(item, 1);
			}else{
				items.put(item, i + 1);
			}
		}

		for(Entry<ItemData, Integer> e : items.entrySet()){
			System.out.println(e.getKey().getId() + "\t" + e.getKey().getName() + "\t" + e.getValue());
		}

	}
}
