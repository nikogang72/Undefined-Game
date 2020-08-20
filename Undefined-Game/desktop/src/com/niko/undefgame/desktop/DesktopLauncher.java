package com.niko.undefgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.niko.cfg.cfg;
import com.niko.undefgame.UndefinedCore;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Undefined Game";
		config.resizable = false;
		config.foregroundFPS = cfg.fps;
		config.width = cfg.VIRTUAL_WIDTH;
		config.height = cfg.VIRTUAL_HEIGTH;
		new LwjglApplication(new UndefinedCore(), config);
	}
}
