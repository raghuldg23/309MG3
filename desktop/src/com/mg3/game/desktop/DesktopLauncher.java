package com.mg3.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mg3.game.MG3game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "2D Battle Game";
		config.width = 1280;
		config.height = 720;
		
		new LwjglApplication(new MG3game(), config);
	}
}
