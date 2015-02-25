package com.genesis.gamehelpers;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.genesis.gameobjects.Player;
import com.genesis.screens.GameOverScreen;
import com.genesis.screens.SplashScreen;
import com.genesis.screens.WinScreen;

public class InputHandler implements InputProcessor {

	//------------------//
	// Class Attributes //
	//------------------//
	
	private OrthographicCamera camera;
	
	private Player myPlayer;
	private SplashScreen splash;
	private GameOverScreen gameOver;
	private WinScreen win;
	
	public enum InputObject {
		PLAYER, SPLASH, GAMEOVER, WIN;
	}
	
	private static InputObject inputObject;

	//-------------//
	// Constructor //
	//-------------//
	
	public InputHandler(Player myPlayer, OrthographicCamera camera) {
		this.myPlayer = myPlayer;
		this.camera = camera;
		inputObject = InputObject.PLAYER;
	}
	
	public InputHandler(SplashScreen splash) {
		this.splash = splash;
		inputObject = InputObject.SPLASH;
	}
	
	public InputHandler(GameOverScreen gameOver) {
		this.gameOver = gameOver;
		inputObject = InputObject.GAMEOVER;
	}
	
	public InputHandler(WinScreen win) {
		this.win = win;
		inputObject = InputObject.WIN;
	}

	//-------------------//
	// Object Processing //
	//-------------------//
	
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Input.Keys.UP: {
				if(inputObject == InputObject.PLAYER){
					myPlayer.setMoveUp(true);
					break;
				}
				
			}
			case Input.Keys.DOWN: {
				if(inputObject == InputObject.PLAYER){
					myPlayer.setMoveDown(true);
					break;
				}
			}
			case Input.Keys.LEFT: {
				if(inputObject == InputObject.PLAYER){
					myPlayer.setMoveLeft(true);
					break;
				}
			}
			case Input.Keys.RIGHT: {
				if(inputObject == InputObject.PLAYER){
					myPlayer.setMoveRight(true);
					break;
				}
			}

			case Input.Keys.SPACE: {
				if(inputObject == InputObject.SPLASH){
					splash.onSpace();
					break;
				} else if(inputObject == InputObject.GAMEOVER) {
					gameOver.onSpace();
					break;
				} else if(inputObject == InputObject.WIN) {
					win.onSpace();
					break;
				}
			}
		}
		
		return true; // function is triggered.
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
			case Input.Keys.UP: {
				if(inputObject == InputObject.PLAYER){
						myPlayer.setMoveUp(false);
						break;	
				}
			}
			case Input.Keys.DOWN: {
				if(inputObject == InputObject.PLAYER){
					myPlayer.setMoveDown(false);
					break;
				}
			}
			case Input.Keys.LEFT: {
				if(inputObject == InputObject.PLAYER){
					myPlayer.setMoveLeft(false);
					break;
				}
			}
			case Input.Keys.RIGHT: {
				if(inputObject == InputObject.PLAYER){
					myPlayer.setMoveRight(false);
					break;
				}
			}

		}
	
		return true; // function is triggered.
	}

	@Override
	public boolean keyTyped(char character) {
		// not used, left blank
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;// function is triggered.
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		//Vector3 touchPos = new Vector3(Gdx.input.getX(pointer), Gdx.input.getY(pointer), 0);
		//camera.unproject(touchPos);
		
		//Vector3 playerPos = new Vector3(myPlayer.getX(), myPlayer.getY(), 0);
		
		//myPlayer.touchDrag(touchPos, playerPos);
		
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// not used, left blank
		return false;
	}
	


}
