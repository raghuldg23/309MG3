package com.mg3.game;

import com.badlogic.gdx.math.Rectangle;

//Heart is to increase HP
public class Heart_Collectable {
Rectangle HeartBody = new Rectangle();
	
	public Heart_Collectable() {
		this.HeartBody.width = 20;
		this.HeartBody.height = 20;
	}
	public Heart_Collectable(float x, float y) {
		this.HeartBody.width = 20;
		this.HeartBody.height = 20;
		setXPos(x);
		setYPos(y);
	}
	
	public void setXPos(float x) {
		this.HeartBody.x = x;
	}
	
	public void setYPos(float y) {
		this.HeartBody.y = y;
	}
	
	public Rectangle getBody() {
		return this.HeartBody;
	}
	
	public float getXPos() {
		return this.HeartBody.x;
	}
	
	public float getYPos() {
		return this.HeartBody.y;
	}
	
}


