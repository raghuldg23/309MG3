package com.mg3.game;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class Enemy {
	Rectangle EnemyBody = new Rectangle();
	Circle detection = new Circle();
	Player target;
	Vector2 destination = null;
	Vector2 direction = null;
	long timer = TimeUtils.millis();
	
	Player noTarget = new Player();
	int health = 100;
	int id = 0;
	
	public Enemy() {
		this.noTarget.setID(-1);
		this.target = noTarget;
	}
	
	public Enemy(float x, float y) {
		this.EnemyBody.x = x;
		this.EnemyBody.y = y;
		this.detection.x = x;
		this.detection.y = y;
		this.detection.radius = 500;
		
		this.EnemyBody.width = 64;
		this.EnemyBody.height = 64;
		noTarget.setID(-1);
		this.target = noTarget;
	}
	
	public Rectangle getBody() {
		return this.EnemyBody;
	}
	
	public Circle getDetection() {
		return this.detection;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public Player getTarget() {
		return this.target;
	}
	
	public long getTimer() {
		return this.timer;
	}
	
	public void setHealth(int h) {
		this.health = h;
	}
	
	public void setXPos(float x) {
		this.EnemyBody.x = x;
	}
	
	public void setYPos(float y) {
		this.EnemyBody.y = y;
	}
	
	public void setTarget(Player p) {
		this.target = p;
	}
	
	public void setNoTarget() {
		this.target = noTarget;
	}
	
	public void setTimer() {
		this.timer = TimeUtils.millis();
	}
	
	public float getXPos() {
		return this.EnemyBody.x;
	}
	
	public float getYPos() {
		return this.EnemyBody.y;
	}
}
