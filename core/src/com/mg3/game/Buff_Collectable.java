package com.mg3.game;

import com.badlogic.gdx.math.Rectangle;

//Buff give player extra effects, can be either positive or negative
public class Buff_Collectable {
Rectangle BuffBody = new Rectangle();
	
	public Buff_Collectable() {
		this.BuffBody.width = 30;
		this.BuffBody.height = 30;
	}
	public Buff_Collectable(float x, float y) {
		this.BuffBody.width = 30;
		this.BuffBody.height = 30;
		setXPos(x);
		setYPos(y);
	}
	
	public void setXPos(float x) {
		this.BuffBody.x = x;
	}
	
	public void setYPos(float y) {
		this.BuffBody.y = y;
	}
	
	public Rectangle getBody() {
		return this.BuffBody;
	}
	
	public float getXPos() {
		return this.BuffBody.x;
	}
	
	public float getYPos() {
		return this.BuffBody.y;
	}
	
}
