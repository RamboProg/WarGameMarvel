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
						.setDamageAmount(((DamagingAbility) ability)
								.getDamageAmount() * 120 / 100);
			else if (ability instanceof HealingAbility)
				((HealingAbility) ability)
						.setHealAmount(((HealingAbility) ability)
								.getHealAmount() * 120 / 100);
		}
	}

	public void remove(Champion c) {
		for (Ability ability : c.getAbilities()) {
			if (ability instanceof DamagingAbility)
				((DamagingAbility) ability)
						.setDamageAmount(((DamagingAbility) ability)
								.getDamageAmount() * 100 / 120);
			else if (ability instanceof HealingAbility)
				((HealingAbility) ability)
						.setHealAmount(((HealingAbility) ability)
								.getHealAmount() * 100 / 120);

		}
	}
}
