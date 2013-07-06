/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
	private static final Charset chs = Charset.forName("Windows-1250");
	public static String readName(InputStream in) throws IOException{
		byte[] stringBuffer = new byte[20];
		in.read(stringBuffer);
		return new String(stringBuffer, chs);
	}

	public static void writeName(OutputStream out, String name) throws IOException {
		out.write(name.getBytes(chs), 0, 20);
	}
}
