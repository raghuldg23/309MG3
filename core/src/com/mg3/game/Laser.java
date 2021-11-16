package com.mg3.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class Laser {
	Rectangle laserBody = new Rectangle();
	int id = 0;
	int damage;
	Vector2 direction;
	long timeSpawned;
	float angle;
	
	public Laser() {
		this.laserBody.width = 30;
		this.laserBody.height = 30;
		this.direction = new Vector2();
		timeSpawned = TimeUtils.millis();
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setXPos(float x) {
		this.laserBody.x = x;
	}
	
	public void setYPos(float y) {
		this.laserBody.y = y;
	}
	
	public void setDamage(int d) {
		this.damage = d;
	}
	
	public void setMoveDirection(Vector2 dir) {
		angle = dir.angle();
		this.direction.x = 1f;
		this.direction.y = 0;
		this.direction.setAngle(angle);
	}
	
	public int getID() {
		return this.id;
	}
	
	public float getXPos() {
		return this.laserBody.x;
	}
	
	public float getYPos() {
		return this.laserBody.y;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public long getTimeSpawned() {
		return this.timeSpawned;
	}
	
	public Rectangle getBody() {
		return this.laserBody;
	}
	
	public void move(float deltaTime) {
		this.setYPos(this.getYPos() + direction.y * 900 * deltaTime);
		this.setXPos(this.getXPos() + direction.x * 900 * deltaTime);
	}
	public float getAngle()
	{
		return angle;
	}
}
