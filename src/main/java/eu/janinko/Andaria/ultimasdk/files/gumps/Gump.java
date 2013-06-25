/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.janinko.Andaria.ultimasdk.files.gumps;

import eu.janinko.Andaria.ultimasdk.Utils;
import eu.janinko.Andaria.ultimasdk.files.graphics.Graphics;
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

		bitmap = Graphics.readGraphics(width, height, 0, data);
	}


	public BufferedImage getImage(){
		return Graphics.getImage(width, height, bitmap);
	}
}
