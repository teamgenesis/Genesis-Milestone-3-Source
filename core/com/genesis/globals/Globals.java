package com.genesis.globals;

import com.badlogic.gdx.Gdx;

public class Globals {

	//------------------//
	// Class Attributes //
	//------------------//

	// game screen dimensions 
	public static final float SCREEN_WIDTH = 600;
	public static float screenHeight;
	
	// device screen dimensions
	public static float deviceWidth;
	public static float deviceHeight;
	
	// specific points on screen
	public static int midPointX;
	public static int midPointY;
	
	//game timer
	public static float fastestTime = 0.00f;
	
	//-------------//
	// Constructor //
	//-------------//
	public Globals() {
		deviceWidth = Gdx.graphics.getWidth();
		deviceHeight = Gdx.graphics.getHeight();
		
		screenHeight = deviceHeight / (deviceWidth / SCREEN_WIDTH);
		
		midPointX = (int) SCREEN_WIDTH / 2;
		midPointY = (int) screenHeight / 2;
	}

	//-------------------//
	// Object Processing //
	//-------------------//

	//---------------------//
	// Attribute Accessors //
	//---------------------//
	
	public float getWidth() {
		return SCREEN_WIDTH;
	}
	
	public float getHeight() {
		return screenHeight;
	}
	
	public float deviceWidth() {
		return deviceWidth;
	}
	
	public float deviceHeight() {
		return deviceHeight;
	}
	
	public int midPointX() {
		return midPointX;
	}
	
	public int midPointY() {
		return midPointY;
	}

	//--------------------//
	// Attribute Mutators //
	//--------------------//

	public void setFastestTime(float time) {
		Globals.fastestTime = time;
	}


	
}
