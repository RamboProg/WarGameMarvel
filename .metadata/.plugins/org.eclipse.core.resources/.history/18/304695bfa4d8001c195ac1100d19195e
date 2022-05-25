package model.abilities;

import java.util.ArrayList;

import model.world.Champion;
import model.world.Cover;
import model.world.Damageable;

public class HealingAbility extends Ability {
	private int healAmount;

	public HealingAbility(String name, int cost, int baseCooldown,
			int castRange, AreaOfEffect area, int required, int healAmount) {
		super(name, cost, baseCooldown, castRange, area, required);
		this.healAmount = healAmount;
	}

	public int getHealAmount() {
		return healAmount;
	}

	public void setHealAmount(int amount) {
		this.healAmount = amount;
	}

	public void execute(ArrayList<Damageable> targets) {
		for (Damageable d : targets) {
			if (d instanceof Champion)
				d.setCurrentHP(d.getCurrentHP() + this.healAmount);
			else if (d instanceof Cover)
				d.setCurrentHP(d.getCurrentHP() + this.healAmount);
		}
	}

}
