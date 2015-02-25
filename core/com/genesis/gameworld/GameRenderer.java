package com.genesis.gameworld;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.genesis.gamehelpers.AssetLoader;
import com.genesis.gameobjects.BulletEnemy;
import com.genesis.gameobjects.BulletPlayer;
import com.genesis.gameobjects.EnemyCarrier;
import com.genesis.gameobjects.Player;

public class GameRenderer {

	//------------------//
	// Class Attributes //
	//------------------//
		
	private GameWorld myWorld;
	
	private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    
    private SpriteBatch batcher;
    
    private Player player;
    private ArrayList<BulletPlayer> playerBulletList;
    
    private EnemyCarrier carrier;
    private ArrayList<BulletEnemy> cBStrWingL1; 
    private ArrayList<BulletEnemy> cBStrWingL2;
    private ArrayList<BulletEnemy> cBStrWingR1;
    private ArrayList<BulletEnemy> cBStrWingR2;
    
    private Queue<ArrayList<BulletEnemy>> carrierCoreSprd;
    private Queue<ArrayList<BulletEnemy>> carrierWingLSprd;
    private Queue<ArrayList<BulletEnemy>> carrierWingRSprd;
    
    // private Rectangle healthBar;
    
    private float runTime;
    
    private int midPointY;
    private int gameHeight;
	
    private DecimalFormat format = new DecimalFormat("#.##");
    
	//-------------//
	// Constructor //
	//-------------//
    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
    	// make reference to GameWorld
        myWorld = world;
        
        runTime = myWorld.getRunTime(); 
        		
        player = myWorld.getPlayer();
        playerBulletList = player.getBullets();
        
        carrier = myWorld.getCarrier();
        cBStrWingL1 = carrier.bStrWingL1();
        cBStrWingL2 = carrier.bStrWingL2();
        cBStrWingR1 = carrier.bStrWingR1();
        cBStrWingR2 = carrier.bStrWingR2();
        
        carrierCoreSprd = carrier.bSpreadCore();
        
        carrierWingLSprd = carrier.bSpreadWingL();
        carrierWingRSprd = carrier.bSpreadWingR();
        
        //healthBar = carrier.getHealthBar();
        
        this.gameHeight = gameHeight;
        this.midPointY = midPointY;
        
        // setting up orthographic camera scaled to actual
        // screen resolution
        cam = new OrthographicCamera();
        cam.setToOrtho(true, 600, 700);
        
        batcher = new SpriteBatch();
        // Attach batcher to camera
        batcher.setProjectionMatrix(cam.combined);
        
        // attaching shape renderer to camera
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
    }
    
	//-------------------//
	// Object Processing //
	//-------------------//
    public void render(float delta, float runTime) {    	
		// Clear screen to black 
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Begin SpriteBatch
        batcher.begin();
        
        // drawing game-screen background
        // blending is disabled for non-transparency
        batcher.disableBlending();
        batcher.draw(AssetLoader.bgStars, 0, 0, 600, 960);
        
        
        // drawing game objects
        batcher.enableBlending();
        AssetLoader.fontWhite.draw(batcher, "Time elapsed: " + format.format(runTime) + "s", 305f, 670f);
        //AssetLoader.fontWhite.draw(batcher, toString((float)carrier.getHealth()), 425f, 640f);
        //AssetLoader.fontWhite.draw(batcher, toString(player.getX()), 305f, 640f);
        //AssetLoader.fontWhite.draw(batcher, toString(player.getY()), 470f, 640f);
        batcher.draw(AssetLoader.player, player.getX(), player.getY(), player.getWidth(), player.getHeight());
        batcher.draw(AssetLoader.enemyCarrier, carrier.getX(), 
        		carrier.getY(), carrier.getWidth(), carrier.getHeight() );
        
        for(BulletPlayer bullet : playerBulletList) {
        	batcher.draw(AssetLoader.bulletPlayer, bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
        }
        
        for(BulletEnemy bLW1 : this.cBStrWingL1) {
			//shapeRenderer.setColor(Color.BLUE);
    		batcher.draw(AssetLoader.bulletEnemyStr, bLW1.getX() - 10, bLW1.getY() - 10, 20, 20);
		}
		
		for(BulletEnemy bLW2 : this.cBStrWingL2) {
			//shapeRenderer.setColor(Color.BLUE);
			batcher.draw(AssetLoader.bulletEnemyStr, bLW2.getX() - 10, bLW2.getY() - 10, 20, 20);
		}
		
		for(BulletEnemy bRW1 : this.cBStrWingR1) {
			//shapeRenderer.setColor(Color.BLUE);
			batcher.draw(AssetLoader.bulletEnemyStr, bRW1.getX() - 10, bRW1.getY() - 10, 20, 20);
		}
		
		for(BulletEnemy bRW2 : this.cBStrWingR2) {
			//shapeRenderer.setColor(Color.BLUE);
			batcher.draw(AssetLoader.bulletEnemyStr, bRW2.getX() - 10, bRW2.getY() - 10, 20, 20);
		}
        
        
        for(ArrayList<BulletEnemy> sprdCoreList : carrierCoreSprd) {
			for(int i = 0; i < carrier.getBulletsInSprd(); i++) {
				batcher.draw(AssetLoader.bulletEnemySprd, sprdCoreList.get(i).getX() - 15, 
									 sprdCoreList.get(i).getY() - 15,
									 30, 30);
			}
		}
        
        for(ArrayList<BulletEnemy> sprdCoreList : carrierWingLSprd) {
			for(int i = 0; i < carrier.getBulletsInSprd(); i++) {
				batcher.draw(AssetLoader.bulletEnemySprd, sprdCoreList.get(i).getX() - 15, 
						 sprdCoreList.get(i).getY() - 15,
						 30, 30);
			}
		}
		
		for(ArrayList<BulletEnemy> sprdCoreList : carrierWingRSprd) {
			for(int i = 0; i < carrier.getBulletsInSprd(); i++) {
				batcher.draw(AssetLoader.bulletEnemySprd, sprdCoreList.get(i).getX() - 15, 
						 sprdCoreList.get(i).getY() - 15,
						 30, 30);
			}
		}
        
        // End SpriteBatch
        batcher.end();
        
        // Begin ShapeRenderer
        shapeRenderer.begin(ShapeType.Filled);
        
        // draw collision circle
        shapeRenderer.setColor(Color.PURPLE);
        shapeRenderer.circle(player.getCollisionCircle().x, player.getCollisionCircle().y, 
        						player.getCollisionCircle().radius);
        
        /*for(BulletPlayer bullet : playerBulletList) {
    		shapeRenderer.setColor(Color.PURPLE);
    		shapeRenderer.circle(bullet.getX(), bullet.getY(), 6.5f);
    	}*/
        
        
        /*
        if(carrier.getHealth() >= 412.5 && carrier.getHealth() <= 750) {
        	shapeRenderer.setColor(Color.GREEN);
        } else if (carrier.getHealth() >= 187.5 && carrier.getHealth() < 412.5) {
        	shapeRenderer.setColor(Color.YELLOW);
        } else {
        	shapeRenderer.setColor(Color.RED);
        }
        
        shapeRenderer.rect(healthBar.x, healthBar.y, healthBar.width, healthBar.height);
        */
        
        /*
        shapeRenderer.rect(carrier.getSrcWingL1().x, carrier.getSrcWingL1().y,
        				   carrier.getSrcWingL1().width, carrier.getSrcWingL1().height);
        shapeRenderer.rect(carrier.getSrcWingL2().x, carrier.getSrcWingL2().y,
				   			carrier.getSrcWingL2().width, carrier.getSrcWingL2().height);
        shapeRenderer.rect(carrier.getSrcWingR1().x, carrier.getSrcWingR1().y,
        					carrier.getSrcWingR1().width, carrier.getSrcWingR1().height);
		shapeRenderer.rect(carrier.getSrcWingR2().x, carrier.getSrcWingR2().y,
				   			carrier.getSrcWingR2().width, carrier.getSrcWingR2().height);
		*/
		//shapeRenderer.circle(carrier.getSrcCore().x, carrier.getSrcCore().y, carrier.getSrcCore().radius);
        
		shapeRenderer.setColor(Color.BLUE);
		
		/*
		for(BulletEnemy bLW1 : this.cBStrWingL1) {
			//shapeRenderer.setColor(Color.BLUE);
    		shapeRenderer.circle(bLW1.getX(), bLW1.getY(), 10f);
		}
		
		for(BulletEnemy bLW2 : this.cBStrWingL2) {
			//shapeRenderer.setColor(Color.BLUE);
    		shapeRenderer.circle(bLW2.getX(), bLW2.getY(), 10f);
		}
		
		for(BulletEnemy bRW1 : this.cBStrWingR1) {
			//shapeRenderer.setColor(Color.BLUE);
    		shapeRenderer.circle(bRW1.getX(), bRW1.getY(), 10f);
		}
		
		for(BulletEnemy bRW2 : this.cBStrWingR2) {
			//shapeRenderer.setColor(Color.BLUE);
    		shapeRenderer.circle(bRW2.getX(), bRW2.getY(), 10f);
		}
		*/
		

		if(!carrier.getAlive()) {
			shapeRenderer.setColor(Color.WHITE);
		} else {
			shapeRenderer.setColor(Color.BLUE);
		}
				
		shapeRenderer.setColor(Color.BLUE);
		
		/*
		for(ArrayList<BulletEnemy> sprdCoreList : carrierCoreSprd) {
			for(int i = 0; i < carrier.getBulletsInSprd(); i++) {
				shapeRenderer.circle(sprdCoreList.get(i).getX(), 
									 sprdCoreList.get(i).getY(),
									 sprdCoreList.get(i).getWidth());
			}
		}
        
		
		for(ArrayList<BulletEnemy> sprdCoreList : carrierWingLSprd) {
			for(int i = 0; i < carrier.getBulletsInSprd(); i++) {
				shapeRenderer.circle(sprdCoreList.get(i).getX(), 
									 sprdCoreList.get(i).getY(),
									 sprdCoreList.get(i).getWidth());
			}
		}
		
		for(ArrayList<BulletEnemy> sprdCoreList : carrierWingRSprd) {
			for(int i = 0; i < carrier.getBulletsInSprd(); i++) {
				shapeRenderer.circle(sprdCoreList.get(i).getX(), 
									 sprdCoreList.get(i).getY(),
									 sprdCoreList.get(i).getWidth());
			}
		}*/
		
		
       /* shapeRenderer.rect(carrier.getPOCHead().x, carrier.getPOCHead().y, 
        				   carrier.getPOCHead().width, carrier.getPOCHead().height);
        shapeRenderer.rect(carrier.getPOCWings().x, carrier.getPOCWings().y, 
        		           carrier.getPOCWings().width, carrier.getPOCWings().height);
        shapeRenderer.rect(carrier.getPOCBody().x, carrier.getPOCBody().y, 
		           carrier.getPOCBody().width, carrier.getPOCBody().height);
        
        shapeRenderer.rect(player.getCollisionRect().x, player.getCollisionRect().y,
        		player.getCollisionRect().width, player.getCollisionRect().height);
        */
       
        // End ShapeRenderer
        shapeRenderer.end();
		
	}
    
    public String toString(float f) {
    	return String.format("%s", f);
    }
    
    
    public OrthographicCamera getCam() {
    	return cam;
    }

}
