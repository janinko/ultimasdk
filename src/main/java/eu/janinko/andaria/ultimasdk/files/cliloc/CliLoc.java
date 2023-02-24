package eu.janinko.andaria.ultimasdk.files.cliloc;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Objects;

import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataOutputStream;

/**
 *
 * @author Honza Brázdil &lt;jbrazdil@redhat.com&gt;
 */
public class CliLoc {
    private final Charset UTF8 = Charset.forName("UTF-8");

    private final int number;
    private final byte flag;
    private final short length;
    private final String text;

    public CliLoc(LittleEndianDataInputStream in) throws IOException {
        number = in.readInt();
        flag = in.readByte();
        length = in.readShort();

        byte[] textBytes = new byte[length];
        in.readFully(textBytes);
        text = new String(textBytes, UTF8);
    }

    public CliLoc(int number, String text) {
        final byte[] bytes = text.getBytes(UTF8);
        if(bytes.length > Short.MAX_VALUE){
            throw new IllegalArgumentException("Entered text is too long");
        }
        this.number = number;
        this.text = text;
        this.length = (short) bytes.length;
        this.flag = 2;
    }

    public void save(LittleEndianDataOutputStream os) throws IOException {
        os.writeInt(number);
        os.writeByte(flag);
        os.writeShort(length);
        os.write(text.getBytes(UTF8));
    }

    public int getNumber() {
        return number;
    }

    public byte getFlag() {
        return flag;
    }

    public short getLength() {
        return length;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "CliLoc{" + "number=" + number + ", flag=" + flag + ", length=" + length + ", text=" + text + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + this.number;
        hash = 73 * hash + this.flag;
        hash = 73 * hash + this.length;
        hash = 73 * hash + Objects.hashCode(this.text);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CliLoc other = (CliLoc) obj;
        if (this.number != other.number) {
            return false;
        }
        if (this.flag != other.flag) {
            return false;
        }
        if (this.length != other.length) {
            return false;
        }
        if (!Objects.equals(this.text, other.text)) {
            return false;
        }
        return true;
    }
}
