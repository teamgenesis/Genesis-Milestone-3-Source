package com.genesis.gamehelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

	//------------------//
	// Class Attributes //
	//------------------//

	// master image file
	public static Texture texture;
	
	// texture atlas (image position referencing)
	public static TextureAtlas atlas;
	
	// main player
	public static TextureRegion player;
	
	// enemy ships
	// public static TextureRegion enemyStation;
	public static TextureRegion enemyCarrier;
		
	// game screen background
	public static TextureRegion bgStars;
	
	// splash screen
	public static TextureRegion gameLogo;
	public static TextureRegion bgSplash;
	
	// game font
	public static BitmapFont fontWhite;
	
	// bullets
	public static TextureRegion bulletPlayer;
	public static TextureRegion bulletEnemySprd;
	public static TextureRegion bulletEnemyStr;
	
	//-------------//
	// Constructor //
	//-------------//

	//-------------------//
	// Object Processing //
	//-------------------//
	
	public static void load() {
		
		// load game font
		fontWhite = new BitmapFont(Gdx.files.internal("fonts/genesis_font.fnt"), true);
		
		// load master texture file
		texture = new Texture(Gdx.files.internal("images/genesis.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		atlas = new TextureAtlas(Gdx.files.internal("images/genesis.txt"));
		
		// load background
		bgStars = atlas.findRegion("spacebackgrd_vertical");
		bgStars.flip(false, true);
		
		// load enemies
		// enemyStation = atlas.findRegion("spaceship4");
		enemyCarrier = atlas.findRegion("spaceship7_trimmed");
		// carrier is not flipped, already in position.
				
		// load main player
		player = atlas.findRegion("mainplayer_small");
		player.flip(false, true);
		
		// load splash screen
		bgSplash = atlas.findRegion("splash_screen_m2_portrait");
		bgSplash.flip(false, true);
		
		// load game logo
		gameLogo = atlas.findRegion("genesislogo_small");
		gameLogo.flip(false, true);
		
		bulletPlayer = atlas.findRegion("playerbullet_vertical");
		bulletPlayer.flip(false, true);
		
		bulletEnemySprd = atlas.findRegion("carrier_sprd_alt");
		
		bulletEnemyStr = atlas.findRegion("carrier_str");

	}
	
	public static void dispose() {
        // We must dispose of the texture when we are finished.
        texture.dispose();
    }


	//---------------------//
	// Attribute Accessors //
	//---------------------//

	//--------------------//
	// Attribute Mutators //
	//--------------------//

	
}
