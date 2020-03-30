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

import eu.janinko.Andaria.ultimasdk.files.tiledata.TileDatum;

/**
 * @author jbrazdil
 */
public class TileData implements UOFile<TileDatum> {
    private static final int LAND_COUNT = 0x4000;
    private static final int ITEM_COUNT = 0x8000;
	private final ArrayList<LandData> landData = new ArrayList<>(LAND_COUNT);
	private final ArrayList<Integer> landHeaders = new ArrayList<>(0x400);
	private final ArrayList<ItemData> itemData = new ArrayList<>(ITEM_COUNT);
	private final ArrayList<Integer> itemHeaders = new ArrayList<>(0x800);

    public TileData(InputStream is) throws IOException {
        LittleEndianDataInputStream in = new LittleEndianDataInputStream(is);

        for (int i = 0; i < LAND_COUNT; i++) {
            if ((i & 0x1f) == 0) { // 0x1f = 31 = 0001 1111
                landHeaders.add(in.readInt());
            }

            LandData land = new LandData(in);
            land.setId(i);
            landData.add(land);
        }

        for (int i = 0; i < ITEM_COUNT; i++) {
            if ((i & 0x1f) == 0) { // 0x1f = 31 = 0001 1111
                itemHeaders.add(in.readInt());
            }

            ItemData item = new ItemData(in);
            item.setId(i);
            itemData.add(item);
        }
    }

    @Override
    public TileDatum get(int idx) {
        if (idx < LAND_COUNT) {
            return getLand(idx);
        } else {
            return getItem(idx - LAND_COUNT);
        }
    }

    @Override
    public int count() {
        return LAND_COUNT + ITEM_COUNT;
    }

	public void save(OutputStream os) throws IOException{
		LittleEndianDataOutputStream out = new LittleEndianDataOutputStream(os);

		for(int i=0; i<LAND_COUNT; i++){
			if((i & 0x1f) == 0){ // 0x1f = 31 = 0001 1111
				out.writeInt(landHeaders.get(i / 32));
			}

			landData.get(i).save(out);
		}

		for(int i=0; i<ITEM_COUNT; i++){
			if((i & 0x1f) == 0){ // 0x1f = 31 = 0001 1111
				out.writeInt(itemHeaders.get(i / 32));
			}

			itemData.get(i).save(out);
		}
	}

	public ItemData getItem(int i){
		return itemData.get(i);
	}

	public LandData getLand(int i){
		return landData.get(i);
	}

	public List<ItemData> getItems(){
		return Collections.unmodifiableList(itemData);
	}

	public List<LandData> getLands(){
		return Collections.unmodifiableList(landData);
	}

	public void setItem(int i, ItemData item) {
		itemData.set(i, item);
	}

	@Override
	public String toString() {
		return "TileData{" + "landData=\n" + landData + ",\n\n\n itemData=\n" + itemData + '}';
	}

}
