package com.genesis.gameobjects;



public abstract class Enemy extends GameObject {
	//------------------//
	// Class Attributes //
	//------------------//

	// object hit points
	private static int health;
	
	// determining if object is destroyed by player bullet
	private boolean alive;
	

	//-------------//
	// Constructor //
	//-------------//
	
	public Enemy(float x, float y, int width, int height, int health) {
		// calling parent class constructor
		super(x, y, width, height);
		
		this.health = health;
		this.alive = true;
				
	}

	//-------------------//
	// Object Processing //
	//-------------------//
	
	public abstract void update(float delta, Player player);

	//---------------------//
	// Attribute Accessors //
	//---------------------//
	

	
	public boolean getAlive() {
		return alive;
	}
	
	public int getHealth() {
		return health;
	}
			
	//--------------------//
	// Attribute Mutators //
	//--------------------//

		
	public void setAlive(boolean bool) {
		this.alive = bool;
	}
		
	public void loseHealth() {
		health--;
	}
	
	public void die() {
		setAlive(false);
	}
}
