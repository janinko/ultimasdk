package eu.janinko.Andaria.ultimasdk.files;


import java.io.File;
import java.io.IOException;

import eu.janinko.Andaria.ultimasdk.files.index.FileIndex.DataPack;
import eu.janinko.Andaria.ultimasdk.files.gumps.Gump;

import java.io.InputStream;

import eu.janinko.Andaria.ultimasdk.files.index.IdxFile;
import eu.janinko.Andaria.ultimasdk.graphics.BitmapWriter;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Gumps extends IdxFile<Gump> {
    public static final int GUMPS_COUNT=0x10000;

	public Gumps(InputStream gumpidx, File gumpmul) throws IOException{
		super(gumpidx, gumpmul, GUMPS_COUNT);
	}

    @Override
	public Gump get(int index) throws IOException{
		DataPack data =  fileIndex.getData(index);
		if(data == null) return null;
		int width = (data.getExtra() >> 16) & 0xFFFF;
		int height = data.getExtra() & 0xFFFF;

		Gump gump = new Gump(width, height, data.getNewStream());

		return gump;
	}
    
    public boolean isGump(int index) {
        return fileIndex.isData(index);
    }

	public void setGump(int i, Gump gump) throws IOException {
		byte[] data = BitmapWriter.writeColorLines(gump.getBitmap());
		int extra = (gump.getHeight() & 0xFFFF) | ((gump.getWidth() & 0xFFFF) << 16);
		DataPack dp = new DataPack(data, extra);
        fileIndex.set(i, dp);
	}
}
