package DOWNFALL;
//This class is essentially the brain of the hostile (and civilian) space ships
//This class will perform different actions based upon the designated behavior

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import DOWNFALL.Game.STATE;

public class AI_Space_Ship extends Game_Object {

	private Game_Handle handler;
	private int deathTimer, gunTimer, move_Y, move_X, n, ltimer, l2timer;
	private boolean startCoolDown, AI_stats_logic, moveUp, moveDown, death, clampLogic;
	private AI_Behavoir AI_Type;
	private Level1 level;

	public AI_Space_Ship(int x, int y, ID_System id, Game_Handle handler, AI_Behavoir AI_Type, Level1 level) {
		super(x, y, id);
		this.handler = handler;
		this.AI_Type = AI_Type;
		this.level = level;

		AI_stats_logic = false;

		// enables/disables clamp
		clampLogic = true;

		// these variables will move the ship
		move_Y = x;
		move_X = y;

		// movement logic
		moveUp = false;
		moveDown = true;

		setGunReady(false);
		gunTimer = 0;
		deathTimer = 0;
		ltimer = 0;
		l2timer = 0;

		// color logic for the death animation
		n = 255;
		startCoolDown = false;

		// will kill the AI and display graphics
		death = false;
	}

	public Rectangle getBounds() {
		// values of hit box
		return new Rectangle(920 + move_X, 25 + move_Y, 195, 290);
	}

	public void clamp() {
		if (clampLogic) {
			if (move_Y < -26) {
				moveUp = false;
				moveDown = true;
			}
			if (move_Y > 388) {
				moveUp = true;
				moveDown = false;
			}
		}
	}

	public void move() {
		// if the player dies
		for (int l = 0; l < handler.object.size(); l++) {
			Game_Object tempObject = handler.object.get(l);
			if (tempObject.getId() == ID_System.Player_Ship) {
				l2timer++;
			}
		}

		if (l2timer > 0) {
			l2timer = 0;
		} else {
			ltimer++;
		}

		if (ltimer > 1) {
			// removing boundary
			clampLogic = false;
		}

		if (ltimer > 9000) {
			handler.removeObject(this);
		}
		// end of special action

		if (AI_Type == AI_Behavoir.Coward) {
			// Simple back and forth movement pattern

			if (moveUp == true) {
				move_Y = move_Y - getSpeed();
			}
			if (moveDown == true) {
				move_Y = move_Y + getSpeed();
			}
		}
		if (AI_Type == AI_Behavoir.Attacker) {
			// Simple back and forth movement pattern

			if (moveUp == true) {
				move_Y = move_Y - getSpeed();
			}
			if (moveDown == true) {
				move_Y = move_Y + getSpeed();
			}

		}
		if (AI_Type == AI_Behavoir.Civilian) {
			// collision check
			for (int l = 0; l < handler.object.size(); l++) {
				Game_Object tempObject = handler.object.get(l);
				if (tempObject.getId() == ID_System.Player_Ship) {
					if (getBounds().intersects(tempObject.getBounds())) {
						// kills both the player and the AI
						tempObject.setHealth(0);
						death = true;
						setHealth(-1);
					}
				}
			}

			// Simple fleeing motion
			move_X = move_X - getSpeed();
			// System.out.println(move_X);

			if (move_X < -1200) {
				level.nextWave();
				handler.removeObject(this);
			}

		}
	}

	public void attack() {
		if (AI_Type == AI_Behavoir.Coward) {
			// Attack
			if (getGunReady() == true) {
				// attack

				// up
				handler.addObject(new AI_Projectile((915 + move_X + 62), (25 + move_Y + 58), ID_System.AI_Projectile,
						handler, Direction_System.Left));

				// down
				handler.addObject(new AI_Projectile((915 + move_X + 62), (25 + move_Y + 220), ID_System.AI_Projectile,
						handler, Direction_System.Left));
				setGunReady(false);
			} else {
				// reset timer
				startCoolDown = true;
			}
		}
		if (AI_Type == AI_Behavoir.Attacker) {
			// Attack
			if (getGunReady() == true) {
				// attack

				// up
				handler.addObject(new AI_Projectile((915 + move_X + 62), (25 + move_Y + 58), ID_System.AI_Projectile,
						handler, Direction_System.Left));

				// down
				handler.addObject(new AI_Projectile((915 + move_X + 62), (25 + move_Y + 220), ID_System.AI_Projectile,
						handler, Direction_System.Left));

				// up
				handler.addObject(new AI_Projectile((915 + move_X + 62), (25 + move_Y + 58), ID_System.AI_Projectile,
						handler, Direction_System.Left_Up));

				// down
				handler.addObject(new AI_Projectile((915 + move_X + 62), (25 + move_Y + 220), ID_System.AI_Projectile,
						handler, Direction_System.Left_Up));

				// up
				handler.addObject(new AI_Projectile((915 + move_X + 62), (25 + move_Y + 58), ID_System.AI_Projectile,
						handler, Direction_System.Left_Down));

				// down
				handler.addObject(new AI_Projectile((915 + move_X + 62), (25 + move_Y + 220), ID_System.AI_Projectile,
						handler, Direction_System.Left_Down));
				setGunReady(false);
			} else {
				// reset timer
				startCoolDown = true;
			}
		}
	}

	public void health() {
		// Testing
		// System.out.println("AI Health: " + getHealth());
		if (AI_Type != AI_Behavoir.Civilian) {
			if (getHealth() < 0) {
				death = true;
			}
		}
		if (AI_Type == AI_Behavoir.Civilian) {
			if (getHealth() < 0) {
				level.lose();
				death = true;
			}
		}
	}

	public void tick() {
		// cooldown logic

		if (startCoolDown = true) {
			if (gunTimer > (getCombatFrequency())) {
				setGunReady(true);
				gunTimer = 0;
				startCoolDown = false;
			} else {
				gunTimer++;
			}
		}

		// logic
		if (AI_stats_logic == false) {
			if (AI_Type == AI_Behavoir.Coward) {
				setSpeed(2);
				setCombatFrequency(95);
				setAwareness(getAwareness() + 234);
				setDamage(5);
			}

			if (AI_Type == AI_Behavoir.Attacker) {
				setSpeed(3);
				setCombatFrequency(65);
				setAwareness(getAwareness() + 234);
				setDamage(2);
			}

			if (AI_Type == AI_Behavoir.Civilian) {
				setHealth(1);
				setSpeed(2);
				setCombatFrequency(-1);
				setAwareness(getAwareness() - 1);
				setDamage(-1);
			}
			AI_stats_logic = true;
		}

		// Note: The x and y axis have been inverted for some reason
		if (death == false) {
			clamp();
			move();
			if (AI_Type != AI_Behavoir.Civilian) {
				attack();
			}
			health();
		}
	}

	public void render(Graphics g) {
		if (death) {
			if (deathTimer > 15) {
				Color nnn = new Color(n, n, n);
				g.setColor(nnn);
				// g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
				n = n - 5;
				if (n < 15) {
					// launch the next wave...
					level.nextWave();
					// delete
					handler.removeObject(this);
				}
			} else {
				deathTimer++;
			}
		}

		if (death == false) {
			// AI Ship
			// Location Declaration
			int graphic_val_1 = -165 + move_Y;
			int graphic_val_2 = 900 + move_X;

			// Color Declaration
			Color N_Color = new Color(255, 255, 255);
			Color N_Color2 = new Color(255, 255, 255);

			if (AI_Type == AI_Behavoir.Coward) {
				N_Color = new Color(153, 153, 0);
				N_Color2 = new Color(253, 253, 0);
			}

			if (AI_Type == AI_Behavoir.Attacker) {
				N_Color = new Color(155, 4, 4);
				N_Color2 = new Color(255, 4, 4);
			}

			if (AI_Type == AI_Behavoir.Civilian) {
				N_Color = new Color(0, 70, 159);
				N_Color2 = new Color(0, 70, 255);
			}
			// Part N_1
			int Part_N_1_x[] = { 280 + graphic_val_1, 280 + graphic_val_1, 300 + graphic_val_1, 370 + graphic_val_1,
					390 + graphic_val_1, 390 + graphic_val_1, 370 + graphic_val_1, 350 + graphic_val_1,
					320 + graphic_val_1, 300 + graphic_val_1 };
			int Part_N_1_y[] = { 60 + graphic_val_2, 40 + graphic_val_2, 20 + graphic_val_2, 20 + graphic_val_2,
					40 + graphic_val_2, 60 + graphic_val_2, 60 + graphic_val_2, 40 + graphic_val_2, 40 + graphic_val_2,
					60 + graphic_val_2 };

			g.setColor(N_Color);
			g.fillPolygon(Part_N_1_y, Part_N_1_x, 10);

			// Part N_2
			int Part_N_2_x[] = { 280 + graphic_val_1, 280 + graphic_val_1, 300 + graphic_val_1, 320 + graphic_val_1,
					350 + graphic_val_1, 370 + graphic_val_1, 390 + graphic_val_1, 390 + graphic_val_1,
					370 + graphic_val_1, 360 + graphic_val_1, 310 + graphic_val_1, 300 + graphic_val_1 };
			int Part_N_2_y[] = { 215 + graphic_val_2, 60 + graphic_val_2, 60 + graphic_val_2, 40 + graphic_val_2,
					40 + graphic_val_2, 60 + graphic_val_2, 60 + graphic_val_2, 215 + graphic_val_2,
					215 + graphic_val_2, 190 + graphic_val_2, 190 + graphic_val_2, 215 + graphic_val_2 };

			g.setColor(Color.black);
			g.fillPolygon(Part_N_2_y, Part_N_2_x, 12);

			// Part N_3
			int Part_N_3_x[] = { 260 + graphic_val_1, 280 + graphic_val_1, 280 + graphic_val_1, 260 + graphic_val_1 };
			int Part_N_3_y[] = { 210 + graphic_val_2, 210 + graphic_val_2, 60 + graphic_val_2, 80 + graphic_val_2 };

			int Part_N_3_x2[] = { 410 + graphic_val_1, 390 + graphic_val_1, 390 + graphic_val_1, 410 + graphic_val_1 };
			int Part_N_3_y2[] = { 210 + graphic_val_2, 210 + graphic_val_2, 60 + graphic_val_2, 80 + graphic_val_2 };

			g.setColor(Color.black);
			g.fillPolygon(Part_N_3_y, Part_N_3_x, 4);

			g.setColor(Color.black);
			g.fillPolygon(Part_N_3_y2, Part_N_3_x2, 4);

			// Part N_4
			int Part_N_4_x[] = { 260 + graphic_val_1, 200 + graphic_val_1, 190 + graphic_val_1, 190 + graphic_val_1,
					260 + graphic_val_1 };
			int Part_N_4_y[] = { 200 + graphic_val_2, 200 + graphic_val_2, 190 + graphic_val_2, 140 + graphic_val_2,
					90 + graphic_val_2 };

			int Part_N_4_x2[] = { 410 + graphic_val_1, 470 + graphic_val_1, 480 + graphic_val_1, 480 + graphic_val_1,
					410 + graphic_val_1 };
			int Part_N_4_y2[] = { 200 + graphic_val_2, 200 + graphic_val_2, 190 + graphic_val_2, 140 + graphic_val_2,
					90 + graphic_val_2 };

			g.setColor(Color.black);
			g.fillPolygon(Part_N_4_y, Part_N_4_x, 5);

			g.setColor(Color.black);
			g.fillPolygon(Part_N_4_y2, Part_N_4_x2, 5);

			// Part O_N_1
			g.setColor(N_Color2);
			g.drawPolygon(Part_N_1_y, Part_N_1_x, 10);

			// Part O_N_2
			g.setColor(N_Color2);
			g.drawPolygon(Part_N_2_y, Part_N_2_x, 12);

			// Part O_N_3
			g.setColor(N_Color2);
			g.drawPolygon(Part_N_3_y, Part_N_3_x, 4);

			g.setColor(N_Color2);
			g.drawPolygon(Part_N_3_y2, Part_N_3_x2, 4);

			// Part O_N_4
			g.setColor(N_Color2);
			g.drawPolygon(Part_N_4_y, Part_N_4_x, 5);

			g.setColor(N_Color2);
			g.drawPolygon(Part_N_4_y2, Part_N_4_x2, 5);

			// Gun(s)
			if (AI_Type != AI_Behavoir.Civilian) {
				// up
				g.setColor(Color.black);
				g.fillRect((915 + move_X) + 62, (25 + move_Y) + 58, 34, 12);
				g.setColor(N_Color2);
				g.drawRect((915 + move_X) + 62, (25 + move_Y) + 58, 34, 12);

				// down
				g.setColor(Color.black);
				g.fillRect((915 + move_X) + 62, (25 + move_Y) + 220, 34, 12);
				g.setColor(N_Color2);
				g.drawRect((915 + move_X) + 62, (25 + move_Y) + 220, 34, 12);
			}

			// Hit box
			if (Game.getTESTING_MODE() == true) {
				g.setColor(Color.green);
				// if (shipHIT == true) {
				// g.setColor(Color.red);
				// }
				g.drawRect(920 + move_X, 25 + move_Y, 195, 290);
			}
		} else {
			// AI Ship: Death Animation

			// Location Declaration
			int graphic_val_1 = -165 + move_Y;
			int graphic_val_2 = 900 + move_X;

			// Color Declaration
			Color N_Color = new Color(n, n, n);
			Color N_Color2 = new Color(n, n, n);

			// Part N_1
			int Part_N_1_x[] = { 280 + graphic_val_1, 280 + graphic_val_1, 300 + graphic_val_1, 370 + graphic_val_1,
					390 + graphic_val_1, 390 + graphic_val_1, 370 + graphic_val_1, 350 + graphic_val_1,
					320 + graphic_val_1, 300 + graphic_val_1 };
			int Part_N_1_y[] = { 60 + graphic_val_2, 40 + graphic_val_2, 20 + graphic_val_2, 20 + graphic_val_2,
					40 + graphic_val_2, 60 + graphic_val_2, 60 + graphic_val_2, 40 + graphic_val_2, 40 + graphic_val_2,
					60 + graphic_val_2 };

			g.setColor(N_Color);
			g.fillPolygon(Part_N_1_y, Part_N_1_x, 10);

			// Part N_2
			int Part_N_2_x[] = { 280 + graphic_val_1, 280 + graphic_val_1, 300 + graphic_val_1, 320 + graphic_val_1,
					350 + graphic_val_1, 370 + graphic_val_1, 390 + graphic_val_1, 390 + graphic_val_1,
					370 + graphic_val_1, 360 + graphic_val_1, 310 + graphic_val_1, 300 + graphic_val_1 };
			int Part_N_2_y[] = { 215 + graphic_val_2, 60 + graphic_val_2, 60 + graphic_val_2, 40 + graphic_val_2,
					40 + graphic_val_2, 60 + graphic_val_2, 60 + graphic_val_2, 215 + graphic_val_2,
					215 + graphic_val_2, 190 + graphic_val_2, 190 + graphic_val_2, 215 + graphic_val_2 };

			g.setColor(Color.black);
			g.fillPolygon(Part_N_2_y, Part_N_2_x, 12);

			// Part N_3
			int Part_N_3_x[] = { 260 + graphic_val_1, 280 + graphic_val_1, 280 + graphic_val_1, 260 + graphic_val_1 };
			int Part_N_3_y[] = { 210 + graphic_val_2, 210 + graphic_val_2, 60 + graphic_val_2, 80 + graphic_val_2 };

			int Part_N_3_x2[] = { 410 + graphic_val_1, 390 + graphic_val_1, 390 + graphic_val_1, 410 + graphic_val_1 };
			int Part_N_3_y2[] = { 210 + graphic_val_2, 210 + graphic_val_2, 60 + graphic_val_2, 80 + graphic_val_2 };

			g.setColor(Color.black);
			g.fillPolygon(Part_N_3_y, Part_N_3_x, 4);

			g.setColor(Color.black);
			g.fillPolygon(Part_N_3_y2, Part_N_3_x2, 4);

			// Part N_4
			int Part_N_4_x[] = { 260 + graphic_val_1, 200 + graphic_val_1, 190 + graphic_val_1, 190 + graphic_val_1,
					260 + graphic_val_1 };
			int Part_N_4_y[] = { 200 + graphic_val_2, 200 + graphic_val_2, 190 + graphic_val_2, 140 + graphic_val_2,
					90 + graphic_val_2 };

			int Part_N_4_x2[] = { 410 + graphic_val_1, 470 + graphic_val_1, 480 + graphic_val_1, 480 + graphic_val_1,
					410 + graphic_val_1 };
			int Part_N_4_y2[] = { 200 + graphic_val_2, 200 + graphic_val_2, 190 + graphic_val_2, 140 + graphic_val_2,
					90 + graphic_val_2 };

			g.setColor(Color.black);
			g.fillPolygon(Part_N_4_y, Part_N_4_x, 5);

			g.setColor(Color.black);
			g.fillPolygon(Part_N_4_y2, Part_N_4_x2, 5);

			// Part O_N_1
			g.setColor(N_Color2);
			g.drawPolygon(Part_N_1_y, Part_N_1_x, 10);

			// Part O_N_2
			g.setColor(N_Color2);
			g.drawPolygon(Part_N_2_y, Part_N_2_x, 12);

			// Part O_N_3
			g.setColor(N_Color2);
			g.drawPolygon(Part_N_3_y, Part_N_3_x, 4);

			g.setColor(N_Color2);
			g.drawPolygon(Part_N_3_y2, Part_N_3_x2, 4);

			// Part O_N_4
			g.setColor(N_Color2);
			g.drawPolygon(Part_N_4_y, Part_N_4_x, 5);

			g.setColor(N_Color2);
			g.drawPolygon(Part_N_4_y2, Part_N_4_x2, 5);

			// Gun(s)
			if (AI_Type != AI_Behavoir.Civilian) {
				// up
				g.setColor(Color.black);
				g.fillRect((915 + move_X) + 62, (25 + move_Y) + 58, 34, 12);
				g.setColor(N_Color2);
				g.drawRect((915 + move_X) + 62, (25 + move_Y) + 58, 34, 12);

				// down
				g.setColor(Color.black);
				g.fillRect((915 + move_X) + 62, (25 + move_Y) + 220, 34, 12);
				g.setColor(N_Color2);
				g.drawRect((915 + move_X) + 62, (25 + move_Y) + 220, 34, 12);
			}
			// Hit box
			if (Game.getTESTING_MODE() == true) {
				g.setColor(Color.green);
				// if (shipHIT == true) {
				// g.setColor(Color.red);
				// }
				g.drawRect(920 + move_X, 25 + move_Y, 195, 290);
			}
		}
	}

	public static void delay(int millisecs) {
		try {
			Thread.currentThread().sleep(millisecs);
		} catch (InterruptedException e) {
		}
	} // delay Method
}
