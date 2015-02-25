// GameWorld.java
// Virtual world for the game.

package com.genesis.gameworld;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Circle;
import com.genesis.gameobjects.BulletEnemy;
import com.genesis.gameobjects.EnemyCarrier;
import com.genesis.gameobjects.Player;
import com.genesis.globals.Globals;
import com.genesis.screens.GameOverScreen;
import com.genesis.screens.WinScreen;

public class GameWorld {

	//------------------//
	// Class Attributes //
	//------------------//

	// main player
	private Player player;
	
	// boss
	private EnemyCarrier carrier;
	
	// the game itself
	private Game game;

	// game timer
	private float time = 0;
	
	// game states
	public enum GameState {
		READY, RUNNING, GAMEOVER
	}
	
	private static GameState currentState;
	
	
	
	//-------------//
	// Constructor //
	//-------------//
	
	public GameWorld(Game game) {
		currentState = GameState.READY;
		this.game = game;
		
		// initialising main player.
		initPlayer();
		
		// initialising boss.
		initCarrier();	
    }
	
	//-----------------------//
	// Object Initialisation //
	//-----------------------//
	
	// Generates player and boss at the specified coordinates.
	
	public void initPlayer() {
		player = new Player(Globals.midPointX - 25, 500);
	}
	
	public void initCarrier() {
		carrier = new EnemyCarrier(Globals.midPointX - 100, -300);
	}
	
	//-------------------//
	// Object Processing //
	//-------------------//

	public void update(float delta) {
		
		// starts game when carrier moves into position.
		if(this.checkReady(carrier)) {
			this.start();
		}
		
		switch(currentState) {		
			case READY: {
				updateReady(delta);
				break;
			}
			
			case RUNNING: {
				updateRunning(delta);
				break;
			}
			
			case GAMEOVER: {
				game.setScreen(new GameOverScreen(game));
			}
			
			default:
				break;
		}
		
	}
	
	public void updateReady(float delta) {
		carrier.updateReady(delta);
	}
	
	public void updateRunning(float delta) {
		if(!carrier.getAlive() ) {
			Globals.fastestTime = time;
			game.setScreen(new WinScreen(game));
		} else if (!player.getAlive()){
			game.setScreen(new GameOverScreen(game));	
		} else {
			
			// updating current game run time
			time += delta;
			
			player.updateRunning(delta, carrier);
			carrier.updateRunning(delta, player);
		}
		
	}
	
	
	
	public boolean checkReady(EnemyCarrier carrier) {
		return carrier.reachedSP();
	}
	
	public boolean isReady() {
		return currentState == GameState.READY;
	}

	public boolean isGameOver() {
		return currentState == GameState.GAMEOVER;
	}


	public boolean isRunning() {
		return currentState == GameState.RUNNING;
	}
	
	public void start() {
		currentState = GameState.RUNNING;
	}
	
	public void ready() {
		currentState = GameState.READY;
	}
		
	//---------------------//
	// Attribute Accessors //
	//---------------------//

	public Player getPlayer() {
        return player;

    }
	
	public EnemyCarrier getCarrier() {
		return carrier;
	}
	
	public float getRunTime() {
		return time;
	}
	
	


	
}
