package eu.janinko.andaria.ultimasdk.files.hues;

import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataInputStream;
import eu.janinko.andaria.ultimasdk.utils.Utils;
import eu.janinko.andaria.ultimasdk.graphics.Color;
import eu.janinko.andaria.ultimasdk.utils.LittleEndianDataOutputStream;

import java.io.IOException;

/**
 *
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public final class Hue {
    private int id;
    private Color[] colors = new Color[32];
    private short tableStart;
    private short tableEnd;
    private String name;

    public Hue(Hue o) {
        this.id = o.id;
        this.colors = o.colors.clone();
        this.tableStart = o.tableStart;
        this.tableEnd = o.tableEnd;
        this.name = o.name;
    }

    public Hue(LittleEndianDataInputStream in) throws IOException {
        for(int i=0; i<32; i++){
            colors[i] = Color.getInstance(in.readShort());
        }
        tableStart = in.readShort();
        tableEnd = in.readShort();
        this.setName(Utils.readName(in, 20));
    }

    public void save(LittleEndianDataOutputStream out) throws IOException {
        for(int i=0; i<32; i++){
            out.writeShort(colors[i].getColor());
        }
        out.writeShort(tableStart);
        out.writeShort(tableEnd);
        Utils.writeName(out, name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Color[] getColors() {
        return colors;
    }

    public void setColors(Color[] colors) {
        this.colors = colors;
    }

    public Color getColor(int id) {
        return colors[id];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public float[] averageHsb(){
        float r = 0;
        float g = 0;
        float b = 0;
        for(Color color : colors){
            r += color.get5Red();
            g += color.get5Green();
            b += color.get5Blue();
        }
        float[] hsb = java.awt.Color.RGBtoHSB((int)(r /32/31*255), (int)(g /32/31*255), (int)(b /32/31*255), null);
        return hsb;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Hue ");
        sb.append(id);
        sb.append(": ");
        sb.append(name);
        sb.append(' ');
        for(int i=0; i<32; i++){
            sb.append('[');
            if(colors[i].isAlpha()){ // alpha
                sb.append('A');
            }else{
                sb.append(' ');
            }
            sb.append(' ');
            sb.append(colors[i].get5Red()); // red
            sb.append(' ');
            sb.append(colors[i].get5Green()); // green
            sb.append(' ');
            sb.append(colors[i].get5Blue()); // blue
            sb.append("] ");
        }
        return sb.toString();
    }
}
