package model.effects;

public class Shock extends Effect{
	EffectType type = EffectType.DEBUFF;
	public Shock(String name, int duration, EffectType type) {
		super(name , duration, type);
		type = EffectType.DEBUFF;
		this.type = type;
		//type = EffectType.DEBUFF;
	}

}
