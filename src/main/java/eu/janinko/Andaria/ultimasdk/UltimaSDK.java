package eu.janinko.Andaria.ultimasdk;

import eu.janinko.Andaria.ultimasdk.files.Anims;
import eu.janinko.Andaria.ultimasdk.files.Arts;
import eu.janinko.Andaria.ultimasdk.files.FileIndex;
import eu.janinko.Andaria.ultimasdk.files.Gumps;
import eu.janinko.Andaria.ultimasdk.files.Hues;
import eu.janinko.Andaria.ultimasdk.files.Statics;
import eu.janinko.Andaria.ultimasdk.files.TileData;
import eu.janinko.Andaria.ultimasdk.files.Verdata;
import eu.janinko.Andaria.ultimasdk.files.anims.Anim;
import eu.janinko.Andaria.ultimasdk.files.anims.Frame;
import eu.janinko.Andaria.ultimasdk.files.arts.Art;
import eu.janinko.Andaria.ultimasdk.files.graphics.Bitmap;
import eu.janinko.Andaria.ultimasdk.files.graphics.Color;
import eu.janinko.Andaria.ultimasdk.files.gumps.Gump;
import eu.janinko.Andaria.ultimasdk.files.hues.Hue;
import eu.janinko.Andaria.ultimasdk.files.statics.Static;
import eu.janinko.Andaria.ultimasdk.files.tiledata.ItemData;
import eu.janinko.Andaria.ultimasdk.files.tiledata.LandData;
import eu.janinko.Andaria.ultimasdk.files.tiledata.TileFlag;
import eu.janinko.Andaria.ultimasdk.files.verdata.Verdato;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
		File tiledatamul2 = new File(uopath + "tiledata.mul");
		File gumpidx = new File(uopath + "gumpidx.mul");
		File gumpart = new File(uopath + "gumpart.mul");
		File animidx = new File(uopath + "anim.idx");
		File animmul = new File(uopath + "anim.mul");
		File artidx = new File(uopath + "artidx.mul");
		File artmul = new File(uopath + "art.mul");
		File huesmul = new File(uopath + "hues.mul");
		File huesmul2 = new File("/home/janinko/Stažené/hues.mul");
		File staidx0 = new File(uopath + "staidx0.mul");
		File statics0 = new File(uopath + "statics0.mul");
		//File verdatamul = new File(uopath + "verdata.mul");

		TileData tiledata = new TileData(new FileInputStream(tiledatamul));
		TileData tiledata2 = new TileData(new FileInputStream(tiledatamul2));
		Statics statics = new Statics(new FileInputStream(staidx0), statics0);
		Gumps gumps = new Gumps(new FileInputStream(gumpidx), gumpart);
		Hues hues = new Hues(new FileInputStream(huesmul));
		Hues hues2 = new Hues(new FileInputStream(huesmul2));
		Arts arts = new Arts(new FileInputStream(artidx), artmul);
		Anims anims = new Anims(new FileInputStream(animidx), animmul);
		//Verdata verdata = new Verdata(new FileInputStream(verdatamul));

		//System.out.println(gumps);
		//doTiledata(tiledata);
		//doVerdata(verdata);
		//doHue(hues);
        //generateHueStripes(hues);
		//generatePaperdolGumpImages(tiledata, gumps);
        generateArtImages(arts, tiledata);
		//testBitmap(gumps);
		//allColorGump(gumps, new FileOutputStream(gumpidx));
		//twoColorGump(gumps, new FileOutputStream(gumpidx));
		//multiColorGump(gumps, new FileOutputStream(gumpidx));
		//doAnim(anims);
		//copyGumps(gumps);
		//System.out.println(tdata);
		//System.out.println(hues.getHue(2971));
		//hues.setHue(2971,hues2.getHue(2971));
		//saveHue(hues);
		///System.out.println(hues.getHue(2971));
	}

	private static void doStatic(Statics statics, TileData tiledata) throws IOException{
		List<Static> sts = statics.getStaticsOnBlock(192, 256);
		for(Static s : sts){
			ItemData item = tiledata.getItem(s.getId());
			System.out.println(s + "  " + item);
		}
	}

	private static void doTiledata(TileData tiledata){
		for(ItemData i : tiledata.getItems()){
			System.out.println(i);
		}
		for(LandData l : tiledata.getLands()){
			System.out.println(l);
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

	private static void generatePaperdolGumpImages(TileData tiledata, Gumps gumps) throws IOException{
        File dir = new File("/tmp/uo/gumps/paperdoll/");
        dir.mkdirs();
		boolean partialHue = false;
		for (ItemData item : tiledata.getItems()) {
			if (item.getAnimation() != 0
					&& (!partialHue || item.getFlags().contains(TileFlag.PartialHue))) {
				int animid = 0xffff & item.getAnimation();
				System.out.println(Integer.toHexString(item.getId()) + ": " + item.getName()
						+ " - " + animid + " (" + item.getAnimation() + ") " + Integer.toHexString(animid)
						+ "\t" + item.getFlags().toString());
				Gump g = gumps.getGump(50000 + animid);
				if(g == null){
					System.out.println("null");
					continue;
				}
				BufferedImage image = g.getImage();
				if (image != null) {
					File out = new File(dir, (50000 + animid) + ".png");
					ImageIO.write(image, "png", out);
				}
			}
		}
	}

	private static void generateArtImages(Arts arts, TileData tiledata) throws IOException{
        File dir = new File("/tmp/uo/arts/");
        dir.mkdirs();
		for (int i = 0; i<0x10007; i++) {
			Art art = arts.getStatic(i);
			if(art == null){
				continue;
			}
			BufferedImage image = art.getImage();
			if (image != null) {
				File out = new File(dir, i + ".png");
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
			Bitmap image = art.getBitmap();
			image.hue(hues.getHue(i), false);
			File out = new File("/tmp/arts/" + i + ".png");
			ImageIO.write(image.getImage(), "png", out);
		}
	}

	private static void saveHue(Hues hues) throws IOException{
		hues.save(new FileOutputStream(new File("/tmp/hues.mul")));
	}

	private static void doVerdata(Verdata verdata){
		for(Verdato v : verdata.getVerdata()){
			System.out.println(v);
		}
	}
	
	private static void copyGumps(Gumps gumps) throws IOException{
		File idx = new File("/tmp/gumps/gumpIdx.mul");
		File mul = new File("/tmp/gumps/gump.mul");
		gumps.save(new FileOutputStream(idx), new FileOutputStream(mul));
	}

	private static void allColorGump(Gumps gumps, OutputStream gumpIdx) throws IOException {
		Bitmap b = new Bitmap(260, 237);
		for(int x=100; x<164; x++){
			short value = (short) ((x - 100) / 2);
			for(int y=40; y<140; y++){
				if(y < 65){
					b.setColor(x, y, Color.getInstance(value, (short) 0, (short) 0));
				}else if(y < 90){
					b.setColor(x, y, Color.getInstance((short) 0, value, (short) 0));
				}else if(y < 115){
					b.setColor(x, y, Color.getInstance((short) 0, (short) 0, value));
				}else{
					b.setColor(x, y, Color.getInstance(value, value, value));
				}
			}
		}
		Gump newGump = new Gump(b);
		gumps.setGump(50435, newGump, gumpIdx);
	}

	private static void twoColorGump(Gumps gumps, OutputStream gumpIdx) throws IOException {
		Bitmap b = new Bitmap(260, 237);
		for(int x=100; x<164; x++){
			short value1 = (short) ((x - 100) / 2);
			for(int y=50; y<114; y++){
				short value2 = (short) ((y - 50) / 2);
				b.setColor(x, y, Color.getInstance(value1, value2, (short) 31));
			}
		}
		Gump newGump = new Gump(b);
		gumps.setGump(50435, newGump, gumpIdx);
	}

	private static void multiColorGump(Gumps gumps, OutputStream gumpIdx) throws IOException {
		Bitmap b = new Bitmap(260, 237);
		for(int x=85; x<181; x++){
			short value1, value2, value3;
			if(x < 117){
				value1 = (short) ((x - 85));
				value2 = 0;
				value3 = 0;
			}else if(x < 149){
				value1 = 31;
				value2 = (short) ((x - 117));
				value3 = 0;
			}else{
				value1 = 31;
				value2 = 31;
				value3 = (short) ((x - 149));
			}
			for(int y=30; y<170; y++){
				if(y < 50){
					b.setColor(x, y, Color.getInstance(value1, value1, value1));
				}else if(y < 70){
					b.setColor(x, y, Color.getInstance(value1, value2, value3));
				}else if(y < 90){
					b.setColor(x, y, Color.getInstance(value1, value3, value2));
				}else if(y < 110){
					b.setColor(x, y, Color.getInstance(value2, value1, value3));
				}else if(y < 130){
					b.setColor(x, y, Color.getInstance(value3, value1, value2));
				}else if(y < 150){
					b.setColor(x, y, Color.getInstance(value2, value3, value1));
				}else{
					b.setColor(x, y, Color.getInstance(value3, value2, value1));
				}
			}
		}
		Gump newGump = new Gump(b);
		gumps.setGump(50435, newGump, gumpIdx);
	}
	
	/*private static void testBitmap(Gumps gumps) throws IOException{
		Gump gump = gumps.getGump(50435);
		byte[] data = gump.getBitmap().writeColorLines();
		FileIndex.DataPack dp = gumps.fileIndex.getData(50435);
		byte[] data2 = dp.getData();
		System.out.println("size: " + data.length + " : " + data2.length);
		for(int i = 0; i < data.length; i++){
			if(data[i] != data2[i]){
				System.out.println(i + ": " + (data[i] & 0xff) + " : " + (data2[i] & 0xff) );
			}
		}
	}*/
	
	private static void doAnim(Anims anims) throws IOException{
		for(int i=0; i< 1000; i++){
			Anim anim = anims.getAnim1(i, Anims.STAY, Anims.DOWN);
			if(anim == null) continue;
			Frame f = anim.getFrame(0);
			if(f == null) continue;
			BufferedImage image = f.getImage();
			if (image != null) {
				File out = new File("/tmp/anims/stay/" + i + ".png");
				ImageIO.write(image, "png", out);
			}
		}
	}

    private static void generateHueStripes(Hues hues) throws IOException{
        File dir = new File("/tmp/uo/hues/");
        dir.mkdirs();
        for(int i=1; i<=3000; i++){
            Hue h = hues.getHue(i);
            BufferedImage image = new BufferedImage(32, 16, BufferedImage.TYPE_4BYTE_ABGR);

            for(int x=0; x<32; x++){
                Color c = h.getColor(x);
                for(int y=0; y<16; y++){
                    image.setRGB(x, y, c.getAGBR());
                }
            }

            File out = new File(dir,i + "hue.png");
			ImageIO.write(image, "png", out);
        }
    }
}
