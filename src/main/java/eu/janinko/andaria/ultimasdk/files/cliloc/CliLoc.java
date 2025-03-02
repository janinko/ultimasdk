package eu.janinko.andaria.ultimasdk.files.cliloc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataOutputStream;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Honza Brázdil &lt;jbrazdil@redhat.com&gt;
 */
@EqualsAndHashCode
@Getter
public class CliLoc {
    private final int id;
    private final byte flag;
    @Setter
    private String text;

    public CliLoc(LittleEndianDataInputStream in) throws IOException {
        id = in.readInt();
        flag = in.readByte();
        short length = in.readShort();

        byte[] textBytes = new byte[length];
        in.readFully(textBytes);
        text = new String(textBytes, StandardCharsets.UTF_8);
    }

    public CliLoc(int number, String text) {
        final byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        if(bytes.length > Short.MAX_VALUE){
            throw new IllegalArgumentException("Entered text is too long");
        }
        this.id = number;
        this.text = text;
        this.flag = 2;
    }

    public void save(LittleEndianDataOutputStream os) throws IOException {
        os.writeInt(id);
        os.writeByte(flag);
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        os.writeShort(bytes.length);
        os.write(bytes);
    }

    @Override
    public String toString() {
        return "CliLoc{" + "number=" + id + ", flag=" + flag + ", text=" + text + '}';
    }

}
