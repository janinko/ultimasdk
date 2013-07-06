
package eu.janinko.Andaria.ultimasdk;

import static eu.janinko.Andaria.ultimasdk.StaticDrawer.VERTICAL_SHIFT;
import eu.janinko.Andaria.ultimasdk.files.Map;
import eu.janinko.Andaria.ultimasdk.files.Radarcol;
import eu.janinko.Andaria.ultimasdk.files.Statics;
import eu.janinko.Andaria.ultimasdk.files.TileData;
import eu.janinko.Andaria.ultimasdk.files.map.MapTile;
import eu.janinko.Andaria.ultimasdk.files.statics.Static;
import eu.janinko.Andaria.ultimasdk.files.tiledata.ItemData;
import eu.janinko.Andaria.ultimasdk.files.tiledata.TileFlag;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * @author janinko
 */
public class MapGenerator {
	public static final String uopath="/home/janinko/Ultima/hra/";

	public static void main(String[] args) throws IOException {
		File staidx0 = new File(uopath + "staidx0.mul");
		File statics0 = new File(uopath + "statics0.mul");
		File map0 = new File(uopath + "map0.mul");
		File radarcolmul = new File(uopath + "radarcol.mul");
		File tiledatamul = new File(uopath + "tiledata.mul");

		Statics statics = new Statics(new FileInputStream(staidx0), statics0);
		Map map = new Map(map0);
		Radarcol radarcol = new Radarcol(new FileInputStream(radarcolmul));
		TileData tiledata = new TileData(new FileInputStream(tiledatamul));

		StaticPositionComparator comparator = new StaticPositionComparator(tiledata);

		BufferedImage image = new BufferedImage(768*8, 512*8, BufferedImage.TYPE_4BYTE_ABGR);
		for(int x = 0; x < 768; x++){
			System.out.print(x + " ");
			for(int y = 0; y < 512; y++){
				if( y % 16 == 0) System.out.print(".");
				MapTile[][] tiles = map.getTilesOnBlock(x, y);
				for(int dx=0; dx<8; dx++){
					for(int dy=0; dy<8; dy++){
						MapTile t = tiles[dx][dy];
						image.setRGB(t.getX(), t.getY(), radarcol.getMapColor(t.getId()).getAGBR());
					}
				}
				List<Static> sts = statics.getStaticsOnBlock(x, y);
				Collections.sort(sts, comparator);
				for(Static s : sts){
					int dx = s.getX() - x*8;
					int dy = s.getY() - y*8;
					if(s.getZ() >= tiles[dx][dy].getAlt()){
						image.setRGB(s.getX(), s.getY(), radarcol.getStaticColor(s.getId()).getAGBR());
					}
				}
			}
			System.out.println();
		}

		File out = new File("/tmp/map.png");
		ImageIO.write(image, "png", out);
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
