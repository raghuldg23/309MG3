package com.mg3.game;

import com.badlogic.gdx.math.Rectangle;

//NPC
public class NPC {
	Rectangle NPCBody = new Rectangle();
	
	public NPC() {
		this.NPCBody.width = 2;
		this.NPCBody.height = 2;
	}
	
	
	float getXPos() {
		return this.NPCBody.x;
	}
	
	float getYPos() {
		return this.NPCBody.y;
	}
	
}
