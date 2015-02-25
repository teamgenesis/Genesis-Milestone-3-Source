// GameScreen.java
// Actual in-game screen.

package com.genesis.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.genesis.gamehelpers.InputHandler;
import com.genesis.gameworld.GameRenderer;
import com.genesis.gameworld.GameWorld;
import com.genesis.globals.Globals;

public class GameScreen implements Screen {
	
	//------------------//
	// Class Attributes //
	//------------------//
	private GameWorld world;
    private GameRenderer renderer;
    private Globals globals;
    
    private float runTime = 0;
    
	//-------------//
	// Constructor //
	//-------------//
    public GameScreen(Game game) {
    	globals = new Globals();
        world = new GameWorld(game);
        renderer = new GameRenderer(world, (int)globals.getHeight(), globals.midPointY());
        
        Gdx.input.setInputProcessor(new InputHandler(world.getPlayer(), renderer.getCam()));
    }
	
	//-------------------//
	// Object Processing //
	//-------------------//
    @Override
    public void render(float delta) {
        world.update(delta);
        renderer.render(delta, world.getRunTime());
    }
	
	
	@Override
    public void resize(int width, int height) {
        System.out.println("GameScreen - resizing");
    }

    @Override
    public void show() {
        System.out.println("GameScreen - show called");
    }

    @Override
    public void hide() {
        System.out.println("GameScreen - hide called");     
    }

    @Override
    public void pause() {
        System.out.println("GameScreen - pause called");        
    }

    @Override
    public void resume() {
        System.out.println("GameScreen - resume called");       
    }

    @Override
    public void dispose() {
        // Leave blank
    }




}
