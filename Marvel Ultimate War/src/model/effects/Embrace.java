package model.effects;

import model.world.Champion;

public class Embrace extends Effect {
	private int count = 0;

	public Embrace(int duration) {
		super("Embrace", duration, EffectType.BUFF);
	}

	public void apply(Champion c) {
		c.setMana(c.getMana() * 120 / 100);
		c.setCurrentHP((int) (c.getCurrentHP() + c.getMaxHP() * 20 / 100));

		c.setAttackDamage((int) (c.getAttackDamage() * 120 / 100));
		c.setSpeed((int) (c.getSpeed() * 120 / 100));
		this.count++;
	}

	public void remove(Champion c) {
		for (int i = 0; i < this.count; i++) {
			c.setSpeed((int) (c.getSpeed() * 100 / 120));
			c.setAttackDamage((int) (c.getAttackDamage() * 100 / 120));

		}
	}
}
