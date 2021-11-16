package com.mg3.game;

import com.badlogic.gdx.math.Rectangle;

//weapon is a an item players can by in the shop to increase their damage
public class Weapon {
	Rectangle WeaponBody = new Rectangle();
	
	public Weapon() {
		this.WeaponBody.width = 50;
		this.WeaponBody.height = 50;
	}
	
	public void setXPos(float x) {
		this.WeaponBody.x = x;
	}
	
	public void setYPos(float y) {
		this.WeaponBody.y = y;
	}
	
	public Rectangle getBody() {
		return this.WeaponBody;
	}
	
	public float getXPos() {
		return this.WeaponBody.x;
	}
	
	public float getYPos() {
		return this.WeaponBody.y;
	}
	
	
}
