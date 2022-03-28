package model.effects;

public class Shock extends Effect{
	//EffectType type = EffectType.DEBUFF;
	public Shock(int duration) {
		super("Shock" , duration, EffectType.DEBUFF);		
		//type = EffectType.DEBUFF;
	}

}
