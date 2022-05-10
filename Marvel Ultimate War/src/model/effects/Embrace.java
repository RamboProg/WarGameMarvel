package model.effects;

import model.world.Champion;

public class Embrace extends Effect {
	public Embrace(int duration) {
		super("Embrace", duration, EffectType.BUFF);
	}

	public void apply(Champion c) {
		c.setMana(c.getMana() * 120 / 100);
		c.setCurrentHP((int) (c.getCurrentHP() + c.getMaxHP() * 20 / 100));

		c.setAttackDamage((int) (c.getAttackDamage() * 120 / 100));
		c.setSpeed((int) (c.getSpeed() * 120 / 100));

		c.getAppliedEffects().add(new Embrace(getDuration()));
	}

	public void remove(Champion c) {
		c.setSpeed((int) (c.getSpeed() * 100 / 120));
		c.setAttackDamage((int) (c.getAttackDamage() * 100 / 120));
		for (Effect effect : c.getAppliedEffects()) {
			if (effect.getName().equals("Embrace")) {
				c.getAbilities().remove(effect);
				break;
			}
		}
	}
}
