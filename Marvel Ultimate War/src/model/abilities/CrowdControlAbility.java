package model.abilities;

import java.util.ArrayList;

import model.effects.*;
import model.world.Champion;
import model.world.Damageable;

public class CrowdControlAbility extends Ability {
	private Effect effect;

	public CrowdControlAbility(String name, int cost, int baseCooldown,
			int castRange, AreaOfEffect area, int required, Effect effect) {
		super(name, cost, baseCooldown, castRange, area, required);
		this.effect = effect;
	}

	public Effect getEffect() {
		return effect;
	}

	public void execute(ArrayList<Damageable> targets) {
		for (Damageable d : targets) {
			if (d instanceof Champion)
				this.effect.apply((Champion) d);
		}
	}

}
