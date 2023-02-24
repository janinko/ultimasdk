package eu.janinko.andaria.ultimasdk.tools;

import java.io.IOException;
import java.util.List;

import eu.janinko.andaria.ultimasdk.files.Map;
import eu.janinko.andaria.ultimasdk.files.Radarcol;
import eu.janinko.andaria.ultimasdk.files.Statics;
import eu.janinko.andaria.ultimasdk.files.TileData;
import eu.janinko.andaria.ultimasdk.graphics.Color;
import eu.janinko.andaria.ultimasdk.files.map.MapTile;
import eu.janinko.andaria.ultimasdk.files.statics.Static;
import eu.janinko.andaria.ultimasdk.files.statics.StaticHeightComparator;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class MiniMap {

    private final Map map;
    private final Statics statics;
    private final Radarcol radarcol;
    private final TileData tiledata;
    private final StaticHeightComparator shc;

    public MiniMap(Map map, Statics statics, Radarcol radarcol, TileData tiledata) {
        this.map = map;
        this.statics = statics;
        this.radarcol = radarcol;
        this.tiledata = tiledata;
        this.shc = new StaticHeightComparator(tiledata);
    }

    public Color getColorAt(final int x, final int y) throws IOException{
        List<Static> items = statics.getStatics(x, y);
        MapTile tile = map.getTile(x, y);
        Static topmost = null;
        int top = tile.getAlt();
        for(Static item : items){
            int h = item.getZ() + tiledata.getItem(item.getId()).getHeight();
            if(top < h){
                topmost = item;
                top = h;
            }
        }
        if(topmost == null){
            return radarcol.getMapColor(tile.getId());
        }else{
            return radarcol.getStaticColor(topmost.getId());
        }
    }

    public Color[][] getColorsAtBlock(final int xBlock, final int yBlock) throws IOException{
        List<Static> items = statics.getBlock(xBlock, yBlock).getStatics();
        MapTile[][] tile = map.getBlock(xBlock, yBlock).getTiles();
        int[][] tops = new int[8][8];
        Color[][] colors = new Color[8][8];
        Static[][] topmost = new Static[8][8];

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                tops[x][y] = tile[x][y].getAlt();
            }
        }

        final int xDiff = xBlock * 8;
        final int yDiff = yBlock * 8;
        for (Static item : items) {
            int x = item.getX() - xDiff;
            int y = item.getY() - yDiff;
            if (x > 7 || y > 7) {
                System.out.println("Static: " + item);
                System.out.println("Static: " + item);
            }
            int h = item.getZ() + tiledata.getItem(item.getId()).getHeight();
            if(tops[x][y] < h){
                topmost[x][y] = item;
                tops[x][y] = h;
            } else if (tops[x][y] == h
                    && (topmost[x][y] == null || shc.compare(topmost[x][y], item) < 0)) {
                topmost[x][y] = item;
            }
        }

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if(topmost[x][y] == null){
                    //System.out.println("M"+tile[x][y].getId());
                    colors[x][y] = radarcol.getMapColor(tile[x][y].getId());
                }else{
                    //System.out.println("S"+topmost[x][y].getId());
                    colors[x][y] = radarcol.getStaticColor(topmost[x][y].getId());
                }
            }
        }
        return colors;
    }

    public Color[][] getMap(final int width, final int height) throws IOException{
        int wBlocks = width / 8;
        int hBlocks = height / 8;
        Color[][] map = new Color[width][height];
        for (int xBlock = 0; xBlock < wBlocks; xBlock++) {
            for (int yBlock = 0; yBlock < hBlocks; yBlock++) {
                Color[][] colorsAtBlock = getColorsAtBlock(xBlock, yBlock);
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8; y++) {
                        map[xBlock*8+x][yBlock*8+y] = colorsAtBlock[x][y];
                    }
                }
            }
        }
        return map;
    }
}
