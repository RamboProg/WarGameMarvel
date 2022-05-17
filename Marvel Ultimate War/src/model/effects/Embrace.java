package model.effects;

import model.world.Champion;

public class Embrace extends Effect {
	public Embrace(int duration) {
		super("Embrace", duration, EffectType.BUFF);
	}

	public void apply(Champion c) {
		c.setMana((int) (c.getMana() * 1.2));
		c.setCurrentHP((int) (c.getCurrentHP() + c.getMaxHP() * 0.2));

		c.setAttackDamage((int) (c.getAttackDamage() * 1.2));
		c.setSpeed((int) (c.getSpeed() * 1.2));

	}

	public void remove(Champion c) {
		c.setSpeed((int) (c.getSpeed() / 1.2));
		c.setAttackDamage((int) (c.getAttackDamage() / 1.2));
		for (Effect effect : c.getAppliedEffects()) {
			if (effect.getName().equals("Embrace")) {
				c.getAbilities().remove(effect);
				break;
			}
		}
	}
}
