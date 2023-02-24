package eu.janinko.andaria.ultimasdk.files.tiledata;

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

    public void setFlag(TileFlag flag, boolean set){
        if(set){
            flags.add(flag);
        }else{
            flags.remove(flag);
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.flags != null ? this.flags.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TileFlags other = (TileFlags) obj;
        if (this.flags != other.flags && (this.flags == null || !this.flags.equals(other.flags))) {
            return false;
        }
        return true;
    }
}
