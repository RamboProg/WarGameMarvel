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
		for (Champion champion : targets) {
			ArrayList<Effect> temp = new ArrayList<Effect>();
			for (Effect e : champion.getAppliedEffects()) {
				if (e.getType() != EffectType.DEBUFF) {
					 temp.add(e);
				}
			}
			champion.getAppliedEffects().clear();
			for (Effect e : temp) {
				champion.getAppliedEffects().add(e);
			}
			Embrace e = new Embrace(2);
			e.apply(champion);
			champion.getAppliedEffects().add(e);
		}
	}

}
