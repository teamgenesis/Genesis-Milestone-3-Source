package com.genesis.screens;

import java.text.DecimalFormat;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.genesis.gamehelpers.AssetLoader;
import com.genesis.gamehelpers.InputHandler;
import com.genesis.globals.Globals;

public class SplashScreen implements Screen {

	private SpriteBatch batcher;
	private Game game;
	private OrthographicCamera cam;
	
	private DecimalFormat format = new DecimalFormat("0.00");
	
	public SplashScreen(Game game) {
		this.game = game;
		this.batcher = new SpriteBatch();
		cam = new OrthographicCamera();
        cam.setToOrtho(true, 600, 700);
        
        // Attach batcher to camera
        batcher.setProjectionMatrix(cam.combined);
        
        Gdx.input.setInputProcessor(new InputHandler(this));
	}
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batcher.begin();
        
        batcher.disableBlending();
        batcher.draw(AssetLoader.bgSplash, 0, 0, 600, 700);
        batcher.enableBlending();
        
        batcher.draw(AssetLoader.gameLogo, 100, 100, 400, 100);

        AssetLoader.fontWhite.draw(batcher, "PRESS SPACE TO START", 150, 300);
        
        AssetLoader.fontWhite.draw(batcher, "FASTEST TIME: " + format.format(Globals.fastestTime), 150, 450);
      
        
        batcher.end();
	}
	
	public void onSpace() {
		game.setScreen(new GameScreen(game));
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	public Game getGame() {
		return game;
	}

}
