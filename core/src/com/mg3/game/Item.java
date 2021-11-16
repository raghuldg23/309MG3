package com.mg3.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Item {
	
	public String name = null; // = image file name
	public int id = -1;
	// 1 = Weapon, 2 = Armor, 3 = Shoes
	public int attribute = -1;
	public int[] effects = null;
	public Rectangle ItemBody = new Rectangle();
    public int itemId = -1;
	
    public Item() {
		
	}

	public Item(String name, int ID, int attribute, int [] effects) {
		this.name = name;
		this.ItemBody.width = 20;
		this.ItemBody.height = 20;
		this.id = ID;
		this.attribute = attribute;
		this.effects = effects;
	}
	
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setXPos(float x) {
		this.ItemBody.x = x;
	}
	
	public void setYPos(float y) {
		this.ItemBody.y = y;
	}
	
	public Rectangle getBody() {
		return this.ItemBody;
	}
	
	public float getXPos() {
		return this.ItemBody.x;
	}
	
	public float getYPos() {
		return this.ItemBody.y;
	}

	public int getAttribute() {
		return attribute;
	}
	
	
}
