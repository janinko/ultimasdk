package eu.janinko.andaria.ultimasdk.files.index;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import eu.janinko.andaria.ultimasdk.files.UOFile;
import java.io.Closeable;

/**
 *
 * @author Honza Brázdil &lt;jbrazdil@redhat.com&gt;
 */
public abstract class IdxFile<T> implements UOFile<T>, Closeable{

    protected final WritableFileIndex fileIndex;

    protected IdxFile(InputStream idx, File mulFile, int length) throws IOException {
        fileIndex = new WritableFileIndex(idx, mulFile, length);
    }

    protected IdxFile(int blockSize, File mulFile, int length) throws IOException {
        fileIndex = new WritableFileIndex(blockSize, mulFile, length);
    }

    public void save(OutputStream idx, OutputStream mul) throws IOException {
        fileIndex.save(idx, mul);
    }

    @Override
    public int count() {
        return fileIndex.size();
    }

    @Override
    public String toString() {
        return fileIndex.toString();
    }

    @Override
    public void close() throws IOException{
        fileIndex.close();
    }
}
