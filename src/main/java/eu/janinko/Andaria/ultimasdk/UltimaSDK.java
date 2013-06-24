/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.janinko.Andaria.ultimasdk;

import eu.janinko.Andaria.ultimasdk.files.Gumps;
import eu.janinko.Andaria.ultimasdk.files.Hues;
import eu.janinko.Andaria.ultimasdk.files.Statics;
import eu.janinko.Andaria.ultimasdk.files.TileData;
import eu.janinko.Andaria.ultimasdk.files.hues.Hue;
import eu.janinko.Andaria.ultimasdk.files.statics.Static;
import eu.janinko.Andaria.ultimasdk.files.tiledata.ItemData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

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
		File huesmul = new File(uopath + "hues.mul");
		File staidx0 = new File(uopath + "staidx0.mul");
		File statics0 = new File(uopath + "statics0.mul");

		TileData tiledata = new TileData(new FileInputStream(tiledatamul));

		Statics statics = new Statics(staidx0, statics0);
		List<Static> sts = statics.getStaticsOnBlock(192, 256);
		for(Static s : sts){
			ItemData item = tiledata.getItem(s.getId());
			System.out.println(s + "  " + item);
		}


		/*Gumps gumps = new Gumps(gumpidx, gumpart);
		Hues hues = new Hues(new FileInputStream(huesmul));
		for(Hue hue : hues.getHues()){
		System.out.println(hue);
		}
		for(ItemData item : tiledata.getItems()){
		if(item.getFlags().contains(TileFlag.PartialHue)
		&& item.getAnimation() != 0 ){
		int animid = 0xffff & item.getAnimation();
		System.out.println(Integer.toHexString(item.getId()) +": " + item.getName()
		+ " - " + animid + " (" + item.getAnimation() + ") " + Integer.toHexString(animid)
		+ "\t" + item.getFlags().toString());
		BufferedImage image = gumps.getGump(50000 + animid).getImage();
		if(image != null){
		File out = new File("/tmp/gumps/partial/" + (50000 + animid) + ".png");
		ImageIO.write(image, "png", out);
		}
		}
		}*/
		//System.out.println(tdata);
	}
}
