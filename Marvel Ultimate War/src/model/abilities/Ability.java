package model.abilities;

public class Ability {
	private String name; //name of the ability
	private int manaCost; //mana cost for the ability
	private int baseCooldown; //representing the number of turns the champion has to wait to play the ability again
	private int currentCooldown; //representing the number of turns the champion is currently waiting on (READ, WRITE)
	private int castRange; //max cast range
	private int requiredActionPoints; //represents the needed action points to cast the ability
	private AreaOfEffect castArea; //represents the area effect of the ability
	
	public Ability(String name, int cost, int baseCooldown, int castRange, AreaOfEffect area, int required){
		this.name = name;
		this.manaCost = cost;
		this.baseCooldown = baseCooldown;
		this.castRange = castRange;
		this.castArea = area;
		this.requiredActionPoints = required;
	}
	
	public String getName(){
		return name;
	}
	
	public int getCost(){	
		return manaCost;
	}
	
	public int getBaseCooldown(){
		return baseCooldown;
	}
	
	public int getCurrentCooldown(){
		return currentCooldown;
	}
	
	public void setCurrentCooldown(int currentCool){
		this.currentCooldown = currentCool;
	}
	
	public int getRange(){
		return castRange;
	}
	
	public int getPointsReq(){
		return requiredActionPoints;
	}
	
	public AreaOfEffect getArea(){
		return castArea;
	}
	
	
	
}
