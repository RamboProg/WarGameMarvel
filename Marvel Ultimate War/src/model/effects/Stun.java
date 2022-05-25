package model.effects;

import model.world.Champion;
import model.world.Condition;

public class Stun extends Effect {
	public Stun(int duration) {
		super("Stun", duration, EffectType.DEBUFF);
	}

	public void apply(Champion c) {
		c.setCondition(Condition.INACTIVE);
	}

	public void remove(Champion c) {
		for (Effect e : c.getAppliedEffects()) {
			if(e instanceof Root) {
				c.setCondition(Condition.ROOTED);
				return;
			}
		}
		c.setCondition(Condition.ACTIVE);
	}
}
