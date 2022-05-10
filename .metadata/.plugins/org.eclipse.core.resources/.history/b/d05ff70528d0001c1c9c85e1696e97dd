package model.effects;

import model.abilities.AreaOfEffect;
import model.abilities.DamagingAbility;
import model.world.Champion;

public class Disarm extends Effect{
	private int tempMana;
	public Disarm(int duration) {
		super("Disarm" , duration, EffectType.DEBUFF);	
	}
	
	DamagingAbility d = new DamagingAbility("Punch",0 , 1, 1, AreaOfEffect.SINGLETARGET,1, 50);
	
	
	public void apply(Champion c){
		c.getAbilities().add(d);
		this.tempMana = c.getMana();
		c.setMana(0);
//		c.getAppliedEffects().add();
	}
	
	public void remove(Champion c){
		c.setMana(this.tempMana);
	}
		
	
}
