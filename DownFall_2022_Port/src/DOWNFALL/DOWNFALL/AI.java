package DOWNFALL;
//This class is essentially the brain of a hostile characters within the second level
//This class utilizes different hit boxes (these can be viewed by pressing the L key) to perform specific actions at specific times, depending if the player intersects them

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class AI extends Game_Object {

	private Game_Handle handler;

	Random rand = new Random();
	private int prevX, prevY, reactionSpeed, upX, upY, logic_upX, logic_upY, downX, downY, logic_downX, logic_downY,
			leftX, leftY, logic_leftX, logic_leftY, rightX, rightY, logic_rightX, logic_rightY, localTimer, upX_move,
			upY_move, downX_move, downY_move, leftX_move, leftY_move, rightX_move, rightY_move, lastLogic_upX,
			lastLogic_upY, lastLogic_downX, lastLogic_downY, lastLogic_leftX, lastLogic_leftY, lastLogic_rightX,
			lastLogic_rightY;
	// Note: |DIRECTION|Scan == if the player is within the box
	// Note: logic_|NAME| pertains to the "logic projectile"
	private boolean movingLEFT, movingRIGHT, movingUP, movingDOWN, upScan, upScanTRUE, logicUP, downScan, downScanTRUE,
			logicDOWN, leftScan, leftScanTRUE, logicLEFT, rightScan, rightScanTRUE, logicRIGHT, upScan_WALL,
			downScan_WALL, leftScan_WALL, rightScan_WALL;
	private Direction_System ds;

	public AI(int x, int y, ID_System id, Game_Handle handler) {
		super(x, y, id);
		this.handler = handler;

		// NEW STAT: Reaction Speed
		// modifies the speed of the "scan projectile"
		reactionSpeed = 45;

		localTimer = 0;
		// the initial x and y positions of the up scanner
		upX = (getX());
		upY = ((getY() - getAwareness()));

		// the initial x and y positions of the down scanner
		downX = (getX());
		downY = ((getY() + 32));

		// the initial x and y positions of the left scanner
		leftX = ((getX() - getAwareness()));
		leftY = (getY());

		// the initial x and y positions of the right scanner
		rightX = ((getX() + 32));
		rightY = (getY());

		// Scan logic
		upScan = false;
		upScanTRUE = false;
		logicUP = false;

		downScan = false;
		downScanTRUE = false;
		logicDOWN = false;

		leftScan = false;
		leftScanTRUE = false;
		logicLEFT = false;

		rightScan = false;
		rightScanTRUE = false;
		logicRIGHT = false;

		// alt logic
		upScan_WALL = false;
		downScan_WALL = false;
		leftScan_WALL = false;
		rightScan_WALL = false;

		prevX = x;
		prevY = y;

		// Prevoius logic
		lastLogic_upX = 0;
		lastLogic_upY = 0;
		lastLogic_downX = 0;
		lastLogic_downY = 0;
		lastLogic_leftX = 0;
		lastLogic_leftY = 0;
		lastLogic_rightX = 0;
		lastLogic_rightY = 0;

		// Movement
		upX_move = x;
		upY_move = y;
		downX_move = x;
		downY_move = y;
		leftX_move = x;
		leftY_move = y;
		rightX_move = x;
		rightY_move = y;

		// modify stats...
		setHealth(20);
		setDamage(5);
	}

	public void tick() {
		// methods that are enable the AI to attack the player
		combat_and_scan_Methods();
		// methods that enable the AI to move
		movementMethods();

		// Health logic
		if (getHealth() <= 0) {
			handler.removeObject(this);
		}
	}

	public void movementMethods() {
		// "clamping"
		setX((int) Game.clamp((int) getX(), 0, Game.WIDTH - 39));
		setY((int) Game.clamp((int) getY(), 0, Game.HEIGHT - 64));

		AI_Move();
	}

	// MOVEMENT
	// Works, but is highly buggy regarding walls, AI will teleport
	/*
	 * public void AI_Move() { //fixes logical error(s) combat_and_scan_Methods();
	 * combat_and_scan_Methods();
	 * 
	 * // player in AI's scan if (upScanTRUE || downScanTRUE || leftScanTRUE ||
	 * rightScanTRUE) { if (upScanTRUE) { // move up y = y - getSpeed(); } if
	 * (downScanTRUE) { // move down y = y + getSpeed(); } if (leftScanTRUE) { //
	 * move left x = x - getSpeed(); } if (rightScanTRUE) { // move right x = x +
	 * getSpeed(); } } //passive else //randomly generate in future { //BOOLEAN
	 * TOGGLING LOGIC inverseX(); inverseY();
	 * 
	 * //AI logic //"Tracking" //"Unfair" //WORKS for (int i = 0; i <
	 * handler.object.size(); i++) { Game_Object tempObject = handler.object.get(i);
	 * if (tempObject.getId() == ID_System.Player_3D) { if (tempObject.getY() > y) {
	 * movingUP = false; movingDOWN = true; } else if (tempObject.getY() < y) {
	 * movingUP = true; movingDOWN = false; } if (tempObject.getX() > x) {
	 * movingLEFT = false; movingRIGHT = true; } else if (tempObject.getX() < x) {
	 * movingLEFT = true; movingRIGHT = false; } } } //solution to logical error(s)
	 * AI_scan(); combat_and_scan_Methods(); AI_scan(); combat_and_scan_Methods();
	 * AI_scan(); combat_and_scan_Methods(); AI_scan(); //end of solution
	 * 
	 * if (movingUP) { upScan_WALL = false; logicMove(Direction_System.Up); if
	 * (upScan_WALL == false) { y = y - getSpeed(); } else {
	 * 
	 * //ISSUES will occur
	 * HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * while ((y + 32) > lastLogic_upY) { y = y - getSpeed(); } } } if (movingDOWN)
	 * { downScan_WALL = false; logicMove(Direction_System.Down); if (downScan_WALL
	 * == false) { y = y + getSpeed(); } else {
	 * 
	 * //ISSUES will occur
	 * HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * while ((y - 32) < lastLogic_downY) { y = y + getSpeed(); } } }
	 * 
	 * if (movingLEFT) { leftScan_WALL = false; logicMove(Direction_System.Left); if
	 * (leftScan_WALL == false) { x = x - getSpeed(); } else {
	 * 
	 * //ISSUES will occur
	 * HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * while ((x + 32) > lastLogic_leftX) { x = x - getSpeed(); } } } if
	 * (movingRIGHT) { rightScan_WALL = false; logicMove(Direction_System.Right); if
	 * (rightScan_WALL == false) { x = x + getSpeed(); } else {
	 * 
	 * //ISSUES will occur
	 * HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * while ((x - 32) < lastLogic_rightX) { x = x + getSpeed(); } } } } }
	 */
	public void AI_Move() {
		// fixes logical error(s)
		combat_and_scan_Methods();
		combat_and_scan_Methods();

		// player in AI's scan
		if (upScanTRUE || downScanTRUE || leftScanTRUE || rightScanTRUE) {
			if (upScanTRUE) {
				// move up
				y = y - getSpeed();
			}
			if (downScanTRUE) {
				// move down
				y = y + getSpeed();
			}
			if (leftScanTRUE) {
				// move left
				x = x - getSpeed();
			}
			if (rightScanTRUE) {
				// move right
				x = x + getSpeed();
			}
		} // passive
		else // randomly generate in future
		{
			// BOOLEAN TOGGLING LOGIC
			inverseX();
			inverseY();

			// AI logic
			// "Tracking"
			// "Unfair"
			// WORKS
			for (int i = 0; i < handler.object.size(); i++) {
				Game_Object tempObject = handler.object.get(i);
				if (tempObject.getId() == ID_System.Player_3D) {
					if (tempObject.getY() > y) {
						movingUP = false;
						movingDOWN = true;
					} else if (tempObject.getY() < y) {
						movingUP = true;
						movingDOWN = false;
					}
					if (tempObject.getX() > x) {
						movingLEFT = false;
						movingRIGHT = true;
					} else if (tempObject.getX() < x) {
						movingLEFT = true;
						movingRIGHT = false;
					}
				}
			}
			// solution to logical error(s)
			AI_scan();
			combat_and_scan_Methods();
			AI_scan();
			combat_and_scan_Methods();
			AI_scan();
			combat_and_scan_Methods();
			AI_scan();
			// end of solution

			// CODE WORKS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			// NOTE: MINOR ISSUES WITH GUN && GUN FREQUENCY
			if (movingUP) {
				int temp = getAwareness();
				setAwareness(32);
				upScan_WALL = false;
				logicMove(Direction_System.Up);
				setAwareness(temp);
				// IF THE WALL WAS NOT FOUND...
				if (upScan_WALL == false) {
					y = y - getSpeed();
				} else {
					// System.out.println("U");
					// action: special check to traverse wall
					scale(Direction_System.Up);
				}
			}
			if (movingDOWN) {
				int temp = getAwareness();
				setAwareness(32);
				downScan_WALL = false;
				logicMove(Direction_System.Down);
				setAwareness(temp);
				if (downScan_WALL == false) {
					y = y + getSpeed();
				} else {
					// System.out.println("D");
					// action: special check to traverse wall
					scale(Direction_System.Down);
				}
			}

			if (movingLEFT) {
				int temp = getAwareness();
				setAwareness(32);
				leftScan_WALL = false;
				logicMove(Direction_System.Left);
				setAwareness(temp);
				if (leftScan_WALL == false) {
					x = x - getSpeed();
				} else {
					// System.out.println("L");
					// action: special check to traverse wall
					scale(Direction_System.Left);
				}
			}
			if (movingRIGHT) {
				int temp = getAwareness();
				setAwareness(32);
				rightScan_WALL = false;
				logicMove(Direction_System.Right);
				setAwareness(temp);
				if (rightScan_WALL == false) {
					x = x + getSpeed();
				} else {
					// System.out.println("R");
					// action: special check to traverse wall
					scale(Direction_System.Right);
				}
			}
		}
	}

	public void scale(Direction_System ds) {
		// Will "scale" the wall, or attempt to
		// ERRORS!

		if (ds == Direction_System.Up) {
			logicMove(ds);

			// lastLogic_upY == woprks for up && down!
			int lastY = lastLogic_downY;

			if (lastY + 32 < y) {
				y = y - getSpeed();
			}
		}
		if (ds == Direction_System.Down) {
			logicMove(ds);

			// int lastY = lastLogic_upY; == LOGICAL ERROR
			int lastY = lastLogic_upY;

			if (lastY - 32 > y) {
				y = y + getSpeed();
			}
		}
		if (ds == Direction_System.Left) {
			logicMove(ds);

			int lastX = lastLogic_rightX;

			if (lastX + 32 < x) {
				x = x - getSpeed();
			}
		}
		if (ds == Direction_System.Right) {
			logicMove(ds);

			int lastX = lastLogic_leftX;

			if (lastX - 32 > x) {
				x = x + getSpeed();
			}
		}
	}

	public void inverseX() {
		// X movement
		if (x == prevX) {
			if (movingLEFT == true) {
				movingLEFT = false;
				movingRIGHT = true;
			} else {
				movingLEFT = true;
				movingRIGHT = false;
			}
		}
		// logic; updates value
		prevX = x;
	}

	public void inverseY() {
		// Y movement
		if (y == prevY) {
			if (movingUP == true) {
				movingUP = false;
				movingDOWN = true;
			} else {
				movingUP = true;
				movingDOWN = false;
			}
		}
		// logic; updates value
		prevY = y;
	}
	// end of FIX

	public void combat_and_scan_Methods() {// start of logic regarding gun cool down
		if (getGunReady() == false) {
			localTimer++;
		}

		if (localTimer > getCombatFrequency()) {
			setGunReady(true);
			localTimer = 0;
		}
		// end of logic regarding gun cool down

		if (logicUP != true) {
			dateLogicXY_up();
		}
		if (logicDOWN != true) {
			dateLogicXY_down();
		}
		if (logicLEFT != true) {
			dateLogicXY_left();
		}
		if (logicRIGHT != true) {
			dateLogicXY_right();
		}
		AI_scan();

		if (logicUP == true) {
			// create/move
			if (getGunReady() == true) {
				logicMove(Direction_System.Up);
			}
		}
		if (logicDOWN == true) {
			// create/move
			if (getGunReady() == true) {
				logicMove(Direction_System.Down);
			}
		}
		if (logicLEFT == true) {
			// create/move
			if (getGunReady() == true) {
				logicMove(Direction_System.Left);
			}
		}
		if (logicRIGHT == true) {
			// create/move
			if (getGunReady() == true) {
				logicMove(Direction_System.Right);
			}
		}
	}

	// moves the "logic projectile"
	public void logicMove(Direction_System direction) {
		if (direction == Direction_System.Up) {
			logic_upY = logic_upY - reactionSpeed;

			// logic: if the projectile is too far away, reset
			if (logic_upY < (y - getAwareness() - 12)) {
				dateLogicXY_up();
			}

			for (int dae = 0; dae < handler.object.size(); dae++) {
				Game_Object tempObject = handler.object.get(dae);
				if (tempObject.getId() == ID_System.Player_3D) {
					if (getBoundsLOGIC_up().intersects(tempObject.getBounds())) {
						upScanTRUE = true;
						AI_fire();

						// New code: purpose is to treat the player as a Wall so the AI will not run
						// inside of the player
						lastLogic_upX = logic_upX;
						lastLogic_upY = logic_upY;
						if (Game.getTESTING_MODE() == true) {
							System.out.println("UP HIT WALL");
							System.out.println("Setting lastLogic_upX &&  lastLogic_upY\n lastLogic_upX = "
									+ lastLogic_upX + "\n lastLogic_upY = " + lastLogic_upY);
						}
						// reset
						upScan_WALL = true;
						dateLogicXY_up();
						// end of New code

					}
				}
				if (tempObject.getId() == ID_System.Wall) {
					if (getBoundsLOGIC_up().intersects(tempObject.getBounds())) {
						// last X & Y positions
						lastLogic_upX = logic_upX;
						lastLogic_upY = logic_upY;
						if (Game.getTESTING_MODE() == true) {
							System.out.println("UP HIT PLAYER");
							System.out.println("Setting lastLogic_upX &&  lastLogic_upY\n lastLogic_upX = "
									+ lastLogic_upX + "\n lastLogic_upY = " + lastLogic_upY);
						}
						// reset
						upScan_WALL = true;
						dateLogicXY_up();
					}
				}
			}
		}
		if (direction == Direction_System.Down) {
			logic_downY = logic_downY + reactionSpeed;

			// logic: if the projectile is too far away, reset
			if (logic_downY > (y + getAwareness() + 32)) {
				dateLogicXY_down();
			}

			for (int dae = 0; dae < handler.object.size(); dae++) {
				Game_Object tempObject = handler.object.get(dae);
				if (tempObject.getId() == ID_System.Player_3D) {
					if (getBoundsLOGIC_down().intersects(tempObject.getBounds())) {
						downScanTRUE = true;
						AI_fire();

						// New code: purpose is to treat the player as a Wall so the AI will not run
						// inside of the player
						lastLogic_downX = logic_downX;
						lastLogic_downY = logic_downY;
						if (Game.getTESTING_MODE() == true) {
							System.out.println("DOWN HIT PLAYER");
							System.out.println("Setting lastLogic_downX &&  lastLogic_downY\n lastLogic_downX = "
									+ lastLogic_downX + "\n lastLogic_downY = " + lastLogic_downY);
						}
						// reset
						downScan_WALL = true;
						dateLogicXY_down();
						// end of New code
					}
				}
				if (tempObject.getId() == ID_System.Wall) {
					if (getBoundsLOGIC_down().intersects(tempObject.getBounds())) {
						// last X & Y positions
						lastLogic_downX = logic_downX;
						lastLogic_downY = logic_downY;
						if (Game.getTESTING_MODE() == true) {
							System.out.println("DOWN HIT WALL");
							System.out.println("Setting lastLogic_downX &&  lastLogic_downY\n lastLogic_downX = "
									+ lastLogic_downX + "\n lastLogic_downY = " + lastLogic_downY);
						}
						// reset
						downScan_WALL = true;
						dateLogicXY_down();
					}
				}
			}
		}

		if (direction == Direction_System.Left) {
			logic_leftX = logic_leftX - reactionSpeed;

			if (logic_leftX < (x - getAwareness() - 12)) {
				dateLogicXY_left();
			}

			for (int dae = 0; dae < handler.object.size(); dae++) {
				Game_Object tempObject = handler.object.get(dae);
				if (tempObject.getId() == ID_System.Player_3D) {
					if (getBoundsLOGIC_left().intersects(tempObject.getBounds())) {
						leftScanTRUE = true;
						AI_fire();

						// New code: purpose is to treat the player as a Wall so the AI will not run
						// inside of the player
						lastLogic_leftX = logic_leftX;
						lastLogic_leftY = logic_leftY;
						if (Game.getTESTING_MODE() == true) {
							System.out.println("LEFT HIT PLAYER");
							System.out.println("Setting lastLogic_leftX &&  lastLogic_leftY\n lastLogic_leftX = "
									+ lastLogic_leftX + "\n lastLogic_leftY = " + lastLogic_leftY);
						}
						// reset
						leftScan_WALL = true;
						dateLogicXY_left();
						// end of New code
					}
				}
				if (tempObject.getId() == ID_System.Wall) {
					if (getBoundsLOGIC_left().intersects(tempObject.getBounds())) {
						// last X & Y positions
						lastLogic_leftX = logic_leftX;
						lastLogic_leftY = logic_leftY;
						if (Game.getTESTING_MODE() == true) {
							System.out.println("LEFT HIT PLAYER");
							System.out.println("Setting lastLogic_leftX &&  lastLogic_leftY\n lastLogic_leftX = "
									+ lastLogic_leftX + "\n lastLogic_leftX = " + lastLogic_leftY);
						}
						// reset
						leftScan_WALL = true;
						dateLogicXY_left();
					}
				}
			}
		}
		if (direction == Direction_System.Right) {
			logic_rightX = logic_rightX + reactionSpeed;

			// logic: if the projectile is too far away, reset
			if (logic_rightX > (x + getAwareness() + 32)) {
				dateLogicXY_right();
			}

			for (int dae = 0; dae < handler.object.size(); dae++) {
				Game_Object tempObject = handler.object.get(dae);
				if (tempObject.getId() == ID_System.Player_3D) {
					if (getBoundsLOGIC_right().intersects(tempObject.getBounds())) {
						rightScanTRUE = true;
						AI_fire();

						// New code: purpose is to treat the player as a Wall so the AI will not run
						// inside of the player
						lastLogic_rightX = logic_rightX;
						lastLogic_rightY = logic_rightY;
						if (Game.getTESTING_MODE() == true) {
							System.out.println("RIGHT HIT PLAYER");
							System.out.println("Setting lastLogic_rightX &&  lastLogic_rightY\n lastLogic_rightX = "
									+ lastLogic_rightX + "\n lastLogic_rightY = " + lastLogic_rightY);
						}
						// reset
						rightScan_WALL = true;
						dateLogicXY_right();
						// end of New code
					}
				}
				if (tempObject.getId() == ID_System.Wall) {
					if (getBoundsLOGIC_right().intersects(tempObject.getBounds())) {
						// last X & Y positions
						lastLogic_rightX = logic_rightX;
						lastLogic_rightY = logic_rightY;
						if (Game.getTESTING_MODE() == true) {
							System.out.println("RIGHT HIT WALL");
							System.out.println("Setting lastLogic_rightX &&  lastLogic_rightY\n lastLogic_rightX = "
									+ lastLogic_rightX + "\n lastLogic_rightY = " + lastLogic_rightY);
						}
						// reset
						rightScan_WALL = true;
						dateLogicXY_right();
					}
				}
			}
		}
	}

	public void AI_fire() {
		if (upScanTRUE) {
			if (getGunReady() == true) {
				// potential issue since "CTRL C'd" downScan data
				handler.addObject(new AI_Projectile((getX() + 10), getY(), ID_System.AI_Projectile, handler,
						Direction_System.Up));
				setGunReady(false);
			}
		}
		if (downScanTRUE) {
			if (getGunReady() == true) {
				// potential issue since "CTRL C'd" downScan data
				handler.addObject(new AI_Projectile((getX() + 10), (getY() + 22), ID_System.AI_Projectile, handler,
						Direction_System.Down));
				setGunReady(false);
			}
		}
		if (leftScanTRUE) {
			if (getGunReady() == true) {
				handler.addObject(new AI_Projectile(getX(), getY() + 10, ID_System.AI_Projectile, handler,
						Direction_System.Left));
				setGunReady(false);
			}
		}
		if (rightScanTRUE) {
			if (getGunReady() == true) {
				// potential issue since "CTRL C'd" downScan data
				handler.addObject(new AI_Projectile((getX() + 22), getY() + 10, ID_System.AI_Projectile, handler,
						Direction_System.Right));
				setGunReady(false);
			}
		}
	}

	public void datePositions() { // the initial x and y positions of the up scanner
		upX = (getX());
		upY = ((getY() - getAwareness()));

		// the initial x and y positions of the down scanner
		downX = (getX());
		downY = ((getY() + 32));

		// the initial x and y positions of the left scanner
		leftX = ((getX() - getAwareness()));
		leftY = (getY());

		// the initial x and y positions of the right scanner
		rightX = ((getX() + 32));
		rightY = (getY());
	}

	public void dateLogicXY_up() {
		datePositions();
		logic_upX = x + 10;
		logic_upY = y - 12;
		upScanTRUE = false;
	}

	public void dateLogicXY_down() {
		datePositions();
		logic_downX = x + 10;
		logic_downY = y + 32;
		downScanTRUE = false;
	}

	public void dateLogicXY_left() {
		datePositions();
		logic_leftX = x - 12;
		logic_leftY = y + 10;
		leftScanTRUE = false;
	}

	public void dateLogicXY_right() {
		datePositions();
		// tweak values
		logic_rightX = x + 32;
		logic_rightY = y + 10;
		rightScanTRUE = false;
	}

	public void render(Graphics g) {
		if (Game.getTESTING_MODE() == true) {
			// Logic == The s
			if (logicUP == true) {
				g.setColor(Color.yellow);
				g.drawRect(logic_upX, logic_upY, 12, 12);
			}
			if (logicDOWN == true) {
				g.setColor(Color.yellow);
				g.drawRect(logic_downX, logic_downY, 12, 12);
			}
			if (logicLEFT == true) {
				g.setColor(Color.yellow);
				g.drawRect(logic_leftX, logic_leftY, 12, 12);
			}
			if (logicRIGHT == true) {
				g.setColor(Color.yellow);
				g.drawRect(logic_rightX, logic_rightY, 12, 12);
			}

			// logic
			/*
			 * g.setColor(Color.green); if (upHit_move) { g.setColor(Color.red); }
			 * g.drawRect(upX_move, upY_move, 32, 32); g.setColor(Color.green);
			 * 
			 * if (downHit_move) { g.setColor(Color.red); } g.drawRect(downX_move,
			 * downY_move, 32, 32); g.setColor(Color.green);
			 * 
			 * if (leftHit_move) { g.setColor(Color.red); } g.drawRect(leftX_move,
			 * leftY_move, 32, 32); g.setColor(Color.green);
			 * 
			 * if (rightHit_move) { g.setColor(Color.red); } g.drawRect(rightX_move,
			 * rightY_move, 32, 32); g.setColor(Color.green);
			 */
			// AI Scanner
			//
			g.setColor(Color.red);
			if (upScan) {
				g.setColor(Color.yellow);
			}
			if (upScanTRUE) {
				g.setColor(Color.green);
			}
			g.drawRect(upX, upY, 32, getAwareness());
			//

			//
			g.setColor(Color.red);
			if (downScan) {
				g.setColor(Color.yellow);
			}
			if (downScanTRUE) {
				g.setColor(Color.green);
			}
			g.drawRect(downX, downY, 32, getAwareness());
			//

			//
			g.setColor(Color.red);
			if (leftScan) {
				g.setColor(Color.yellow);
			}
			if (leftScanTRUE) {
				g.setColor(Color.green);
			}
			g.drawRect(leftX, leftY, getAwareness(), 32);
			//

			//
			g.setColor(Color.red);
			if (rightScan) {
				g.setColor(Color.yellow);
			}
			if (rightScanTRUE) {
				g.setColor(Color.green);
			}
			g.drawRect(rightX, rightY, getAwareness(), 32);
			//
		}

		// AI
		g.setColor(Color.yellow);
		g.fillRect(x, y, 32, 32);
		g.setColor(Color.RED);
		g.drawRect(x, y, 32, 32);
	}

	public void AI_scan() {
		// NOTE: All System.out.println scanType == true when player intersects area
		for (int i = 0; i < handler.object.size(); i++) {
			Game_Object tempObject = handler.object.get(i);
			if (tempObject.getId() == ID_System.Player_3D) {
				if (getBoundsUp().intersects(tempObject.getBounds())) {
					upScan = true;
					Direction_System direction = Direction_System.Up;
					AI_scan2(direction);
				} else {
					dateLogicXY_up();
					upScan = false;
				}
				if (getBoundsDown().intersects(tempObject.getBounds())) {
					downScan = true;
					Direction_System direction = Direction_System.Down;
					AI_scan2(direction);
				} else {
					dateLogicXY_down();
					downScan = false;
				}
				if (getBoundsLeft().intersects(tempObject.getBounds())) {
					leftScan = true;
					Direction_System direction = Direction_System.Left;
					AI_scan2(direction);
				} else {
					dateLogicXY_left();
					leftScan = false;
				}
				if (getBoundsRight().intersects(tempObject.getBounds())) {
					rightScan = true;
					Direction_System direction = Direction_System.Right;
					AI_scan2(direction);
				} else {
					dateLogicXY_right();
					rightScan = false;
				}
			}
		}
	}

	// KEY for movement!!!!!
	public void AI_scan2(Direction_System direction) {
		// this method will see if the player is visible from the AI's view
		// only called from the AI_scan
		if (direction == Direction_System.Up) {
			launchUp(logic_upX, logic_upY);
		}
		if (direction == Direction_System.Down) {
			launchDown(logic_downX, logic_downY);
		}
		if (direction == Direction_System.Left) {
			launchLeft(logic_leftX, logic_leftY);
		}
		if (direction == Direction_System.Right) {
			launchRight(logic_rightX, logic_rightY);
		}
	}

	public void launchUp(int logic_upX, int logic_upY) {
		// dateLogicXY();
		logicUP = true;
	}

	public void launchDown(int logic_downX, int logic_downY) {
		// dateLogicXY();
		logicDOWN = true;
	}

	public void launchLeft(int logic_leftX, int logic_leftY) {
		// dateLogicXY();
		logicLEFT = true;
	}

	public void launchRight(int logic_rightX, int logic_rightY) {
		// dateLogicXY();
		logicRIGHT = true;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
		// return new Rectangle(getX(), getY(), 32, 32);
	}

	public Rectangle getBoundsUp() {
		return new Rectangle(upX, upY, 32, getAwareness());
	}

	public Rectangle getBoundsDown() {
		return new Rectangle(downX, downY, 32, getAwareness());
	}

	public Rectangle getBoundsLeft() {
		return new Rectangle(leftX, leftY, getAwareness(), 32);
	}

	public Rectangle getBoundsRight() {
		return new Rectangle(rightX, rightY, getAwareness(), 32);
	}

	public Rectangle getBoundsUp_move() {
		return new Rectangle(upX_move, upY_move, 32, 32);
	}

	public Rectangle getBoundsDown_move() {
		return new Rectangle(downX_move, downY_move, 32, 32);
	}

	public Rectangle getBoundsLeft_move() {
		return new Rectangle(leftX_move, leftY_move, 32, 32);
	}

	public Rectangle getBoundsRight_move() {
		return new Rectangle(rightX_move, rightY_move, 32, 32);
	}

	public Rectangle getBoundsLOGIC_up() {
		return new Rectangle(logic_upX, logic_upY, 12, 12);
	}

	public Rectangle getBoundsLOGIC_down() {
		return new Rectangle(logic_downX, logic_downY, 12, 12);
	}

	public Rectangle getBoundsLOGIC_left() {
		return new Rectangle(logic_leftX, logic_leftY, 12, 12);
	}

	public Rectangle getBoundsLOGIC_right() {
		return new Rectangle(logic_rightX, logic_rightY, 12, 12);
	}

	public int getWalls() {
		int count = 0;
		for (int i = 0; i < handler.object.size(); i++) {
			Game_Object tempObject = handler.object.get(i);
			if (tempObject.getId() == ID_System.Wall) {
				count++;
			}
		}
		return count;
	}
}
