package model.effects;

import model.world.Champion;

public class Shock extends Effect {
	int sp;
	public Shock(int duration) {
		super("Shock", duration, EffectType.DEBUFF);
	}

	public void apply(Champion c) {
		this.sp = c.getSpeed();
		c.setSpeed((int) (sp* 1.2));
		c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn() - 1);
		c.setCurrentActionPoints(c.getCurrentActionPoints() - 1);
		c.setAttackDamage((int)(c.getAttackDamage() - (c.getAttackDamage()*0.1)));
	}

	public void remove(Champion c) {
		c.setSpeed(this.sp);
		c.setMaxActionPointsPerTurn(c.getMaxActionPointsPerTurn() + 1);
		c.setCurrentActionPoints(c.getCurrentActionPoints() + 1);
		c.setAttackDamage((int)(c.getAttackDamage() + (c.getAttackDamage()*0.9)));

	}
}
