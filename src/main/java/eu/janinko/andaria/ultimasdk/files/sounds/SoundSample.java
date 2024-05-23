package eu.janinko.andaria.ultimasdk.files.sounds;

import eu.janinko.andaria.ultimasdk.utils.RandomAccessLEDataInputStream;
import eu.janinko.andaria.ultimasdk.utils.Utils;
import lombok.Data;

import java.io.IOException;

@Data
public class SoundSample {

    private int index;
    private int reserved;

    private final String name;
    private final int unknown1;
    private final int unknown2;
    private final int unknown3;
    private final int unknown4;
    private final byte[] data;

    public SoundSample(int index, int reserved, RandomAccessLEDataInputStream input, int dataLenghth) throws IOException {
        this.index = index;
        this.reserved = reserved;

        name = Utils.readName(input, 16);
        unknown1 = input.readInt();
        unknown2 = input.readInt();
        unknown3 = input.readInt();
        unknown4 = input.readInt();

        data = new byte[dataLenghth];
        input.read(data);
    }

    @Override
    public String toString() {
        return "SoundSample{" +
                "index=" + index +
                ", reserved=" + reserved +
                ", name='" + name + '\'' +
                ", unknown1=" + unknown1 +
                ", unknown2=" + unknown2 +
                ", unknown3=" + unknown3 +
                ", unknown4=" + unknown4 +
                '}';
    }
}
