package model.effects;

import model.abilities.Ability;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.world.Champion;

public class PowerUp extends Effect {
	public PowerUp(int duration) {
		super("PowerUp", duration, EffectType.BUFF);
	}

	public void apply(Champion c) {
		for (Ability ability : c.getAbilities()) {
			if (ability instanceof DamagingAbility)
				((DamagingAbility) ability)
						.setDamageAmount((int) (((DamagingAbility) ability)
								.getDamageAmount() * 1.2));
			if (ability instanceof HealingAbility)
				((HealingAbility) ability)
						.setHealAmount((int) (((HealingAbility) ability)
								.getHealAmount() * 1.2));
		}
	}

	public void remove(Champion c) {
		for (Ability ability : c.getAbilities()) {
			if (ability instanceof DamagingAbility) {
				((DamagingAbility) ability)
						.setDamageAmount((int) (((DamagingAbility) ability)
								.getDamageAmount() / 1.2));
			}
			if (ability instanceof HealingAbility) {
				((HealingAbility) ability)
						.setHealAmount((int) (((HealingAbility) ability)
								.getHealAmount() / 1.2));
			}

		}
	}
}
