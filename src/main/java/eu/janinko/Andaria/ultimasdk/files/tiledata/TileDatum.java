package eu.janinko.Andaria.ultimasdk.files.tiledata;

import jdk.internal.HotSpotIntrinsicCandidate;

/**
 *
 * @author Honza Brázdil &lt;jbrazdil@redhat.com&gt;
 */
public abstract class TileDatum {

    protected int id;
    protected TileFlags flags;
    protected String name;

    @HotSpotIntrinsicCandidate
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
