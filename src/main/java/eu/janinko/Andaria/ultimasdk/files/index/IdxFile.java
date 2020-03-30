package eu.janinko.Andaria.ultimasdk.files.index;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import eu.janinko.Andaria.ultimasdk.files.UOFile;

/**
 *
 * @author Honza Brázdil &lt;jbrazdil@redhat.com&gt;
 */
public abstract class IdxFile<T> implements UOFile<T>{

    protected final WritableFileIndex fileIndex;

    protected IdxFile(InputStream idx, File mulFile, int length) throws IOException {
        fileIndex = new WritableFileIndex(idx, mulFile, length);
    }

    protected IdxFile(int blockSize, File mulFile, int length) throws IOException {
        fileIndex = new WritableFileIndex(blockSize, mulFile, length);
    }

    public void save(OutputStream artidx, OutputStream artmul) throws IOException {
        fileIndex.save(artidx, artmul);
    }

    @Override
    public int count() {
        return fileIndex.size();
    }

    @Override
    public String toString() {
        return fileIndex.toString();
    }
}
