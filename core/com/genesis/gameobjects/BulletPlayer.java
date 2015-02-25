package com.genesis.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class BulletPlayer extends Bullet {

	//------------------//
	// Class Attributes //
	//------------------//
		
	// object characteristics
	private static final float MOVEMENT_SPEED = 50000;
	
	private Rectangle collisionRect;
	
	private Circle collisionCircle;
	
	//-------------//
	// Constructor //
	//-------------//

	public BulletPlayer(float x, float y, int width, int height) {
		super(x, y, width, height);
		collisionRect = new Rectangle();
		// collisionCircle = new Circle();
		
	}
	
	//-------------------//
	// Object Processing //
	//-------------------//

	public void update(float delta) {
		collisionRect.set(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		// collisionCircle.set(this.getX(), this.getY(), (float)this.getWidth());
		
		// player shoots straight.
		// only the Y-coordinates are of concern.	
		this.setVelocity(0, MOVEMENT_SPEED * delta * -1);
		
		this.getPosition().add(this.getVelocity().scl(delta));
		
		if (checkOutOfScreen()) {
			this.setVisible(false);
		}
		
	}
	
	// returns true if bullet travels out of screen dimensions.
	// returns false otherwise.
	public boolean checkOutOfScreen() {
		return this.getY() < 0;
	}
	
	public boolean collides(EnemyCarrier carrier) {
		if(Intersector.overlaps(collisionRect, carrier.getPOCHead())
			|| Intersector.overlaps(collisionRect, carrier.getPOCWings())	
		  ) {
			return true;
		}
			
		return false;
	}
	
	public Circle getCollisionCircle() {
		return collisionCircle;
	}
	
	public Rectangle getCollisionRect() {
		return collisionRect;
	}



}
