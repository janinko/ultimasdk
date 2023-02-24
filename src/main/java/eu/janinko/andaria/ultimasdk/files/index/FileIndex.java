package eu.janinko.andaria.ultimasdk.files.index;

import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataOutputStream;
import eu.janinko.andaria.ultimasdk.utils.RandomAccessLEDataInputStream;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class FileIndex {
    protected ArrayList<Entry3D> index;

    private RandomAccessFile mulData;

    public FileIndex(InputStream idxStream, File mulFile, int length) throws IOException{
        index = new ArrayList<>(length);

        try(LittleEndianDataInputStream idxData = new LittleEndianDataInputStream(idxStream)){
            mulData = new RandomAccessFile(mulFile,"r");

            boolean reading = true;
            while(reading){
                try{
                    int offset = idxData.readInt();
                    int len = idxData.readInt();
                    int extra = idxData.readInt();
                    index.add(new Entry3D(offset, len, extra));
                }catch(EOFException ex){
                    reading = false;
                }
            }
        }
    }

    public FileIndex(int blockSize, File mulFile, int length) throws IOException{
        index = new ArrayList<>(length);

        mulData= new RandomAccessFile(mulFile,"r");

        for(int i=0; i < length; i++){
            index.add(new Entry3D(i*blockSize, blockSize, 0));
        }
    }

    public boolean isData(int i){
        return index.get(i).length > 0;
    }

    public DataPack getData(int i) throws IOException{
        Entry3D entry = index.get(i);
        if(entry.length <= 0) return null;
        //System.out.println(i+": l=" + entry.length+ " o="+ entry.offset + " e=" + entry.extra);
        byte[] data = new byte[entry.length];
        mulData.seek(entry.offset);
        mulData.readFully(data);
        return new DataPack(data, entry.extra);
    }

    @Override
    public String toString() {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for(Entry3D e : index){
            sb.append(i++).append(": ").append(e).append('\n');
        }
        return sb.toString();
    }

    public void save(OutputStream idxStream, OutputStream mulFile) throws IOException {
        LittleEndianDataOutputStream out = new LittleEndianDataOutputStream(idxStream);
        int offset = 0;
        for(int i=0; i<index.size(); i++){
            offset += saveDatum(i, offset, out, mulFile);
        }
    }

    public int size(){
        return index.size();
    }

    protected int saveDatum(int i, int offset, LittleEndianDataOutputStream out, OutputStream mulFile) throws IOException {
        Entry3D entry = index.get(i);
        if (entry.length == 0 || entry.length == -1) {
                if(-1 != entry.offset){
                    //System.out.println(offset + " : " + entry.offset);
                }
            out.writeInt(-1);
        } else {
                if(offset != entry.offset){
                    //System.out.println(offset + " : " + entry.offset);
                }
            out.writeInt(offset);
        }
        out.writeInt(entry.length);
        out.writeInt(entry.extra);
        DataPack data = getData(i);
        if (data == null) {
            return 0;
        }
        mulFile.write(data.data);
        return data.data.length;
    }

    public static class DataPack{
        private final byte[] data;
        private final int extra;

        public DataPack(byte[] data, int extra) {
            this.data = data;
            this.extra = extra;
        }

        public byte[] getData() {
            return data;
        }

        public RandomAccessLEDataInputStream getNewStream(){
            return new RandomAccessLEDataInputStream(data);
        }

        public int getExtra() {
            return extra;
        }
    }

    private static class Entry3D{
        private final int offset;
        private final int length;
        private final int extra;

        public Entry3D(int offset, int length, int extra) {
            this.offset = offset;
            this.length = length;
            this.extra = extra;
        }

        @Override
        public String toString() {
            return "Entry3D{" + "offset=" + offset + ", length=" + length + ", extra=" + extra + '}';
        }
    }

    public void close() throws IOException{
        mulData.close();
    }

}
