package com.genesis.projectgenesis.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.genesis.projectgenesis.ProjectGenesis;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Project Genesis";
		config.width = 600;
		config.height = 700;
		config.useGL30 = false;
		
		new LwjglApplication(new ProjectGenesis(), config);
	}
}
