package eu.janinko.Andaria.ultimasdk;

import eu.janinko.Andaria.ultimasdk.files.Arts;
import eu.janinko.Andaria.ultimasdk.files.Gumps;
import eu.janinko.Andaria.ultimasdk.files.Hues;
import eu.janinko.Andaria.ultimasdk.files.Statics;
import eu.janinko.Andaria.ultimasdk.files.TileData;
import eu.janinko.Andaria.ultimasdk.files.arts.Art;
import eu.janinko.Andaria.ultimasdk.files.graphics.Bitmap;
import eu.janinko.Andaria.ultimasdk.files.hues.Hue;
import eu.janinko.Andaria.ultimasdk.files.statics.Static;
import eu.janinko.Andaria.ultimasdk.files.tiledata.ItemData;
import eu.janinko.Andaria.ultimasdk.files.tiledata.TileFlag;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author jbrazdil
 */
public class UltimaSDK {
	public static final String uopath="/home/janinko/Ultima/hra/";

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

		doHueArt(arts, hues);
		//System.out.println(tdata);
	}

	private static void doStatic(Statics statics, TileData tiledata) throws IOException{
		List<Static> sts = statics.getStaticsOnBlock(192, 256);
		for(Static s : sts){
			ItemData item = tiledata.getItem(s.getId());
			System.out.println(s + "  " + item);
		}
	}

	private static void doStatic2(Statics statics, TileData tiledata) throws IOException{
		List<Static> sts = new ArrayList<Static>();
		sts.addAll(statics.getStatics(1012, 1014));
		sts.addAll(statics.getStatics(1014, 1014));
		sts.addAll(statics.getStatics(1012, 1017));
		sts.addAll(statics.getStatics(1008, 1017));
		sts.addAll(statics.getStatics(1016, 1010));
		sts.addAll(statics.getStatics(1025, 1012));
		sts.addAll(statics.getStatics(1025, 1013));
		sts.addAll(statics.getStatics(1026, 1013));
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
		for (int i = 0; i<150; i++) {
			Art art = arts.getStatic(i);
			if(art == null){
				continue;
			}
			BufferedImage image = art.getImage().getImage();
			if (image != null) {
				File out = new File("/tmp/arts/" + i + ".png");
				ImageIO.write(image, "png", out);
			}
		}
	}

	private static void doHueArt(Arts arts, Hues hues) throws IOException{
		for (int i = 0; i<150; i++) {
			Art art = arts.getStatic(i);
			if(art == null){
				continue;
			}
			Bitmap image = art.getImage();
			image.hue(hues.getHue(i), false);
			if (image != null) {
				File out = new File("/tmp/arts/" + i + ".png");
				ImageIO.write(image.getImage(), "png", out);
			}
		}
	}
}
