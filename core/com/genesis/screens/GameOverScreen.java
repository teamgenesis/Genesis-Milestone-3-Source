package com.genesis.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.genesis.gamehelpers.AssetLoader;
import com.genesis.gamehelpers.InputHandler;

public class GameOverScreen implements Screen {

	private SpriteBatch batcher;
	private Game game;
	private OrthographicCamera cam;
	
	public GameOverScreen(Game game) {
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
		
		AssetLoader.fontWhite.draw(batcher, "GAME OVER", 150, 300);
		
		AssetLoader.fontWhite.draw(batcher, "PRESS SPACE TO RESTART", 150, 400);
		
		
		batcher.end();

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
