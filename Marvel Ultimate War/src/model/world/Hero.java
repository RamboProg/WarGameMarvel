package model.world;

import java.util.ArrayList;

import model.effects.*;

public class Hero extends Champion {

	public Hero(String name, int maxHP, int mana, int maxActions, int speed,
			int attackRange, int attackDamage) {
		super(name, maxHP, mana, maxActions, speed, attackRange, attackDamage);
		setCurrentHP(maxHP);
		setCondition(Condition.ACTIVE);
	}

	public void useLeaderAbility(ArrayList<Champion> targets) {
		for (Champion c : targets) {
			for (Effect e : c.getAppliedEffects()) {
				if (e.getType().equals(EffectType.DEBUFF)) {
					e.remove(c);
				}
			}
			c.getAppliedEffects().add(new Embrace(2));

		}
	}

}
