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

public class WinScreen implements Screen {

	private SpriteBatch batcher;
	private Game game;
	private OrthographicCamera cam;
	
	private DecimalFormat format;
	
	public WinScreen(Game game) {
		format = new DecimalFormat("#.##");
		this.game = game;
		this.batcher = new SpriteBatch();
		cam = new OrthographicCamera();
        cam.setToOrtho(true, 600, 700);
        
        // Attach batcher to camera
        batcher.setProjectionMatrix(cam.combined);
        
        Gdx.input.setInputProcessor(new InputHandler(this));
	}
	
	public void onSpace() {
		game.setScreen(new GameScreen(game));
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batcher.begin();
		
		AssetLoader.fontWhite.draw(batcher, "YOU WIN", 150, 300);
		
		AssetLoader.fontWhite.draw(batcher, "TIME TAKEN: " + format.format(Globals.fastestTime) + "s", 150, 400);
		
		AssetLoader.fontWhite.draw(batcher, "PRESS SPACE TO RESTART", 125, 500);
		
		
		batcher.end();

	}
	
	public String toString(float runTime) {
		return String.format("%s", runTime);
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

}
