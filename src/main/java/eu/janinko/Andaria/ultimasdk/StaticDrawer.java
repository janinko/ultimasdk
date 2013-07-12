package eu.janinko.Andaria.ultimasdk;

import eu.janinko.Andaria.ultimasdk.files.Arts;
import eu.janinko.Andaria.ultimasdk.files.Map;
import eu.janinko.Andaria.ultimasdk.files.Statics;
import eu.janinko.Andaria.ultimasdk.files.TileData;
import eu.janinko.Andaria.ultimasdk.files.arts.Art;
import eu.janinko.Andaria.ultimasdk.files.map.MapTile;
import eu.janinko.Andaria.ultimasdk.files.statics.Static;
import eu.janinko.Andaria.ultimasdk.files.tiledata.ItemData;
import eu.janinko.Andaria.ultimasdk.files.tiledata.TileFlag;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class StaticDrawer {
	public static final String uopath=System.getProperty("user.home") + "/Ultima/hra/";
	public static final int VERTICAL_SHIFT = -4;
	public static final int TILE_HALFSIZE = 22;
	public static final int TILE_SIZE = TILE_HALFSIZE*2;
	private int cx, cy;
	private int ccx, ccy;
	private int width, height;
	private BufferedImage image;
	private Graphics canvas;

	public StaticDrawer(int width, int height, int cx, int cy){
		this(width, height, cx, cy, 0, 0);
	}

	public StaticDrawer(int width, int height, int cx, int cy, int ccx, int ccy){
		this.width = width;
		this.height = height;
		this.cx = cx;
		this.cy = cy;
		this.ccx = ccx;
		this.ccy = ccy;
		image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		canvas = image.getGraphics();
	}
	
	private int getRelGX(int x, int y){
		return (y - cy) * 22 - (x - cx) * 22 + ccx;
	}

	private int getRelGY(int x, int y){
		return (y - cy) * 22 + (x - cx) * 22 + ccy;
	}

	private int getArtRelGX(Art art){
		return - art.getWidth() / 2;
	}

	private int getArtRelGY(Art art){
		return 22 - art.getHeight();
	}



	public void putArt(Art art, int x, int y){
		putArt(art, x, y, 0);
	}

	public void putArt(Art art, int x, int y, int z){
		int gx = width / 2 + getRelGX(x, y) + getArtRelGX(art);
		int gy = height / 2 + getRelGY(x, y) + getArtRelGY(art) + z*VERTICAL_SHIFT;
		canvas.drawImage(art.getImage(), gx, gy, null);
	}

	private BufferedImage getImage() {
		return image;
	}


	public static void main(String[] args) throws IOException {
		File artidx = new File(uopath + "artidx.mul");
		File artmul = new File(uopath + "art.mul");
		File staidx0 = new File(uopath + "staidx0.mul");
		File statics0 = new File(uopath + "statics0.mul");
		File map0 = new File(uopath + "map0.mul");
		File tiledatamul = new File(uopath + "tiledata.mul");

		Arts arts = new Arts(new FileInputStream(artidx), artmul);
		Statics statics = new Statics(new FileInputStream(staidx0), statics0);
		Map map = new Map(map0);
		TileData tiledata = new TileData(new FileInputStream(tiledatamul));

		renderWholeStatic(arts, statics, tiledata);
	}

	private static void renderThyris() throws IOException{
		File artidx = new File(uopath + "artidx.mul");
		File artmul = new File(uopath + "art.mul");
		File staidx0 = new File(uopath + "staidx0.mul");
		File statics0 = new File(uopath + "statics0.mul");
		File map0 = new File(uopath + "map0.mul");
		File tiledatamul = new File(uopath + "tiledata.mul");

		Arts arts = new Arts(new FileInputStream(artidx), artmul);
		Statics statics = new Statics(new FileInputStream(staidx0), statics0);
		Map map = new Map(map0);
		TileData tiledata = new TileData(new FileInputStream(tiledatamul));

		List<Static> sts = new ArrayList<Static>();

		for(int x = 1150/8; x <= 2150/8; x++){
			for(int y = 2550/8; y <= 3000/8; y++){
				sts.addAll(statics.getStaticsOnBlock(x, y));
			}
		}

		Collections.sort(sts, new StaticPositionComparator(tiledata));

		StaticDrawer sd = new StaticDrawer(15000, 11000, 2780, 1930);

		for(int x = 1150/8; x <= 2150/8; x++){
			for(int y = 2550/8; y <= 3000/8; y++){
				for(MapTile[] tiles : map.getTilesOnBlock(x, y)){
					for(MapTile tile : tiles){
						Art a = arts.getMap(tile.getId());
						sd.putArt(a, tile.getY(), tile.getX(), tile.getAlt());
					}
				}
			}
		}

		int i = 0;
		for(Static s : sts){
			Art a = arts.getStatic(s.getId());
			ItemData t = tiledata.getItem(s.getId());
			if(a == null){
				System.out.println(i + ": id: " + s.getId() + " name: " + t.getName() + " pos: " + s.getX() + "," + s.getY() + "," + s.getZ() );
				continue;
			}
			sd.putArt(a, s.getY(), s.getX(), s.getZ());
			//File out = new File("/tmp/canvas/g"+i+".png");
			//ImageIO.write(sd.getImage(), "png", out);
			//i++;
		}

		BufferedImage img = sd.getImage();

		File out = new File("/tmp/canvas/areaT.png");
		ImageIO.write(img, "png", out);
	}

	private static void renderWholeStatic(Arts arts, Statics statics, TileData tiledata) throws IOException{
		int mapWidth = 896;
		int mapHeight = 512;
		int plateSize = 8192;

		int mapA = mapHeight*8 * TILE_HALFSIZE;
		int mapB = mapWidth*8 * TILE_HALFSIZE;

		int zeroX = (mapA + 22) / 22;
		int zeroY = (mapA + 22) / 22 - mapA/11;

		int overallSize = mapA + mapB;
		int plates = overallSize / plateSize +1;

		System.out.println("plates: " + plates);
		
		long platesOnWidth = Math.round(Math.ceil((mapWidth*8 * TILE_SIZE) / (double) plateSize));
		long platesOnHeight =Math.round(Math.ceil((mapHeight*8 * TILE_SIZE) / (double) plateSize));
		double tilesOnPlate = plateSize / 44.0;
		int renderSize = (int) (Math.round(Math.ceil(tilesOnPlate)) + 15);
		
		for(int px = 0; px < plates; px++){
			for(int py = 0; py < plates; py++){
				int dx = plateSize * px + plateSize / 2;							// pixelový střed plate
				int dy = plateSize * py + plateSize / 2;
				int cx = ((dx + dy)/22 - zeroX)/2;									// souřadnice ve středu
				int cy = (((dx + dy)/22 - dx / 11) - zeroY)/2;
				int ddx = mapA - (cy * TILE_HALFSIZE - cx * TILE_HALFSIZE);	// pixelový střed souřadnic
				int ddy = cy * TILE_HALFSIZE + cx * TILE_HALFSIZE;
				int ccx = dx - ddx;
				int ccy = dy - ddy;
				if(ccx / TILE_SIZE > 0){
					cx -= ccx / TILE_SIZE;
					ccx = ccx % TILE_SIZE;
				}
				if(ccy / TILE_SIZE > 0){
					cy -= ccy / TILE_SIZE;
					ccy = ccy % TILE_SIZE;
				}

				System.out.println("Rendering plate " + px + "," + py +". Central position is " + cx + "," + cy + " with move: " + ccx + "," + ccy + ".");
				int startX = cx - renderSize;
				if(startX < 0) startX = 0;
				int startY = cy - renderSize;
				if(startY < 0) startY = 0;
				int stopX = cx + renderSize;
				if(stopX >= mapWidth*8) stopX = mapWidth*8;
				int stopY = cy + renderSize;
				if(stopY >= mapHeight*8) stopY = mapHeight*8;
				if(stopX < 0 || stopY < 0) continue;
				if(startX >= 8*mapWidth || startY >= 8*mapHeight) continue;
				System.out.println("  Starting rendering on " + startX + "," + startY + ", ending on " + stopX + "," + stopY + ".");

				StaticDrawer sd = new StaticDrawer(plateSize, plateSize, cx, cy, ccx, ccy);
				List<Static> sts = new ArrayList<Static>();
				for(int rx = startX; rx < stopX; rx++){
					for(int ry = startY; ry < stopY; ry++){
						 sts.addAll(statics.getStatics(rx, ry));
					}
				}
				Collections.sort(sts, new StaticPositionComparator(tiledata));
				int i = 0;
				for (Static s : sts) {
					Art a = arts.getStatic(s.getId());
					ItemData t = tiledata.getItem(s.getId());
					if (a == null) {
						System.out.println(i + ": id: " + s.getId() + " name: " + t.getName() + " pos: " + s.getX() + "," + s.getY() + "," + s.getZ());
						continue;
					}
					sd.putArt(a, s.getY(), s.getX(), s.getZ());
				}
				File out = new File("/tmp/map/map_" + px + "_" + py + ".png");
				ImageIO.write(sd.getImage(), "png", out);
			}
		}
	}

	private static class StaticPositionComparator implements Comparator<Static> {
		TileData tiledata;

		public StaticPositionComparator(){

		}

		public StaticPositionComparator(TileData tiledata) {
			this.tiledata = tiledata;
		}

		public int compare(Static o1, Static o2) {
			if(tiledata != null && o1.getX() == o2.getX() && o1.getY() == o2.getY() && o1.getZ() == o2.getZ()){
				ItemData t1 = tiledata.getItem(o1.getId());
				ItemData t2 = tiledata.getItem(o2.getId());
				if(t1 == null && t2 == null) return 0;

				int t1b = (t1.getFlags().contains(TileFlag.Background) ?  1: 0);
				int t2b = (t2.getFlags().contains(TileFlag.Background) ?  1: 0);
				return t2b - t1b;
			}
			return ((o1.getX() + o1.getY()) * 22 - o1.getZ() * VERTICAL_SHIFT) - ((o2.getX() + o2.getY()) * 22 - o2.getZ() * VERTICAL_SHIFT);
		}
	}
}
