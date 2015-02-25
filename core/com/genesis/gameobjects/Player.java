// Player.java
// User-controlled player.

package com.genesis.gameobjects;

import java.util.ArrayList;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.genesis.gameobjects.Bullet;
import com.genesis.globals.Globals;

public class Player extends GameObject {

	//------------------//
	// Class Attributes //
	//------------------//

	private boolean alive;
		
	// movement processing
	private boolean moveUp;
	private boolean moveDown;
	private boolean moveLeft;
	private boolean moveRight;
	
	// constants
	private static final float MOVEMENT_SPEED = 15000;
	private static final int WIDTH = 64;
	private static final int HEIGHT = 64;
	
	/* NOTE: image is square, therefore CENTRE represents for both x and y axes */
	private static final int CENTRE = 32;
	
	// the lower the value, the greater the rate of fire
	private static final int RATE_OF_FIRE = 5;
	
	// "inventory" of bullets
	private ArrayList<BulletPlayer> bullets = new ArrayList<BulletPlayer>();
	
	// points of collision:
	// collisionCircle for bullet collisions with player
	// collisionRect for physical collision between player and ship
	private Circle collisionCircle;
	private Rectangle collisionRect;
	
	// firing timer to control rate of fire
	private int firingTimer = 0;
	
	
	//-------------//
	// Constructor //
	//-------------//
	
	public Player(float x, float y) {
		// calls parent constructor
		super(x, y, WIDTH, HEIGHT);

		alive = true;

		moveUp = false;
		moveDown = false;
		moveLeft = false;
		moveRight = false;
		
		collisionCircle = new Circle();
		collisionRect = new Rectangle();
		
	}
	
	//-------------------//
	// Object Processing //
	//-------------------//
	
	// this update function will be called at every frame.
	public void updateRunning(float delta, EnemyCarrier carrier) {
		
		// fire() will be called at every instance of updateRunning()
		// such that it simulates auto-fire.
		this.fire(delta, carrier);

		// Up, Down: y-axis is modified.
		// Left, Right: x-axis is modified.
		// Game origin (0, 0) located at top left corner of screen.
		
		// sets velocity first, then adds it to current position.
		if(moveUp && this.getY() > 0) {
			this.setVelocity(0, MOVEMENT_SPEED * delta * -1);
			this.getPosition().add(this.getVelocity().scl(delta));
		}
		
		if(moveDown && this.getY() + 64 < 700) {
			this.setVelocity(0, MOVEMENT_SPEED * delta);
			this.getPosition().add(this.getVelocity().scl(delta));
		}
		
		if(moveLeft && this.getX() > 0) {
			this.setVelocity(MOVEMENT_SPEED * delta * -1, 0);
			this.getPosition().add(this.getVelocity().scl(delta));
		}
		
		if(moveRight && this.getX() + 64 < 600) {
			this.setVelocity(MOVEMENT_SPEED * delta, 0);
			this.getPosition().add(this.getVelocity().scl(delta));
		}
		
		// updates current location of the collision circle and rectangle
		collisionCircle.set(this.getX() + CENTRE, this.getY() + CENTRE, 6.5f);
		collisionRect.set(this.getX() + 10, this.getY() + 10, this.getWidth() - 20, this.getHeight() - 20);
		
		// reset velocity to 0.
		// this is to prevent continuous movement even when key is up.
		this.setVelocity(0, 0);
	}
	
	//-----------------//
	// Object Movement //
	//-----------------//
	
	// setMoveLeft/Right/Up/Down:
	// flags to trigger which direction the player is moving.
	// only one flag will remain true at any point of time.
	public boolean setMoveLeft(boolean move) {
		if(moveRight && move) {
			moveRight = false;
		}
		moveLeft = move;
		
		return true;
	}
	
	public boolean setMoveRight(boolean move) {	
		if(moveLeft && move) {
			moveLeft = false;
		}
		moveRight = move;	
		
		return true;
	}
	
	public boolean setMoveUp(boolean move) {
		if(moveDown && move) {
			moveDown = false;
		}

		moveUp = move;
		
		return true;
	}
	
	public boolean setMoveDown(boolean move) {
		if(moveUp && move) {
			moveUp = false;
		}
		
		moveDown = move;
		
		return true;
	}
	
	
	// firing mechanism for player
	public void fire(float delta, EnemyCarrier carrier) {
		// controls burst shot
		if(firingTimer % 3 != 0) {
			
			// controls rate of fire
			if(firingTimer % RATE_OF_FIRE == 0) {
				// create new bullet, set bullet position to player center mass
				BulletPlayer bullet = new BulletPlayer(this.getX() + CENTRE - 7, this.getY() + CENTRE, 15, 29);
				
				// add bullet to inventory
				bullets.add(bullet);
			}
		}
			
		for(int i = 0; i < bullets.size(); i++) {
			if(bullets.get(i).checkOutOfScreen()) {
				// destroy bullet if it exceeds screen boundaries
				bullets.remove(i);
			} else if (bullets.get(i).collides(carrier)) {
				// check for bullet collision with enemy ship
				carrier.loseHealth();
				bullets.remove(i);
			} else {
				bullets.get(i).update(delta);
			}
		}
		
		firingTimer++;
	}
	
	/*
	public void touchDrag(Vector3 touchPos, Vector3 playerPos, float delta) {
		Vector3 deltaV3 = touchPos.sub(playerPos);
		deltaV3.nor();
		
	}
	*/
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//
	
	public boolean getAlive() {
		return alive;
	}
		
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public Circle getCollisionCircle() {
		return collisionCircle;
	}
	
	public ArrayList getBullets() {
		return bullets;
	}
	
	public int getCentre() {
		return CENTRE;
	}
	
	public Rectangle getCollisionRect() {
		return collisionRect;
	}

	//--------------------//
	// Attribute Mutators //
	//--------------------//

	public void die() {
		alive = false;
	}

	
}
