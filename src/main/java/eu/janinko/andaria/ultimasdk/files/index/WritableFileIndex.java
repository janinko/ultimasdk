package eu.janinko.andaria.ultimasdk.files.index;

import eu.janinko.andaria.ultimasdk.files.index.FileIndex;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataOutputStream;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class WritableFileIndex extends FileIndex{
    private HashMap<Integer, DataPack> overrides = new HashMap<>();

    public WritableFileIndex(InputStream idxStream, File mulFile, int length) throws IOException {
        super(idxStream, mulFile, length);
    }

    public WritableFileIndex(int blockSize, File mulFile, int length) throws IOException {
        super(blockSize, mulFile, length);
    }

    public void set(int index, DataPack data){
        overrides.put(index, data);
    }

    @Override
    public DataPack getData(int i) throws IOException {
        if(overrides.containsKey(i)){
            return overrides.get(i);
        }else{
            return super.getData(i);
        }
    }

    @Override
    public void save(OutputStream idxStream, OutputStream mulFile) throws IOException {
        try (LittleEndianDataOutputStream out = new LittleEndianDataOutputStream(idxStream)) {
            int offset = 0;
            for (int i = 0; i < index.size(); i++) {
                if (overrides.containsKey(i)) {
                    DataPack data = overrides.get(i);
                    if (data == null || data.getData().length == 0) {
                        out.writeInt(-1);
                        out.writeInt(0);
                        out.writeInt(0);
                    } else {
                        out.writeInt(offset);
                        out.writeInt(data.getData().length);
                        out.writeInt(data.getExtra());
                        mulFile.write(data.getData());
                        offset += data.getData().length;
                    }
                } else {
                    offset += saveDatum(i, offset, out, mulFile);
                }
            }
        } finally {
            mulFile.close();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<index.size(); i++){
            sb.append(i).append(": ");
            if(overrides.containsKey(i)){
                sb.append("override ");
                DataPack data = overrides.get(i);
                sb.append("length=").append(data == null ? 0 : data.getData().length).append(" extra=").append(data == null ? 0 : data.getExtra());
            }else{
                sb.append(index.get(i));
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
