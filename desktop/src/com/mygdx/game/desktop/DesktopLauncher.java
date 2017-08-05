package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title 	= "Alchemy game";
		config.useGL30 	= false;
		config.width 	= 960;
		config.height 	= 720;
		config.resizable= false;
		config.foregroundFPS = 60;
		config.backgroundFPS = 60;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
