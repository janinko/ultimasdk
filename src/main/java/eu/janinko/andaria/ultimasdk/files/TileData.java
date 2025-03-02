package eu.janinko.andaria.ultimasdk.files;

import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataOutputStream;
import eu.janinko.andaria.ultimasdk.files.tiledata.ItemData;
import eu.janinko.andaria.ultimasdk.files.tiledata.LandData;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eu.janinko.andaria.ultimasdk.files.tiledata.TileDatum;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author jbrazdil
 */
public class TileData implements UOFile<TileDatum> {
    private static final int LAND_COUNT = 0x4000;
    private static final int ITEM_COUNT = 0x10000;
    private final ArrayList<LandData> landData = new ArrayList<>(LAND_COUNT);
    private final ArrayList<Integer> landHeaders = new ArrayList<>(0x400);
    private final ArrayList<ItemData> itemData = new ArrayList<>(ITEM_COUNT);
    private final ArrayList<Integer> itemHeaders = new ArrayList<>(0x1000);

    private TileData(InputStream is) throws IOException {
        LittleEndianDataInputStream in = new LittleEndianDataInputStream(is);

        for (int i = 0; i < LAND_COUNT; i++) {
            if ((i & 0x1f) == 0) { // 0x1f = 31 = 0001 1111
                landHeaders.add(in.readInt());
            }

            LandData land = new LandData(in);
            land.setId(i);
            landData.add(land);
        }

        boolean reading = true;
        int i=0;
        while(reading){
            try{
                if ((i & 0x1f) == 0) { // 0x1f = 31 = 0001 1111
                    itemHeaders.add(in.readInt());
                }
                ItemData item = new ItemData(in);
                item.setId(i);
                itemData.add(item);
                i++;
            }catch(EOFException ex){
                reading = false;
            }
        }
    }

    public static TileData load(Path file) throws IOException {
        try (InputStream input = Files.newInputStream(file)) {
            return new TileData(input);
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
        try (LittleEndianDataOutputStream out = new LittleEndianDataOutputStream(os)) {

            for (int i = 0; i < LAND_COUNT; i++) {
                if ((i & 0x1f) == 0) { // 0x1f = 31 = 0001 1111
                    out.writeInt(landHeaders.get(i / 32));
                }

                landData.get(i).save(out);
            }

            for (int i = 0; i < itemData.size(); i++) {
                if ((i & 0x1f) == 0) { // 0x1f = 31 = 0001 1111
                    out.writeInt(itemHeaders.get(i / 32));
                }

                itemData.get(i).save(out);
            }
        }
    }

    public ItemData getItem(int i){
        if (i > ITEM_COUNT) {
            throw new IllegalArgumentException("Item index " + i + " too large.");
        }
        if (i >= itemData.size()) {
            return new ItemData(i);
        }
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
        if (i > ITEM_COUNT) {
            throw new IllegalArgumentException("Item index " + i + " too large.");
        }
        if (i >= itemData.size()) {
            for (int j = itemData.size(); j < i; j++) { // Fill missing slots with empty data
                if ((j & 0x1f) == 0) { // 0x1f = 31 = 0001 1111
                    itemHeaders.add(0); // TODO: not sure
                }
                itemData.add(new ItemData(i));
            }
            itemData.add(item);
        } else {
            itemData.set(i, item);
        }
    }

    @Override
    public String toString() {
        return "TileData{" + "landData=\n" + landData + ",\n\n\n itemData=\n" + itemData + '}';
    }

}
