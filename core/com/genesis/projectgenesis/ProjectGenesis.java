// ProjectGenesis.java
// contains game core.


package com.genesis.projectgenesis;

import com.badlogic.gdx.Game;
import com.genesis.gamehelpers.AssetLoader;
import com.genesis.screens.GameScreen;
import com.genesis.screens.SplashScreen;

public class ProjectGenesis extends Game {

	
	@Override
	public void create () {
		System.out.println("game created");
		AssetLoader.load();
		setScreen(new SplashScreen(this));
	}
	
	@Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }

}
