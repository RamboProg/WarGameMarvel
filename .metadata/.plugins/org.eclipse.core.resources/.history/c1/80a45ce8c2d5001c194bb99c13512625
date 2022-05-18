package model.effects;

import model.world.Champion;
import model.world.Condition;

public class Root extends Effect {
	public Root(int duration) {
		super("Root", duration, EffectType.DEBUFF);
	}

	public void apply(Champion c) {
		c.getAppliedEffects().add(new Root(getDuration()));
		c.setCondition(Condition.ROOTED);
	}

	public void remove(Champion c) {
		for (Effect effect : c.getAppliedEffects()) {
			if (effect.getName().equals("Root")) {
				c.getAbilities().remove(effect);
				break;
			}
		}
		c.setCondition(Condition.ACTIVE);
	}

}
