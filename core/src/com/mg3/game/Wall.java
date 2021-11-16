package com.mg3.game;

import com.badlogic.gdx.math.Rectangle;

public class Wall {
	Rectangle wall = new Rectangle();
	
	public Wall(float x, float y, float h, float w) {
		this.wall.x = x;
		this.wall.y = y;
		this.wall.height = h;
		this.wall.width = w;
	}
	
	public Rectangle getWall() {
		return this.wall;
	}
	
	public float getXPos() {
		return this.wall.x;
	}
	
	public float getYPos() {
		return this.wall.y;
	}
	
	public float getHeight() {
		return this.wall.height;
	}
	
	public float getWidth() {
		return this.wall.width;
	}
	
	public void setXPos(float x) {
		this.wall.x = x;
	}
	
	public void setYPos(float y) {
		this.wall.y = y;
	}
	
	public void setHeight(float h) {
		this.wall.height = h;
	}
	
	public void setWidth(float w) {
		this.wall.width = w;
	}
	
	public void setWall(float x, float y, float h, float w) {
		this.wall.x = x;
		this.wall.y = y;
		this.wall.height = h;
		this.wall.width = w;
	}

}
