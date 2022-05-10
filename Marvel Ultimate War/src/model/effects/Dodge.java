package model.effects;

import model.world.Champion;

public class Dodge extends Effect {

	public Dodge(int duration) {
		super("Dodge", duration, EffectType.BUFF);
		// Dodge e = new Dodge(name, duration, type);
	}

	public void apply(Champion c) {
		c.setSpeed((int) (c.getSpeed() * 105 / 100));
		c.getAppliedEffects().add(new Dodge(getDuration()));
	}

	public void remove(Champion c) {
		for (Effect effect : c.getAppliedEffects()) {
			if (effect.getName().equals("Dodge")){
				c.getAbilities().remove(effect);
				break;
			}
		}
	}
}
