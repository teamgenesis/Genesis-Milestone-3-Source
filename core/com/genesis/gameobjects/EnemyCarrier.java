package com.genesis.gameobjects;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.genesis.globals.Globals;

public class EnemyCarrier extends Enemy {

	//------------------//
	// Class Attributes //
	//------------------//
	
	// inventory of bullets
	private Queue<ArrayList<BulletEnemy>> bSpreadCore = new LinkedList<ArrayList<BulletEnemy>>();
	private Queue<ArrayList<BulletEnemy>> bSpreadWingL = new LinkedList<ArrayList<BulletEnemy>>();
	private Queue<ArrayList<BulletEnemy>> bSpreadWingR = new LinkedList<ArrayList<BulletEnemy>>();
	
	private ArrayList<BulletEnemy> bStrWingL1 = new ArrayList<BulletEnemy>();
	private ArrayList<BulletEnemy> bStrWingL2 = new ArrayList<BulletEnemy>();
	private ArrayList<BulletEnemy> bStrWingR1 = new ArrayList<BulletEnemy>();
	private ArrayList<BulletEnemy> bStrWingR2 = new ArrayList<BulletEnemy>();
	
	// object characteristics
	private static final float MVNT_SPD_SLOW = 2000;
	private static final float MVNT_SPD_RETREAT = 4000;
	private static final float MVNT_SPD_STRAFE = 1500;
	private static final float MVNT_SPD_RUSH = 10000;
	private static final int WING_RATE_OF_FIRE = 20;
	private static final int CORE_RATE_OF_FIRE = 85; // greater the value, slower rate of fire
	
	private static final int MAX_HEALTH = 750;
	
	// object dimensions
	private static final int WIDTH = 200;
	private static final int HEIGHT = 279;
	
	private static final int CENTRE_X = 100;
	private static final int CENTRE_Y = 134;
	
	// controls rate of fire
	private int timerStrWing = 0;
	private int timerSprdCore = 0;
	
	// controls carrier charge timing
	private int rushTimer = 0;
	
	// determines if carrier has arrived at a specific point on map.
	
	// True to go left, false to go right
	private boolean sideMvntDirTrigger = false;
	
	// Determines if carrier has reached starting position.
	private boolean reachedSP = false;
	
	// Determines if carrier has attempted charge at player
	private boolean chargeAttempted = false;
	
	private boolean waitingForCharge = false;
	
	private boolean verticalReturnDone = false;
	private boolean horizontalReturnDone = false;
	
	private boolean reachedP3Pos = false;
	
	// points of collision
	private Rectangle pocHead;
	private Rectangle pocWings;
	
	private Rectangle pocBody;
	
	// bullet shot source
	private Rectangle srcWingL1;
	private Rectangle srcWingL2;
	
	private Rectangle srcWingR1;
	private Rectangle srcWingR2;
	
	private Circle srcCore;
	
	private Circle srcSprdWingL;
	private Circle srcSprdWingR;
	
	// private Rectangle healthBar;
	
	// number of bullets in a spread
	private static final int numBulletsInSprd = 30;
	
	//-------------//
	// Constructor //
	//-------------//

	public EnemyCarrier(float x, float y) {
		super(x, y, WIDTH, HEIGHT, MAX_HEALTH);
		pocHead = new Rectangle();
		pocWings = new Rectangle();
		pocBody = new Rectangle();
		srcWingL1 = new Rectangle();
		srcWingL2 = new Rectangle();
		srcWingR1 = new Rectangle();
		srcWingR2 = new Rectangle();
		srcCore = new Circle();
		srcSprdWingL = new Circle();
		srcSprdWingR = new Circle();
		// healthBar = new Rectangle();
	}
	
	//-------------------//
	// Object Processing //
	//-------------------//
	
	public void executePhase1(float delta, Player player) {
		// fire burst (straight) shots from wings
		fireStrWings(delta, player);
	
		hoverMotion(delta);
	}
	
	public void executePhase2(float delta, Player player) {
		if(this.bStrWingL1().isEmpty() 
				&& this.bStrWingL2.isEmpty()
				&& this.bStrWingL2.isEmpty()
				&& this.bStrWingR2.isEmpty()) {
			
			if(waitingForCharge && bSpreadCore.isEmpty()) {
				rushForward(delta);
			} else if(waitingForCharge && !bSpreadCore.isEmpty()){
				updateSprd(delta, bSpreadCore, player);
				hoverMotion(delta);
			}
			
			if(!chargeAttempted && rushTimer % 205000 == 0) {
				if(waitingForCharge == false) {
					waitingForCharge = true;
				}
			} else if (chargeAttempted) {
				waitingForCharge = false;
				retreat(delta);
				fireSprdCore(delta, player);
			} else {
				hoverMotion(delta);
				rushTimer++;
				fireSprdCore(delta, player);		
			}
		} else {
			updateStrWings(delta, player);
			hoverMotion(delta);
		}
		
	}
	
	public void executePhase3(float delta, Player player) {	
		fireSprdCore(delta, player);
		
		if(!verticalReturnDone) {
			returnCenterVertical(delta);
			returnCenterHorizontal(delta);
		} else {
			
			hoverMotion(delta);
			fireSprdWings(delta, player);
		}
		
		
		
		
		/*
		if(!verticalReturnDone) {
			returnCenterVertical(delta);
			returnCenterHorizontal(delta);
		} else {
			if(!reachedP3Pos) {
				reverseIntoPos(delta);
			} else {
				hoverMotion(delta);
				fireSprdWings(delta, player);
			}
		}
		*/
	}
	
	public boolean collide(Player player) {
		return(Intersector.overlaps(player.getCollisionRect(), this.getPOCBody()) ||
				Intersector.overlaps(player.getCollisionRect(), this.getPOCWings())||
				Intersector.overlaps(player.getCollisionRect(), this.getPOCHead()));
				
	}

	@Override
	public void update(float delta, Player player) {
		// refCircle.set(this.getX() + this.getCentreX(), this.getY() + this.getCentreY(), 10f);
		// cannon1.set(this.getX() + 40, this.getY() + 260, 16, 16);
		// cannon2.set(this.getX() + 168, this.getY() + 260, 16, 16);
	}
	
	public void updateReady(float delta) {
		if(!reachedSP) {
			if(this.getY() + this.getCentreY() + 125 <= Globals.midPointY) {
				this.setPosition(0, this.getSpeedSlow() * delta, delta);
			} else {
				reachedSP = true;
			}
		}
	}

	public void updateRunning(float delta, Player player) {
		// generate point of collision.
		pocHead.set(this.getX() + this.getCentreX() - 30, this.getY() + 220, 60, 60);
		pocWings.set(this.getX(), this.getY() + 160, 200, 80);
		pocBody.set(this.getX() + 50, this.getY(), 105, 200);
		
		srcWingL1.set(this.getX() + 20, this.getY() + 215, 20, 20);
		srcWingL2.set(this.getX() + 50, this.getY() + 220, 20, 20);
		
		srcWingR1.set(this.getX() + 160, this.getY() + 215, 20, 20);
		srcWingR2.set(this.getX() + 130, this.getY() + 220, 20, 20);
		
		srcCore.set(this.getX() + this.getCentreX(), this.getY() + this.getCentreY(), 10f);
		
		srcSprdWingL.set(this.getX() + 30, this.getY() + 200, 10f);
		srcSprdWingR.set(this.getX() + 140, this.getY() + 200, 10f);
		
		// healthBar.set(50, 25, ((float)this.getHealth() / this.getMaxHealth()) * 500, 25);
		
		if(this.collide(player)) {
			player.die();
		}
		
		
		// Phase 1: health at 100 ~ 75 %
		if(this.getHealth() <= this.getMaxHealth()
		   && this.getHealth() > this.getMaxHealth() * 0.75) {
			
			executePhase1(delta, player);
		
		// Phase 2: health at 75 ~ 45 %
		} else if(this.getHealth() <= this.getMaxHealth() * 0.75
				&& this.getHealth() > this.getMaxHealth() * 0.45) {
		
			executePhase2(delta, player);
			
		// Phase 3: health at 45 ~ 0 %
		} else if(this.getHealth() <= this.getMaxHealth() * 0.45
				&& this.getHealth() > 0){
		
			executePhase3(delta, player);
			
		// Phase 4: health at 0%
		} else {
			this.die();
		}	
	}
	
	
	//------------------//
	// Firing Mechanism //
	//------------------//
	 
	public void loadStrWings() {
			if(timerStrWing % WING_RATE_OF_FIRE == 0) {
				// create new bullet, set bullet position to player center mass
				BulletEnemy bulletWL1 = new BulletEnemy(this.getSrcWingL1().x, this.getSrcWingL1().y,
													 7, 7, 15000);
				BulletEnemy bulletWL2 = new BulletEnemy(this.getSrcWingL2().x, this.getSrcWingL2().y,
						 7, 7, 15000);
				BulletEnemy bulletWR1 = new BulletEnemy(this.getSrcWingR1().x, this.getSrcWingR1().y,
						 7, 7, 15000);
				BulletEnemy bulletWR2 = new BulletEnemy(this.getSrcWingR2().x, this.getSrcWingR2().y,
						 7, 7, 15000);
				
				// add bullet to inventory
				bStrWingL1.add(bulletWL1);
				bStrWingL2.add(bulletWL2);
				bStrWingR1.add(bulletWR1);
				bStrWingR2.add(bulletWR2);
			}
		
	}
	
	public void updateStrWings(float delta, Player player) {
		for(int i = 0; i < bStrWingL1.size(); i++) {
			if(bStrWingL1.get(i).checkOutOfScreen()) {
				// destroy bullet if it exceeds screen boundaries
				bStrWingL1.remove(i);
			} else if (bStrWingL1.get(i).collides(player)) {
				player.die();
				bStrWingL1.remove(i);
			} else {
				bStrWingL1.get(i).update(delta);
			}
		}
		
		for(int j = 0; j < bStrWingL2.size(); j++) {
			if(bStrWingL2.get(j).checkOutOfScreen()) {
				// destroy bullet if it exceeds screen boundaries
				bStrWingL2.remove(j);
			} else if (bStrWingL2.get(j).collides(player)) {
				player.die();
				bStrWingL2.remove(j);
			} else {
				bStrWingL2.get(j).update(delta);
			}
		}
		
		for(int k = 0; k < bStrWingR1.size(); k++) {
			if(bStrWingR1.get(k).checkOutOfScreen()) {
				// destroy bullet if it exceeds screen boundaries
				bStrWingR1.remove(k);
			} else if (bStrWingR1.get(k).collides(player)) {
				player.die();
				bStrWingR1.remove(k);
			} else {
				bStrWingR1.get(k).update(delta);
			}
		}
		
		for(int m = 0; m < bStrWingR2.size(); m++) {
			if(bStrWingR2.get(m).checkOutOfScreen()) {
				// destroy bullet if it exceeds screen boundaries
				bStrWingR2.remove(m);
			} else if (bStrWingR2.get(m).collides(player)) {
				player.die();
				bStrWingR2.remove(m);
			} else {
				bStrWingR2.get(m).update(delta);
			}
		}
	}
	
	public void fireStrWings(float delta, Player player) {
		loadStrWings();
		
		updateStrWings(delta, player);
		
		timerStrWing++;
	}
	
	public void loadSprdShot(Queue<ArrayList<BulletEnemy>> sprdQueue) {
		ArrayList<BulletEnemy> sprdShotList = new ArrayList<BulletEnemy>();
		
		for(int i = 0; i < numBulletsInSprd; i++) {
			if(sprdQueue.equals(bSpreadCore)) {
				BulletEnemy sprdShot = new BulletEnemy(this.getSrcCore().x, this.getSrcCore().y, 
						   10, 10, 
						   10000);
				sprdShotList.add(sprdShot);
			} else if(sprdQueue.equals(bSpreadWingL)) {
				BulletEnemy sprdShot = new BulletEnemy(this.getSrcSprdWingL().x, this.getSrcSprdWingL().y, 
						   (int)this.getSrcSprdWingL().radius, (int)this.getSrcSprdWingL().radius, 
						   10000);
				sprdShotList.add(sprdShot);
			} else if(sprdQueue.equals(bSpreadWingR)) {
				BulletEnemy sprdShot = new BulletEnemy(this.getSrcSprdWingR().x, this.getSrcSprdWingR().y, 
						   (int)this.getSrcSprdWingR().radius, (int)this.getSrcSprdWingR().radius, 
						   10000);
				sprdShotList.add(sprdShot);
			}
		}
		
		sprdQueue.offer(sprdShotList);
	}
	
	public void fireSprdCore(float delta, Player player) {
		if(timerSprdCore % CORE_RATE_OF_FIRE == 0) {
			loadSprdShot(bSpreadCore);
		}
	
		updateSprd(delta, bSpreadCore, player);
				
		timerSprdCore++;
	}
		
	public void updateCorePos(BulletEnemy bullet, float delta, int index) {

		bullet.getCollisionCircle().set(bullet.getX(), bullet.getY(), (float)bullet.getWidth());
		
		bullet.setPosition((float)(bullet.getSpeed() * delta * Math.cos(Math.toRadians(4.5 + (12 * index)))), 
						   (float)(bullet.getSpeed() * delta * Math.sin(Math.toRadians(4.5 + (12 * index)))), 
						   delta);
	}
	
	public void updateSprd(float delta, Queue<ArrayList<BulletEnemy>> sprdQueue, Player player) {
		if(!sprdQueue.isEmpty()) {
					
			if(checkSprdOOS(sprdQueue.peek())) {
				sprdQueue.poll();
			}
			
			for(ArrayList<BulletEnemy> sprdList : sprdQueue) {
				if(SprdCollisionChk(sprdList, player)) {
					player.die();
				} else {
					for(BulletEnemy bullet : sprdList) {
						updateCorePos(bullet, delta, sprdList.indexOf(bullet));
					}		
				}
			}
		}
		
	}
	
	public boolean checkSprdOOS(ArrayList<BulletEnemy> sprdShotList) {
		for(BulletEnemy bullet : sprdShotList) {
			if(!bullet.checkOutOfScreen()) {
				return false;
			}
		}
		
		return true;
	}
	
	public void loadSprdWing(int wing) {
		if(wing == 1) {
			loadSprdShot(bSpreadWingL);
		} else if (wing == 2) {
			loadSprdShot(bSpreadWingR);
		}
	}
	
	public void fireSprdWings(float delta, Player player) {
		if(timerSprdCore % CORE_RATE_OF_FIRE == 0) {
			loadSprdWing(1);
			loadSprdWing(2);
		}
		
		updateSprd(delta, bSpreadWingL, player);
		
		updateSprd(delta, bSpreadWingR, player);
		
		timerSprdCore++;	
	}
	
	public boolean SprdCollisionChk(ArrayList<BulletEnemy> bulletList, Player player) {
		for(BulletEnemy bullet : bulletList) {
			if(bullet.collides(player)) {
				return true;
			} 
		}
		
		return false;
	}
	
	//-----------------//
	// Object Movement //
	//-----------------//
	
	public void floatLeft(float delta) {
		this.setPosition(delta * this.getSpeedStrafe() * -1, 0, delta);
		if(this.getX() + this.getCentreX() <= 100) {
			sideMvntDirTrigger = false;
		}
	}
	
	public void floatRight(float delta) {
		this.setPosition(delta * this.getSpeedStrafe(), 0, delta);
		if(this.getX() + this.getCentreX()>= 500) {
			sideMvntDirTrigger = true;
		}
	}
	
	public void hoverMotion(float delta) {
		if(sideMvntDirTrigger == true) {
			floatLeft(delta);
		} else {
			floatRight(delta);
		}
	}
	
	public void rushForward(float delta) {
		this.setPosition(0,  delta * this.getSpeedRush(), delta);
		if(this.getY() + this.getCentreY() >= 600) {
			chargeAttempted = true;
		}
	}
	
	public void retreat(float delta) {
		this.setPosition(0, delta * MVNT_SPD_RETREAT * -1, delta);
		if(this.getY() + this.getCentreY() <= 200) {
			chargeAttempted = false;
			waitingForCharge = false;
		}
	}
	
	public void returnCenterHorizontal(float delta) {
		if(this.getX() + this.getCentreX() < Globals.midPointX) {
			this.setPosition(delta * this.getSpeedSlow(), 0, delta);
		} else {
			this.setPosition(delta * this.getSpeedSlow() * -1, 0, delta);
		}
		
		if(this.getX() + this.getCentreX() > 295 && this.getX() + this.getCentreX() < 305) {
			horizontalReturnDone = true;
		}
	}
	
	public void returnCenterVertical(float delta) {
		if(this.getY() + this.getCentreY() < Globals.midPointY) {
			this.setPosition(0, delta * this.getSpeedSlow(), delta);
		} else {
			this.setPosition(0, delta * this.getSpeedSlow() * -1, delta);
		}
		
		if(this.getY() + this.getCentreY() > 290 && this.getY() + this.getCentreY() < 310) {
			verticalReturnDone = true;
		}
	}
	
	public void returnCenter(float delta) {
		if(!horizontalReturnDone) {
			returnCenterHorizontal(delta);
		} else {
			returnCenterVertical(delta);
		}
	}
	
	public boolean reachedP3Pos() {
		return this.getY() < Globals.midPointY - 200;
	}
	
	public void reverseIntoPos(float delta) {
		if(!reachedP3Pos()) {
			this.setPosition(0, delta * this.getSpeedSlow() * -1, delta);
		} else {
			reachedP3Pos = true;
		}
		
	}
	
	//---------------------//
	// Attribute Accessors //
	//---------------------//

	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public int getCentreX() {
		return CENTRE_X;
	}
	
	public int getCentreY() {
		return CENTRE_Y;
	}
	
	public float getSpeedSlow() {
		return MVNT_SPD_SLOW;
	}
	
	public float getSpeedStrafe() {
		return MVNT_SPD_STRAFE;
	}
	
	public float getSpeedRush() {
		return MVNT_SPD_RUSH;
	}
	
	public Queue bSpreadCore() {
		return bSpreadCore;
	}
	
	public Queue bSpreadWingL() {
		return bSpreadWingL;
	}
	
	public Queue bSpreadWingR() {
		return bSpreadWingR;
	}
	
	public ArrayList bStrWingL1() {
		return bStrWingL1;
	}
	
	public ArrayList bStrWingL2() {
		return bStrWingL2;
	}
	
	public ArrayList bStrWingR1() {
		return bStrWingR1;
	}
	
	public ArrayList bStrWingR2() {
		return bStrWingR2;
	}
		
	public int getMaxHealth() {
		return MAX_HEALTH;
	}

	public boolean reachedSP() {
		return reachedSP;
	}
	
	public Rectangle getPOCHead() {
		return pocHead;
	}
	
	public Rectangle getPOCWings() {
		return pocWings;
	}
	
	public Rectangle getPOCBody() {
		return pocBody;
	}
	
	public Rectangle getSrcWingL1() {
		return srcWingL1;
	}
	
	public Rectangle getSrcWingL2() {
		return srcWingL2;
	}
	
	public Rectangle getSrcWingR1() {
		return srcWingR1;
	}
	
	public Rectangle getSrcWingR2() {
		return srcWingR2;
	}
	
	public Circle getSrcCore() {
		return srcCore;
	}
	
	private Circle getSrcSprdWingL() {
		return srcSprdWingL;
	}
	
	private Circle getSrcSprdWingR() {
		return srcSprdWingR;
	}
	
	/*
	public Rectangle getHealthBar() {
		return healthBar;
	}*/
	
	public int getBulletsInSprd() {
		return numBulletsInSprd;
	}
}
