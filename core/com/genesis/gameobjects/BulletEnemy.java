package com.genesis.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.genesis.gameobjects.Bullet;
import com.genesis.globals.Globals;

public class BulletEnemy extends Bullet {

	//------------------//
	// Class Attributes //
	//------------------//
	
	private float movementSpeed;
	
	private Circle collisionCircle;
	
	//-------------//
	// Constructor //
	//-------------//
	
	public BulletEnemy(float x, float y, int width, int height, float movementSpeed) {
		super(x, y, width, height);
		this.movementSpeed = movementSpeed;
		collisionCircle = new Circle();
	}

	//-------------------//
	// Object Processing //
	//-------------------//

	@Override
	public void update(float delta) {
		collisionCircle.set(this.getX(), this.getY(), (float)this.getWidth());
		
		this.setVelocity(0, movementSpeed * delta);
		
		this.getPosition().add(this.getVelocity().scl(delta));
		
		if (checkOutOfScreen()) {
			this.setVisible(false);
		}
		
	}
	
	public boolean collides(Player player) {
		return (Intersector.overlaps(player.getCollisionCircle(), this.getCollisionCircle()));
	}
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//

	@Override
	public boolean checkOutOfScreen() {
		return (
					(this.getX() < 0 || this.getX() > Globals.SCREEN_WIDTH)
			     || (this.getY() < 0 || this.getY() > Globals.screenHeight)
			   ); 
			
	}
	
	public float getSpeed() {
		return movementSpeed;
	}
	
	public Circle getCollisionCircle() {
		return collisionCircle;
	}
	//--------------------//
	// Attribute Mutators //
	//--------------------//




	
	

}
