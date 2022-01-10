package com.plantventure.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.plantventure.game.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.forceExit = false;
		config.foregroundFPS = 0;
		config.vSyncEnabled = false;
		config.title = "PlantVenture";
		new LwjglApplication(Game.getInstance(), config);
	}
}
