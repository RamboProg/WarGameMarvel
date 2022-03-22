package model.abilities;

public class HealingAbility extends Ability {
	private int healAmount;
	
	public HealingAbility(String name, int cost, int baseCooldown, int castRange, AreaOfEffect area, int required, int healAmount){
		super(name, cost, baseCooldown, castRange, area, required);
		this.healAmount = healAmount;
	}
	
	public int getHeal(){
		return healAmount;
	}
	
	public void setHeal(int amount){
		this.healAmount = amount;
	}
	
	//getRandomInteger(100, 1000);
	
}
