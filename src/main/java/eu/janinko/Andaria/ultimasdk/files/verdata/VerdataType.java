
package eu.janinko.Andaria.ultimasdk.files.verdata;

/**
 * @author janinko
 */
public enum VerdataType {
	map0(0x0),
	staidx0(0x1),
	statics0(0x2),
	artidx(0x3),
	art(0x4),
	anim(0x5),
	animidx(0x6),
	soundidx(0x7),
	sound(0x8),
	texidx(0x9),
	texmaps(0xa),
	gumpidx(0xb),
	gumpart(0xc), //Various is WORD Height + WORD Width
	multiidx(0xd),
	multi(0xe),
	skillsidx(0xf),
	skills(0x10),
	tiledata(0x1e),
	animdata(0x1f);

	private int type;

	VerdataType(int type){
		this.type = type;
	}

	public int getType(){
		return type;
	}

	public static VerdataType get(int type){
		for(VerdataType v : VerdataType.values()){
			if(v.getType() == type) return v;
		}
		return null;
	}

}
