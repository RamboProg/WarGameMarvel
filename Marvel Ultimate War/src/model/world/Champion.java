package model.world;

import java.awt.Point;

import model.abilities.*;
import model.effects.*;

import java.util.*;

public abstract class Champion implements Comparable, Damageable {

	private String name; // read only
	private int maxHP; // read only
	private int currentHP;
	private int mana; // read only
	private int maxActionPointsPerTurn;
	private int currentActionPoints; // read only
	private int attackRange; // read only
	private int attackDamage;
	private int speed;
	private ArrayList<Ability> abilities;
	private ArrayList<Effect> appliedEffects;
	private Condition condition;
	private Point location;

	public Champion(String name, int maxHP, int mana, int maxActions,
			int speed, int attackRange, int attackDamage) {
		this.name = name;
		this.maxHP = maxHP;
		this.mana = mana;
		this.attackRange = attackRange;
		this.attackDamage = attackDamage;
		this.speed = speed;
		this.currentHP = maxHP;
		this.maxActionPointsPerTurn = maxActions;
		this.currentActionPoints = maxActions;
		abilities = new ArrayList<Ability>();
		appliedEffects = new ArrayList<Effect>();
		setCondition(Condition.ACTIVE);
	}

	public int compareTo(Object o) {
		if (o instanceof Champion) {
			Champion p = (Champion) o;
			if (this.getSpeed() != p.getSpeed())
				return (this.getSpeed() - p.getSpeed())*-1;
			else
				return this.getName().compareTo(p.getName());
		}
		return 0;
	}

	public abstract void useLeaderAbility(ArrayList<Champion> targets) ;

	public String getName() {
		return name;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setCurrentHP(int currentHP) {
		if (currentHP > maxHP)
			this.currentHP = maxHP;
		else if (currentHP < 0)
			this.currentHP = 0;
		else
			this.currentHP = currentHP;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public int getMana() {
		return mana;
	}

	public void setMaxActionPointsPerTurn(int maxActionPointsPerTurn) {
		this.maxActionPointsPerTurn = maxActionPointsPerTurn;
	}

	public int getMaxActionPointsPerTurn() {
		return maxActionPointsPerTurn;
	}

	public int getCurrentActionPoints() {
		return currentActionPoints;
	}

	public int getAttackRange() {
		return attackRange;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public void setCurrentActionPoints(int currentActionPoints) {
		if (currentActionPoints > maxActionPointsPerTurn)
			this.currentActionPoints = maxActionPointsPerTurn;
		else if (currentActionPoints < 0)
			this.currentActionPoints = 0;
		else
			this.currentActionPoints = currentActionPoints;
	}

	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSpeed() {
		return speed;
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
		// ArrayList<Ability> abilities
	}

	public ArrayList<Effect> getAppliedEffects() {
		return appliedEffects;
		// ArrayList<Effect> appliedEffects
	}

	public Condition getCondition() {
		return condition;
	}

	public void setLocation(Point p) {
		this.location = p;
	}

	public Point getLocation() {
		return location;
	}

	public void setCondition() {
		this.condition = Condition.ACTIVE;
	}

}
