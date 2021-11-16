package com.mg3.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ShopPopUp {
	
	TextureRegion shopBGDraw;
	Texture weaponButton;
	Texture armorButton;
	Texture hybridButton;
	
	
	public ShopPopUp() {
		Pixmap shopBG = new Pixmap(600,650,Pixmap.Format.RGB888);
		shopBG.setColor(Color.DARK_GRAY);
		shopBG.fill();
		shopBGDraw = new TextureRegion(new Texture(shopBG));
		
		weaponButton = new Texture(Gdx.files.internal("arrow.png"));
		armorButton = new Texture(Gdx.files.internal("armor.png"));
		hybridButton = new Texture(Gdx.files.internal("hybrid.png"));
	}
	
	public TextureRegion getShopBG() {
		return this.shopBGDraw;
	}
	
	public Texture getWeaponButton() {
		return this.weaponButton;
	}
	
	public Texture getAromorButton() {
		return this.armorButton;
	}
	
	public Texture getHybridButton() {
		return this.hybridButton;
	}

}
