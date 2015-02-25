package com.genesis.gameobjects;

public abstract class Bullet extends GameObject {
	
	//------------------//
	// Class Attributes //
	//------------------//
	//-------------//
	// Constructor //
	//-------------//

	public Bullet(float x, float y, int width, int height) {
		super(x, y, width, height);
		
	}

	//-------------------//
	// Object Processing //
	//-------------------//

	@Override
	public void update(float delta) {}
	
	public abstract boolean checkOutOfScreen();

	
	//---------------------//
	// Attribute Accessors //
	//---------------------//


	//--------------------//
	// Attribute Mutators //
	//--------------------//



	

	
	
	


}
