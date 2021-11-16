package com.mg3.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class INVPopUp {
	
	TextureRegion invBGDraw;
	TextureRegion weaponButton;
	TextureRegion armorButton;
	TextureRegion hybridButton;
	public INVPopUp() {
		Pixmap invBG = new Pixmap(200,500,Pixmap.Format.RGB888);
		invBG.setColor(Color.DARK_GRAY);
		invBG.fill();
		invBGDraw = new TextureRegion(new Texture(invBG));
		
		Pixmap buttonBG= new Pixmap(100,100,Pixmap.Format.RGB888);
		buttonBG.setColor(Color.RED);
		buttonBG.fill();
		weaponButton = new TextureRegion(new Texture(Gdx.files.internal("arrow.png")));
		armorButton = new TextureRegion(new Texture(Gdx.files.internal("armor.png")));
		hybridButton = new TextureRegion(new Texture(Gdx.files.internal("hybrid.png")));
	}
	
	public TextureRegion getINVBG() {
		return this.invBGDraw;
	}
	public TextureRegion getWeaponButton() {
		return this.weaponButton;
	}
	
	public TextureRegion getAromorButton() {
		return this.armorButton;
	}
	
	public TextureRegion getHybridButton() {
		return this.hybridButton;
	}

}
