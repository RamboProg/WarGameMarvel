package model.effects;

import model.world.Champion;

public class Root extends Effect{
	public Root(int duration) {
		super("Root" , duration, EffectType.DEBUFF);
	}
	
	public void apply(Champion c) {
		c.getAppliedEffects().add(new Root(getDuration()));
	}

	public void remove(Champion c) {
		for (Effect effect : c.getAppliedEffects()) {
			if (effect.getName().equals("Root")) {
				c.getAbilities().remove(effect);
				break;
			}
		}
		
	}
	

}
