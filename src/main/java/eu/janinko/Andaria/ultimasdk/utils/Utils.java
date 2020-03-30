package eu.janinko.Andaria.ultimasdk.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Utils {
	public static final Charset chs = Charset.forName("Windows-1250");
	public static String readName(InputStream in) throws IOException{
		byte[] stringBuffer = new byte[20];
		in.read(stringBuffer);
		return new String(stringBuffer, chs).trim();
	}

	public static void writeName(OutputStream out, String name) throws IOException {
		byte[] stringBuffer = new byte[20];
		int i=0;
		for(byte b : name.getBytes(chs)){
			if(i>=20) break;
			stringBuffer[i++]=b;
		}
		out.write(stringBuffer, 0, 20);
	}
}
