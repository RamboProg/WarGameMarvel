package model.world;

import java.awt.Point;
import java.util.*;

public class Cover {

	private int currentHP;
	private Point location;

	public void setLocation(int x, int y) {
		location.x = x;
		location.y = y;
	}

	public Cover(int x, int y) {
		Random rnd = new Random();
		int hp = rnd.nextInt((1000 - 100)) + 100;
		this.currentHP = hp;
		this.location = new Point(x, y);
	}

	public Point getLocation() {
		return this.location;
	}

	public int getHp() {
		return this.currentHP;
	}

	public void setHp(int hp) {
		if (hp < 0)
			this.currentHP = 0;
		else
			this.currentHP = hp;
	}

}
