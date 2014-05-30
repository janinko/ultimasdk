package eu.janinko.Andaria.ultimasdk.files.hues;

import eu.janinko.Andaria.ultimasdk.files.Hues;
import eu.janinko.Andaria.ultimasdk.files.graphics.Color;
import java.util.Arrays;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class HueComparator {
	private static double distanceModif = 0.3;

	public static double[][] getDistanceMatrix(Hues hues){
		double[][] mx = new double[3000][3000];

		for(int i=0; i<3000; i++){
			mx[i][i] = 0;
			for(int j=i+1; j<3000; j++){
				double distance = getSimilarity(hues.getHue(i), hues.getHue(j));
				mx[i][j] = distance;
				mx[j][i] = distance;
			}
		}

		return mx;
	}

	/**
	 * Computes similarity of two hues.
	 * This method is symetric: {@code getSimilarity(h1, h2) == getSimilarity(h2, h1)}.
	 * Same color has similarity 0: {@code getSimilarity(h1, h1) == 0}.
	 *
	 * @param h1 First hue to compare.
	 * @param h2 Second hue to compare.
	 * @return
	 */
	public static double getSimilarity(Hue h1, Hue h2){
		double sim = 0;
		for(int i=0; i<32; i++){
			Color c1 = h1.getColor(i);
			Color c2 = h2.getColor(i);
			double diff = getSimilarity(c1, c2);
			sim += diff;
		}
		return sim;
	}

	private static double getSimilarity(Color c1, Color c2){
		double[] rgb1 = new double[3];
		rgb1[0] = c1.get5Red() / 31.0;
		rgb1[1] = c1.get5Green() / 31.0;
		rgb1[2] = c1.get5Blue() / 31.0;
		double[] l1 = convXYZtoLAB(convRGBtoXYZ(rgb1));
		double[] rgb2 = new double[3];
		rgb2[0] = c2.get5Red() / 31.0;
		rgb2[1] = c2.get5Green() / 31.0;
		rgb2[2] = c2.get5Blue() / 31.0;
		double[] l2 = convXYZtoLAB(convRGBtoXYZ(rgb2));
		return labSimilarity(l1, l2);
	}

	private static double[] convRGBtoXYZ(double[] rgb){
		double red = rgb[0];
		double grn = rgb[1];
		double blu = rgb[2];

		if(red > 0.04045){
			red = Math.pow((red + 0.055)/1.055, 2.4);
		}else{
			red = red / 12.92;
		}
		if(grn > 0.04045){
			grn = Math.pow((grn + 0.055)/1.055, 2.4);
		}else{
			grn = grn / 12.92;
		}
		if(blu > 0.04045){
			blu = Math.pow((blu + 0.055)/1.055, 2.4);
		}else{
			blu = blu / 12.92;
		}
		red *= 100;
		grn *= 100;
		blu *= 100;

		double[] xyz = new double[3];
		xyz[0] = red * 0.4124 + grn * 0.3576 + blu * 0.1805;
		xyz[1] = red * 0.2126 + grn * 0.7152 + blu * 0.0722;
		xyz[2] = red * 0.0193 + grn * 0.1192 + blu * 0.9505;
		
		return xyz;
	}

	private static double[] convXYZtoLAB(double[] xyz){
		double x = xyz[0] / 95.047;
		double y = xyz[1] / 100;
		double z = xyz[2] / 108.883;

		if(x > 0.008856){
			x = Math.pow(x, 1/3);
		}else{
			x = x * 7.787 + 16/116;
		}
		if(y > 0.008856){
			y = Math.pow(y, 1/3);
		}else{
			y = y * 7.787 + 16/116;
		}
		if(z > 0.008856){
			z = Math.pow(z, 1/3);
		}else{
			z = z * 7.787 + 16/116;
		}
		double[] lab = new double[3];
		lab[0] = 116*y - 16;
		lab[1] = 500*(x - y);
		lab[2] = 200*(y - z);
		return lab;
	}

	private static double labSimilarity(double[] l1, double[] l2){
		double c1 = Math.sqrt(l1[1]*l1[1] + l1[2]*l1[2]);
		double c2 = Math.sqrt(l2[1]*l2[1] + l2[2]*l2[2]);
		double dc = c1 - c2;
		double dl = l1[0] - l2[0];
		double da = l1[1] - l2[1];
		double db = l1[2] - l2[2];
		double dh = Math.sqrt(da*da + db*db - dc*dc);
		double f = dl;
		double s = dc / (1+0.045*c1);
		double t = dh / (1+0.015*c1);

		double dist = Math.sqrt(f*f + s*s + t*t);

		return dist;
	}

}
