/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.janinko.Andaria.ultimasdk.files.gumps;

import eu.janinko.Andaria.ultimasdk.Utils;
import java.awt.image.BufferedImage;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class Gump {
	private int width;
	private int height;
	private short[][] bitmap;

	public Gump(int width, int height, byte[] data) {
		bitmap = new short[width][height];
		this.width = width;
		this.height = height;
		int[] starts = new int[height];
		int pos = 0;
		for(int i=0; i<height; i++){
			starts[i] = ((0xff & data[pos++]) + ((0xff & data[pos++]) << 8) + ((0xff & data[pos++]) << 16) + ((0xff & data[pos++]) << 24))*4;
			//System.out.println("starts[" + i + "] = " + starts[i]);
		}

		for(int y = 0; y < height; y++){
			int x = 0;
			pos = starts[y];
			//System.out.println("pos: " + pos);
			while(x < width){
				int color = (0xff & data[pos++]) + ((0xff & data[pos++]) << 8);
				int repeat = (0xff & data[pos++]) + ((0xff & data[pos++]) << 8);
				//System.out.println("color: " + color + " repeat:" + repeat);
				for(int i=0; i < repeat; i++){
					bitmap[x++][y] = (short) color;
				}
			}
		}
	}


	public BufferedImage getImage(){
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		for(int x=0; x<width; x++){
			for(int y=0; y<height; y++){
				int alpha = 255 - ((bitmap[x][y] & 0x8000) >>> 15)*255;
				int red = Utils.convertColor5to8((bitmap[x][y] & 0x7c00) >>> 10);
				int green = Utils.convertColor5to8((bitmap[x][y] & 0x3e0) >>> 5);
				int blue = Utils.convertColor5to8(bitmap[x][y] & 0x1f);
				int argb = (alpha << 24) + (red << 16) + (green << 8) + blue;
				image.setRGB(x, y, argb);
			}
		}
		return image;
	}
}
