package model.effects;

public class Dodge extends Effect{
	public Dodge(String name, int duration, EffectType type) {
		super(name , duration, EffectType.BUFF);
		type = EffectType.BUFF;
		//Dodge e = new Dodge(name, duration, type);

	}

}
