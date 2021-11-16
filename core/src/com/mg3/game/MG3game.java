package com.mg3.game;

import com.badlogic.gdx.Game;
import com.mg3.screens.MainGameScreen;
import com.mg3.screens.WelcomePage;

public class MG3game extends Game {
	
	private WelcomePage welcome;
	private MainGameScreen gameScreen;
	public String playerName;
	public String serverIP;
	
	@Override
	public void create () {
		welcome = new WelcomePage(this);
		
		gameScreen = new MainGameScreen(this);
		
		this.setScreen(welcome);
	}

	@Override
	public void render () {
		super.render();
	}
	
	public void showGameScreen() {
		setScreen(gameScreen);
		
		if(welcome != null) {
			welcome.dispose();
			welcome = null;
		}
	}
	
	@Override
	public void dispose () {

	}

}

