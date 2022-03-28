package model.abilities;

public class DamagingAbility extends Ability {
	private int damageAmount;
	
	public DamagingAbility(String name, int cost, int baseCooldown, int castRange, AreaOfEffect area, int required, int dmgAmount){
		super(name, cost, baseCooldown, castRange, area, required);
		this.damageAmount = dmgAmount;
	}
	
	public int getDamageAmount(){
		return damageAmount;
	}
	
	public void setDamageAmount(int amount){
		this.damageAmount = amount;
	}
	
}
