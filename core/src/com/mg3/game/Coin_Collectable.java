package com.mg3.game;

import com.badlogic.gdx.math.Rectangle;

//IN-game money
public class Coin_Collectable {
Rectangle CoinBody = new Rectangle();
	
	public Coin_Collectable() {
		this.CoinBody.width = 30;
		this.CoinBody.height = 30;
	}
	
	public Coin_Collectable(float x, float y) {
		this.CoinBody.width = 30;
		this.CoinBody.height = 30;
		setXPos(x);
		setYPos(y);
	}
	
	public void setXPos(float x) {
		this.CoinBody.x = x;
	}
	
	public void setYPos(float y) {
		this.CoinBody.y = y;
	}
	
	public Rectangle getBody() {
		return this.CoinBody;
	}
	
	public float getXPos() {
		return this.CoinBody.x;
	}
	
	public float getYPos() {
		return this.CoinBody.y;
	}
	
}


