package eu.janinko.Andaria.ultimasdk.files.tiledata;

import java.util.EnumSet;

/**
 *
 * @author jbrazdil
 */
public class TileFlags {
	EnumSet<TileFlag> flags;

	public TileFlags(){
		flags = EnumSet.noneOf(TileFlag.class);
	}

	public TileFlags(int readInt) {
		flags = EnumSet.noneOf(TileFlag.class);
		for(TileFlag flag: TileFlag.values()){
			if(flag.isIn(readInt)){
				flags.add(flag);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("TileFlags: ");
		boolean first=true;
		for(TileFlag flag : flags){
			if(!first) sb.append(", ");
			sb.append(flag.name());
			first = false;
		}
		return sb.toString();
	}

	int toInt() {
		int ret = 0;
		for(TileFlag flag : flags){
			ret |= flag.getFlag();
		}
		return ret;
	}

	public boolean contains(TileFlag flag){
		return flags.contains(flag);
	}
}
