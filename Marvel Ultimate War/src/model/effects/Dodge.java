package model.effects;

import model.world.Champion;

public class Dodge extends Effect {
	private int count = 0;

	public Dodge(int duration) {
		super("Dodge", duration, EffectType.BUFF);
		// Dodge e = new Dodge(name, duration, type);
	}

	public void apply(Champion c) {
		double chanceTemp = Math.random() * 100;
		int chance = (int) chanceTemp;
		if (chance < 50)
			c.setCurrentHP(c.getCurrentHP());

		c.setSpeed((int) (c.getSpeed() * 105 / 100));
		this.count++;
	}

	public void remove(Champion c) {
		for (int i = 0; i < this.count; i++) {
			c.setSpeed((int) (c.getSpeed() * 100 / 105));

		}
	}
}
