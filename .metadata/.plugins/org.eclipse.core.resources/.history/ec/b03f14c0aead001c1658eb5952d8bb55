package model.world;
import java.awt.Point;
import java.util.*;

public class Cover {

	private int currentHP;
    private	Point location;
    
    public Cover(int x , int y ) {
    	location.x = x;
    	location.y = y;
    }
    
public Cover(int hp , Point location) {
	this.currentHP = hp;
	this.location  = location;	
}

public Point getLocation(){
	return this.location;
}

public int getHp(){
	return this.currentHP;
}

public void setHp(int hp) {
	Random rnd = new Random();
    hp = rnd.nextInt((1000-100))+100;
    if(hp < 0)
    	this.currentHP = 0;
    else 
    	this.currentHP = hp;
}


}




