package model.world;

import java.util.ArrayList;

public class Villain extends Champion {
	public Villain(String name, int maxHP, int mana, int maxActions, int speed,
			int attackRange, int attackDamage) {
		super(name, maxHP, mana, maxActions, speed, attackRange, attackDamage);
		setCurrentHP(maxHP);
		setCondition(Condition.ACTIVE);
	}

	public void useLeaderAbility(ArrayList<Champion> targets) {
		for (Champion c : targets) {
			if (c.getCurrentHP() < ((int) (c.getMaxHP() * 0.3))) {
				c.setCondition(Condition.KNOCKEDOUT);
			}
		}
	}

}
