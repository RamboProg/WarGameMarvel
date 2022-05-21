package model.effects;

import model.abilities.Ability;
import model.world.Champion;
import model.world.Condition;

public class Root extends Effect {
	public Root(int duration) {
		super("Root", duration, EffectType.DEBUFF);
	}

	// check applied effects
	public void apply(Champion c) {
		if (c.getCondition() != Condition.INACTIVE)
			c.setCondition(Condition.ROOTED);
		else
			c.setCondition(Condition.INACTIVE);
	}

	public void remove(Champion c) {
		if (c.getCondition() == Condition.INACTIVE)
			c.setCondition(Condition.INACTIVE);

	}

}
