/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.janinko.Andaria.ultimasdk;

import eu.janinko.Andaria.ultimasdk.files.Arts;
import eu.janinko.Andaria.ultimasdk.files.Gumps;
import eu.janinko.Andaria.ultimasdk.files.Hues;
import eu.janinko.Andaria.ultimasdk.files.Statics;
import eu.janinko.Andaria.ultimasdk.files.TileData;
import eu.janinko.Andaria.ultimasdk.files.arts.Art;
import eu.janinko.Andaria.ultimasdk.files.hues.Hue;
import eu.janinko.Andaria.ultimasdk.files.statics.Static;
import eu.janinko.Andaria.ultimasdk.files.tiledata.ItemData;
import eu.janinko.Andaria.ultimasdk.files.tiledata.TileFlag;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author jbrazdil
 */
public class UltimaSDK {
	public static final String uopath="/home/jbrazdil/Ultima/hra/";

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		File tiledatamul = new File(uopath + "tiledata.mul");
		File gumpidx = new File(uopath + "gumpidx.mul");
		File gumpart = new File(uopath + "gumpart.mul");
		File artidx = new File(uopath + "artidx.mul");
		File artmul = new File(uopath + "art.mul");
		File huesmul = new File(uopath + "hues.mul");
		File staidx0 = new File(uopath + "staidx0.mul");
		File statics0 = new File(uopath + "statics0.mul");

		TileData tiledata = new TileData(new FileInputStream(tiledatamul));
		Statics statics = new Statics(staidx0, statics0);
		Gumps gumps = new Gumps(gumpidx, gumpart);
		Hues hues = new Hues(new FileInputStream(huesmul));
		Arts arts = new Arts(artidx, artmul);

		
		doArt(arts, tiledata);
		
		
		//System.out.println(tdata);
	}

	private static void doStatic(Statics statics, TileData tiledata) throws IOException{
		List<Static> sts = statics.getStaticsOnBlock(192, 256);
		for(Static s : sts){
			ItemData item = tiledata.getItem(s.getId());
			System.out.println(s + "  " + item);
		}
	}

	private static void doHue(Hues hues){
		for(Hue hue : hues.getHues()){
			System.out.println(hue);
		}
	}

	private static void doGump(TileData tiledata, Gumps gumps) throws IOException{
		for (ItemData item : tiledata.getItems()) {
			if (item.getFlags().contains(TileFlag.PartialHue)
					&& item.getAnimation() != 0) {
				int animid = 0xffff & item.getAnimation();
				System.out.println(Integer.toHexString(item.getId()) + ": " + item.getName()
						+ " - " + animid + " (" + item.getAnimation() + ") " + Integer.toHexString(animid)
						+ "\t" + item.getFlags().toString());
				BufferedImage image = gumps.getGump(50000 + animid).getImage();
				if (image != null) {
					File out = new File("/tmp/gumps/partial/" + (50000 + animid) + ".png");
					ImageIO.write(image, "png", out);
				}
			}
		}
	}

	private static void doArt(Arts arts, TileData tiledata) throws IOException{
		for (ItemData item : tiledata.getItems()) {
			int id = item.getId();
			Art art = arts.getStatic(id);
			System.out.print(id);
			if(art == null){
				System.out.println(" x");
				continue;
			}
			BufferedImage image = art.getImage();
			if (image != null) {
				File out = new File("/tmp/arts/" + (id) + ".png");
				ImageIO.write(image, "png", out);
			}
			System.out.println(" ok");
		}
	}
}
