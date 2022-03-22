package model.world;
import java.awt.Point; 
import model.abilities.*;
import model.effects.*;
import java.util.*;

public class Champion {

	private String name;                       //read only
  	private int maxHP;                         //read only 
	private int currentHP;
	private int mana;                          //read only
	private int maxActionPointsPerTurn;
	private int currentActionPoints;           //read only
	private int attackRange;                   //read only
	private int attackDamage;
	private int speed;
	private ArrayList<Ability> abilities; 
	private ArrayList<Effect> appliedEffects;
	private Condition condition;           
	private	Point location;                    
	
	
	public String getName() {
		return name;
	}
	
	public int getMaxHP(){
		return maxHP;
	}

	public  void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}
	
	public int getCurrentHP() {
    	return currentHP;
    }
    
    public int getMana() {
    	return mana;
    }
    
    public void setMaxActionPointsPerTurn (int maxPtsTurn) {
    	this.maxActionPointsPerTurn = maxPtsTurn;
    }
    public int getMaxActionPointsPerTurn() {
    	return  maxActionPointsPerTurn;
    }
 
    public int getCurrentActionPoints() {
    	return currentActionPoints;
    }
    
    public int getAttackRange() {
     return	attackRange;
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
    	//ArrayList<Ability> abilities
    }
    
    public ArrayList<Effect> getAppliedEffects() {
    	return appliedEffects; 
    	//ArrayList<Effect> appliedEffects
    }
    
    public Condition getCondition() {
	return condition;
    }
    
    public void setLocation(Point location) {
    	this.location = location;
    }
    public Point getLocation() {
    	return location;
    }
    public Champion(int x , int y ) {
    	location.x = x;
    	location.y = y;
    }
    
    public void setCondition(){
    	this.condition = Condition.ACTIVE;
    }
    
  public Champion(String name, int maxHP, int mana, int maxActions, int speed, int attackRange, int attackDamage){
	  this.name = name;                       
	  this.maxHP = maxHP;                          
	  this.mana = mana;                         
	  this.attackRange = attackRange ;                   
	  this.attackDamage = attackRange;
	  this.speed = speed;		
  }
    
    
}
