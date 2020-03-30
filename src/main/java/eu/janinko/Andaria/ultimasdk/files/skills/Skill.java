package eu.janinko.Andaria.ultimasdk.files.skills;

import eu.janinko.Andaria.ultimasdk.utils.RandomAccessLEDataInputStream;
import eu.janinko.Andaria.ultimasdk.utils.RandomAccessLEDataOutputStream;
import eu.janinko.Andaria.ultimasdk.utils.Utils;

import java.io.IOException;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Skill {
	private byte isActive;
	private String name;

	public Skill(boolean isActive, String name) {
		this.isActive = (byte) (isActive ? 1 : 0);
		this.name = name;
	}

	public Skill(RandomAccessLEDataInputStream data, int length) throws IOException {
		isActive = data.readByte();
		length -= 1;

		byte[] stringBuffer = new byte[length];
		data.readFully(stringBuffer);
		name = new String(stringBuffer, Utils.chs);
	}

	@Override
	public String toString() {
		return "Skill{" + "isActive=" + isActive + ", name=" + name + '}';
	}

	public byte[] write() throws IOException {
		RandomAccessLEDataOutputStream data = new RandomAccessLEDataOutputStream();
		data.writeByte(isActive);
		data.write(name.getBytes(Utils.chs));
		return data.getArray();
	}

}
