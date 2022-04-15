package DOWNFALL;
//The cube controlled by the player in level 2

//Depended upon the Key_Input class for movement and other abilities 

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Player extends Game_Object {

	Random r = new Random();
	Game_Handle handler;
	Level2 level;
	private int lastVelX, lastVelY, prevX, prevY, upX, upY, downX, downY, leftX, leftY, rightX, rightY, logic_N, n,
			localTimer, cLogic;
	private boolean flash, foundWalls, upHit, downHit, leftHit, rightHit, imortal, spawnedHUD, changeRoom, able_to_move;

	public Player(int x, int y, ID_System id, Game_Handle handler, Level2 level) {
		super(x, y, id);
		this.handler = handler;
		this.level = level;

		flash = true;
		cLogic = 255;

		lastVelX = 0;
		lastVelY = 0;
		prevX = 0;
		prevY = 0;

		upX = x;
		upY = y;
		downX = x;
		downY = y;
		leftX = x;
		leftY = y;
		rightX = x;
		rightY = y;

		localTimer = 0;

		spawnedHUD = false;

		// logic that essentially enables connection with key board
		able_to_move = true;

		// testing ONLY
		// imortal = true;

		// Note: issues with [RIGHT && UP], clipping
		// alters the x & y values for the gaps/hit boxes

		n = 4;

		// assumes walls == static
		foundWalls = false;

		// prevents errors when changing the waveNum
		changeRoom = false;

		System.out.println("Player SPAWNED\nPlayer_X: " + x + "\nPlayer_Y: " + y);
	}

	// Special Case: Level 2: after animation
	public Player(int x, int y, ID_System id, Game_Handle handler, Level2 level, boolean flash) {
		super(x, y, id);
		this.handler = handler;
		this.level = level;

		this.flash = flash;
		cLogic = 255;

		lastVelX = 0;
		lastVelY = 0;
		prevX = 0;
		prevY = 0;

		upX = x;
		upY = y;
		downX = x;
		downY = y;
		leftX = x;
		leftY = y;
		rightX = x;
		rightY = y;

		localTimer = 0;

		spawnedHUD = false;

		able_to_move = false;

		// testing ONLY
		// imortal = true;

		// Note: issues with [RIGHT && UP], clipping
		// alters the x & y values for the gaps/hit boxes

		n = 4;

		// assumes walls == static
		foundWalls = false;

		// prevents errors when changing the waveNum
		changeRoom = false;

		System.out.println("Player SPAWNED\nPlayer_X: " + x + "\nPlayer_Y: " + y);
	}

	public Player(int x, int y, ID_System id, Game_Handle handler, Level2 level, int health) {
		super(x, y, id);
		this.handler = handler;
		this.level = level;
		lastVelX = 0;
		lastVelY = 0;
		prevX = 0;
		prevY = 0;

		upX = x;
		upY = y;
		downX = x;
		downY = y;
		leftX = x;
		leftY = y;
		rightX = x;
		rightY = y;

		// resets the player's health to the value it was in the last room
		setHealth(health);

		localTimer = 0;

		able_to_move = true;
		// testing ONLY
		// imortal = true;

		// Note: issues with [RIGHT && UP], clipping
		// alters the x & y values for the gaps/hit boxes

		n = 4;

		// assumes walls == static
		foundWalls = false;

		System.out.println("Player SPAWNED\nPlayer_X: " + x + "\nPlayer_Y: " + y);
	}

	public void non_level_Logic() {
		// Gun Logic
		// may have issues
		if (getGunReady() == false) {
			localTimer++;
		}

		if (localTimer > getCombatFrequency()) {
			setGunReady(true);
			localTimer = 0;
		}

		// Will find ALL walls

		if (foundWalls != true) {
			logic_N = getWalls();
			foundWalls = true;
		}

		// Initialize local variables
		boolean[] upHitArray = new boolean[logic_N];
		boolean[] downHitArray = new boolean[logic_N];
		boolean[] leftHitArray = new boolean[logic_N];
		boolean[] rightHitArray = new boolean[logic_N];

		// "clamping"
		setX((int) Game.clamp((int) getX(), 0, Game.WIDTH - 39));
		setY((int) Game.clamp((int) getY(), 0, Game.HEIGHT - 64));

		// move
		// could take in logic.....
		resetHits();
		gapMove(upHitArray, downHitArray, leftHitArray, rightHitArray);
		// logic
		gapHit(upHitArray, downHitArray, leftHitArray, rightHitArray);
		playerMove();

		// Health logic
		if (getHealth() <= 0 && imortal == false) {
			handler.removeObject(this);
		}
	}

	public void level_Logic() {
		for (int i = 0; i < handler.object.size(); i++) {
			Game_Object tempObject = handler.object.get(i);
			if (tempObject.getId() == ID_System.Door_Up) {
				if (getBounds().intersects(tempObject.getBounds()) && level.getDoorActive() == true) {
					// actions

					// spawn new player
					// since door == up; spawn player down
					// setY(Game.HEIGHT - 95);

					// set the next waveNum
					if (changeRoom == false) {
						level.setwaveNum(level.getwaveNum() + 1);
						changeRoom = true;
					}

					// second final action
					int lX = getX();
					// int lY = getY();
					int hp = getHealth();
					level.killALL();

					// final action
					level.request_player_spawn(lX, (Game.HEIGHT - 99), hp);
				} // end of waveNum1
			}
			if (tempObject.getId() == ID_System.Door_Down) {
				if (getBounds().intersects(tempObject.getBounds()) && level.getDoorActive() == true) {
					// actions

					// spawn new player
					// since door ==down; spawn player up
					// setY(45);

					// set the next waveNum
					if (changeRoom == false) {
						level.setwaveNum(level.getwaveNum() - 1);
						changeRoom = true;
					}

					// second final action
					int lX = getX();
					int lY = getY();
					int hp = getHealth();
					level.killALL();

					// final action
					level.request_player_spawn(lX, (34), hp);

				} // end of waveNum2
			}
		}
	}

	public void hud() {
		// will spawn the HUD
		if (spawnedHUD == false) {
			handler.addObject(new HUD(0, 0, ID_System.HUD, handler, this));
			spawnedHUD = true;

			// for special case only...
			able_to_move = true;
		}
	}

	public void tick() {
		// hud logic
		hud();

		// door animation logic
		if (flash == true) {
			cLogic = cLogic - 10;
			if (cLogic <= 1) {
				flash = false;
				cLogic = 255;
			}
		}
		level_Logic();
		non_level_Logic();

	}

	public void render(Graphics g) {
		if (flash) {
			g.setColor(new Color(cLogic, cLogic, cLogic));
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		}

		if (Game.getTESTING_MODE() == true) {
			// logic
			g.setColor(Color.green);
			if (upHit) {
				g.setColor(Color.red);
			}
			g.drawRect(upX, upY, 32, 32);
			g.setColor(Color.green);

			if (downHit) {
				g.setColor(Color.red);
			}
			g.drawRect(downX, downY, 32, 32);
			g.setColor(Color.green);

			if (leftHit) {
				g.setColor(Color.red);
			}
			g.drawRect(leftX, leftY, 32, 32);
			g.setColor(Color.green);

			if (rightHit) {
				g.setColor(Color.red);
			}
			g.drawRect(rightX, rightY, 32, 32);
			g.setColor(Color.green);
		}
		// player
		g.setColor(Color.white);
		g.fillRect(getX(), getY(), 32, 32);

		g.setColor(Color.GREEN);
		g.drawRect(getX(), getY(), 32, 32);

	}

	public int getWalls() {
		int count = 0;
		for (int i = 0; i < handler.object.size(); i++) {
			Game_Object tempObject = handler.object.get(i);
			if (tempObject.getId() == ID_System.Wall || tempObject.getId() == ID_System.WallS) {
				count++;
			}
		}
		return count;
	}

	private void gapMove(boolean upHitArray[], boolean downHitArray[], boolean leftHitArray[],
			boolean rightHitArray[]) {
		for (int i = 0; i < handler.object.size(); i++) {
			Game_Object tempObject = handler.object.get(i);
			if (tempObject.getId() == ID_System.Wall || tempObject.getId() == ID_System.WallS) {
				if (getBoundsUp().intersects(tempObject.getBounds())) {
					upHitArray[i] = true;
				} else {
					upHitArray[i] = false;
				}
				if (getBoundsDown().intersects(tempObject.getBounds())) {
					downHitArray[i] = true;
				} else {
					downHitArray[i] = false;
				}
				if (getBoundsLeft().intersects(tempObject.getBounds())) {
					leftHitArray[i] = true;
				} else {
					leftHitArray[i] = false;
				}
				if (getBoundsRight().intersects(tempObject.getBounds())) {
					rightHitArray[i] = true;
				} else {
					rightHitArray[i] = false;
				}
			}
		}

		// movement, only if the player is ready...
		if (able_to_move == true) {
			upX = (getX() + velX);
			upY = ((getY() - n) + velY);
			downX = (getX() + velX);
			downY = ((getY() + n) + velY);
			leftX = ((getX() - n) + velX);
			leftY = (getY() + velY);
			rightX = ((getX() + n) + velX);
			rightY = (getY() + velY);
		}
	}

	private void gapHit(boolean upHitArray[], boolean downHitArray[], boolean leftHitArray[], boolean rightHitArray[]) {
		for (int l = 0; l < upHitArray.length; l++) {
			if (upHitArray[l] == true) {
				upHit = true;
			}
			if (downHitArray[l] == true) {
				downHit = true;
			}
			if (leftHitArray[l] == true) {
				leftHit = true;
			}
			if (rightHitArray[l] == true) {
				rightHit = true;
			}
		}
	}

	public void resetHits() {
		upHit = false;
		downHit = false;
		rightHit = false;
		leftHit = false;
	}

	private void playerMove() {
		if (!(upHit == true || downHit == true)) {
			prevY = getY();
			setY(getY() + velY);
			lastVelY = velY;

		}

		if (!(leftHit == true || rightHit == true)) {
			prevX = getX();
			setX(getX() + velX);
			lastVelX = velX;
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), 32, 32);
	}

	public Rectangle getPastBounds() {
		return new Rectangle(prevX, prevY, 32, 32);
	}

	public Rectangle getBoundsUp() {
		return new Rectangle(upX, upY, 32, 32);
	}

	public Rectangle getBoundsDown() {
		return new Rectangle(downX, downY, 32, 32);
	}

	public Rectangle getBoundsLeft() {
		return new Rectangle(leftX, leftY, 32, 32);
	}

	public Rectangle getBoundsRight() {
		return new Rectangle(rightX, rightY, 32, 32);
	}

	public void setLastVelX(int lastVelX) {
		if (lastVelX != 0) {
			this.lastVelX = lastVelX;
		}
	}

	public void setLastVelY(int lastVelY) {
		if (lastVelY != 0) {
			this.lastVelY = lastVelY;
		}
	}

	public int getLastVelX() {
		return lastVelX;
	}

	public int getLastVelY() {
		return lastVelY;
	}
}