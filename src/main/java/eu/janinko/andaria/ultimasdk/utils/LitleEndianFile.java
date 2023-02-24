package eu.janinko.andaria.ultimasdk.utils;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import lombok.Getter;

/**
 *
 * @author janinko
 */
public class LitleEndianFile implements Closeable {

    private final FileChannel channel;
    @Getter
    private final MappedByteBuffer buffer;

    private LitleEndianFile(FileChannel channel, MappedByteBuffer buffer) {
        this.channel = channel;
        this.buffer = buffer;
    }

    @Override
    public void close() throws IOException {
        buffer.clear();
        channel.close();
    }

    public static LitleEndianFile readFile(Path path) throws IOException {
        FileChannel channel = FileChannel.open(path, StandardOpenOption.READ);
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return new LitleEndianFile(channel, buffer);
    }

}
