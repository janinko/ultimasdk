package eu.janinko.Andaria.ultimasdk.files;

import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.Andaria.ultimasdk.utils.LittleEndianDataOutputStream;
import eu.janinko.Andaria.ultimasdk.files.tiledata.ItemData;
import eu.janinko.Andaria.ultimasdk.files.tiledata.LandData;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author jbrazdil
 */
public class TileData {

	private ArrayList<LandData> landData = new ArrayList<LandData>(0x4000);
	private ArrayList<Integer> landHeaders = new ArrayList<Integer>(0x400);
	private ArrayList<ItemData> itemData = new ArrayList<ItemData>(0x8000);
	private ArrayList<Integer> itemHeaders = new ArrayList<Integer>(0x800);

	public TileData(InputStream is) throws IOException{
		LittleEndianDataInputStream in = new LittleEndianDataInputStream(is);

		for(int i=0; i<0x4000; i++){
			if((i & 0x1f) == 0){ // 0x1f = 31 = 0001 1111
				landHeaders.add(in.readInt());
			}

			LandData land = new LandData(in);
			land.setId(i);
			landData.add(land);
		}

		for(int i=0; i<0x8000; i++){
			if((i & 0x1f) == 0){ // 0x1f = 31 = 0001 1111
				itemHeaders.add(in.readInt());
			}

			ItemData item = new ItemData(in);
			item.setId(i);
			itemData.add(item);
		}
	}

	public void save(OutputStream os) throws IOException{
		LittleEndianDataOutputStream out = new LittleEndianDataOutputStream(os);

		for(int i=0; i<0x4000; i++){
			if((i & 0x1f) == 0){ // 0x1f = 31 = 0001 1111
				out.writeInt(landHeaders.get(i / 32));
			}

			landData.get(i).save(out);
		}

		for(int i=0; i<0x8000; i++){
			if((i & 0x1f) == 0){ // 0x1f = 31 = 0001 1111
				out.writeInt(itemHeaders.get(i / 32));
			}

			itemData.get(i).save(out);
		}
	}

	

	public ItemData getItem(int i){
		return itemData.get(i);
	}

	public List<ItemData> getItems(){
		return Collections.unmodifiableList(itemData);
	}



	@Override
	public String toString() {
		return "TileData{" + "landData=\n" + landData + ",\n\n\n itemData=\n" + itemData + '}';
	}

}
