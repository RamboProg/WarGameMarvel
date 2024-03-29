package model.abilities;

import java.util.ArrayList;

import model.world.Damageable;

public abstract class Ability {
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
	
	public abstract void execute(ArrayList<Damageable> targets) throws CloneNotSupportedException; //Check in all ABilities

	public int getCurrentCooldown() {
		return currentCooldown;
	}

	public void setCurrentCooldown(int currentCooldown) {
		if(currentCooldown > this.baseCooldown)
			this.currentCooldown = baseCooldown;
		else if (currentCooldown < 0 )
			this.currentCooldown = 0;
		else this.currentCooldown = currentCooldown;
	}

	public String getName() {
		return name;
	}

	public int getManaCost() {
		return manaCost;
	}

	public int getBaseCooldown() {
		return baseCooldown;
	}

	public int getCastRange() {
		return castRange;
	}

	public int getRequiredActionPoints() {
		return requiredActionPoints;
	}

	public AreaOfEffect getCastArea() {
		return castArea;
	}

	
}
