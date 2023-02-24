package eu.janinko.andaria.ultimasdk.files.tiledata;

/**
 *
 * @author Honza Brázdil &lt;jbrazdil@redhat.com&gt;
 */
public abstract class TileDatum {

    protected int id;
    protected TileFlags flags;
    protected String name;

    public TileDatum() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TileFlags getFlags() {
        return flags;
    }

    public void setFlags(TileFlags flags) {
        this.flags = flags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
