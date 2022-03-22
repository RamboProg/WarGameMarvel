package model.effects;

public class PowerUp extends Effect{

	public PowerUp(String name, int duration , EffectType type) {
		super(name , duration, type);
		type = EffectType.BUFF;
	}
}
