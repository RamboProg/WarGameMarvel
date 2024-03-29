package model.world;

import java.awt.Point;
import java.util.*;

public class Cover implements Damageable {

	private int currentHP;
	private Point location;

	// public void setLocation(int x, int y) {
	// this.location.x = x;
	// this.location.y = y;
	// }

	public Cover(int x, int y) {
		Random rnd = new Random();
		int hp = rnd.nextInt((1000 - 100)) + 100;
		this.currentHP = hp;
		this.location = new Point(x, y);
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		if (currentHP < 0)
			this.currentHP = 0;
		else
			this.currentHP = currentHP;
	}

	public Point getLocation() {
		return location;
	}

}
