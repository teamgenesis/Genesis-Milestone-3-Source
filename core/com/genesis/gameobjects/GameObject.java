// GameObject.java
// (Abstract) Parent class for all in-game objects.


package com.genesis.gameobjects;

import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {

	//------------------//
	// Class Attributes //
	//------------------//
	
	// - object position - //
	private Vector2 position;
	
	// velocity = number of pixels moved per second
	private Vector2 velocity;
	
	// acceleration = change in velocity
	private Vector2 acceleration;
			
	// - visibility of object on screen - //
	private boolean visible;
	
	// - object dimensions -//
	private int width;
	private int height;
	
	//-------------//
	// Constructor //
	//-------------//
	
	public GameObject(float x, float y, int width, int height) {
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, 0);
		
		this.width = width;
		this.height = height;
		
		visible = true;
	}
	
	//-------------------//
	// Object Processing //
	//-------------------//
	
	// updates object position on screen.
	public void update(float delta) {
		velocity.add(acceleration.scl(delta));
		position.add(velocity.scl(delta));
	}
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//
	
	public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }
    
    public int getWidth() {
    	return width;
    }
    
    public int getHeight() {
    	return height;
    }
    
    public Vector2 getPosition() {
    	return position;
    }
    
    public Vector2 getVelocity() {
    	return velocity;
    }
    
    public Vector2 getAcceleration() {
    	return acceleration;
    }

    public boolean getVisible() {
    	return visible;
    }
    
	
	//--------------------//
	// Attribute Mutators //
	//--------------------//
    
    public void setVelocity(float x, float y) {
    	this.velocity.x = x;
    	this.velocity.y = y;
    	
    }
    
    public void setAcceleration(float x, float y) {
    	this.acceleration.x = x;
    	this.acceleration.y = y;
    }
    
    public void setPosition(Vector2 velocity) {
    	this.position.add(velocity);
    }
    
    public void setPosition(float x, float y, float delta) {
    	this.position.add(new Vector2(x, y).scl(delta));
    }

    public void setVisible(boolean visible) {
    	this.visible = visible;
    }
    
    public void setX(float x) {
    	this.position.add(x, 0);
    }
    
    public void setY(float y) {
    	this.position.add(0, y);
    }
    
}
