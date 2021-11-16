package com.mg3.game;

import com.badlogic.gdx.math.Rectangle;

//weapon is a an item players can by in the shop to increase their damage
public class Shield {
	Rectangle ShieldBody = new Rectangle();
	
	public Shield() {
		this.ShieldBody.width = 50;
		this.ShieldBody.height = 50;
	}
	
	public void setXPos(float x) {
		this.ShieldBody.x = x;
	}
	
	public void setYPos(float y) {
		this.ShieldBody.y = y;
	}
	
	public Rectangle getBody() {
		return this.ShieldBody;
	}
	
	public float getXPos() {
		return this.ShieldBody.x;
	}
	
	public float getYPos() {
		return this.ShieldBody.y;
	}
	
	
}

