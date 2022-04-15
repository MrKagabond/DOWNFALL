package DOWNFALL;
//The space ship controlled by the player in level 1

//Depended upon the Key_Input class for movement and other abilities 

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player_Space_Ship extends Game_Object {

	private Game_Handle handler;

	// denotes if the ship has been hit by a projectile
	boolean movementPermission, spawnedHUD, shipHIT, animateLogic;
	private int move_y, localTimer, animationTimer;

	public Player_Space_Ship(int x, int y, ID_System id, Game_Handle handler) {
		super(x, y, id);
		this.handler = handler;
		setAwareness(getAwareness() + 180);
		setCombatFrequency(getCombatFrequency() + 30);
		setSpeed(getSpeed() + 5);

		localTimer = 0;
		spawnedHUD = false;
		// logic
		move_y = getY();

		// permission to move
		movementPermission = true;

		// timer used for animations during level 2
		animationTimer = 0;
		animateLogic = true;
	}

	public void gun_logic() {
		if (getGunReady() == false) {
			localTimer++;
		}

		if (localTimer > getCombatFrequency()) {
			setGunReady(true);
			localTimer = 0;
		}
	}

	public void hud() {
		// will spawn the HUD
		if (spawnedHUD == false) {
			handler.addObject(new HUD(0, 0, ID_System.HUD, handler, this));
			spawnedHUD = true;
		}
	}

	public void clamp() { // "clamping"
		// setY((int) Game.clamp((-1*y)+45, 0, Game.HEIGHT));
		// System.out.println(y);

		movementPermission = true;
		if (move_y < -405) {
			movementPermission = false;
		} else if (move_y > 46) {
			movementPermission = false;
		}

		if (movementPermission == true) {
			y = move_y;
		}
	}

	public void tick() {
		if (id != ID_System.Wall) {
			// will spawn hud
			hud();
			// gun logic
			gun_logic();

			// clamping logic
			clamp();

			// x = x + velX;
			x = -5;
			move_y = y + (-1 * velY);

			if (getHealth() <= 0) {
				handler.removeObject(this);
			}
		}
	}

	public void render(Graphics g) {
		// Animation
		if (id == ID_System.WallS) {
			x = (20);
		}

		if (id == ID_System.Wall) {
			if (animateLogic == true) {
				x = -5;
				animateLogic = false;
			}
			g.setColor(Color.white);
			g.fillRect(120, 145 + animationTimer, 32, 32);
			g.setColor(Color.green);
			g.drawRect(120, 145 + animationTimer, 32, 32);

			// while (x < 15) {
			if (x < 25) {
				x = x + 1;
			}

			if (animationTimer < 160) {
				animationTimer++;
			} else {
				g.setColor(Color.black);
				g.fillRect(120, 145 + animationTimer, 32, 32);
				g.drawRect(120, 145 + animationTimer, 32, 32);
			}
		}

		// TEST
		// g.setColor(Color.blue);
		// up
		// g.fillRect(270 + x, 66 + (-1 * y), 12, 12);
		// down
		// g.fillRect(270 + x, 264 + (-1 * y), 12, 12);

		// SHIP
		// Location
		int location_x = (25 + x);
		int location_y = (55 + y);

		// Color Declaration
		Color Color_Green = new Color(12, 233, 17);
		Color Color_Green_Light = new Color(142, 204, 46);
		Color Color_PlayerHull = new Color(125, 0, 125);
		Color Color_PlayerHull_Light = new Color(193, 117, 193);
		Color Color_Grey = new Color(148, 148, 148);
		Color Color_Grey_Outline = new Color(127, 127, 127);
		Color Color_Grey_Outline2 = new Color(115, 115, 15);

		// Cockpit
		int Cockpit_x[] = { 180 + location_x, 200 + location_x, 220 + location_x, 220 + location_x, 200 + location_x,
				180 + location_x, 180 + location_x, 190 + location_x, 190 + location_x, 180 + location_x,
				180 + location_x, 190 + location_x, 190 + location_x, 180 + location_x, 180 + location_x };
		int Cockpit_y[] = { 180 - location_y, 180 - location_y, 200 - location_y, 250 - location_y, 270 - location_y,
				270 - location_y, 260 - location_y, 250 - location_y, 240 - location_y, 230 - location_y,
				220 - location_y, 210 - location_y, 200 - location_y, 190 - location_y, 180 - location_y };

		g.setColor(Color_Green_Light);
		g.fillPolygon(Cockpit_x, Cockpit_y, 15);
		g.setColor(Color_Green);
		g.drawPolygon(Cockpit_x, Cockpit_y, 15);

		// Detail
		// < >

		// "Connection"
		int conn_x[] = { 105, 120 + location_x, 120 + location_x, 105 };
		int conn_y[] = { 200 - location_y, 200 - location_y, 250 - location_y, 250 - location_y };

		g.setColor(Color_Grey);
		g.fillPolygon(conn_x, conn_y, 4);
		g.setColor(Color_Grey_Outline);
		g.drawPolygon(conn_x, conn_y, 4);

		// Detail
		g.setColor(Color_Grey_Outline2);
		g.drawLine(105, 204 - location_y, 120 + location_x, 204 - location_y);
		g.drawLine(105, 208 - location_y, 120 + location_x, 208 - location_y);
		g.drawLine(105, 212 - location_y, 120 + location_x, 212 - location_y);
		g.drawLine(105, 216 - location_y, 120 + location_x, 216 - location_y);
		g.drawLine(105, 220 - location_y, 120 + location_x, 220 - location_y);
		g.drawLine(105, 224 - location_y, 120 + location_x, 224 - location_y);
		g.drawLine(105, 228 - location_y, 120 + location_x, 228 - location_y);
		g.drawLine(105, 232 - location_y, 120 + location_x, 232 - location_y);
		g.drawLine(105, 234 - location_y, 120 + location_x, 234 - location_y);
		g.drawLine(105, 238 - location_y, 120 + location_x, 238 - location_y);
		g.drawLine(105, 242 - location_y, 120 + location_x, 242 - location_y);
		g.drawLine(105, 246 - location_y, 120 + location_x, 246 - location_y);
		g.drawLine(105, 250 - location_y, 120 + location_x, 250 - location_y);

		g.setColor(Color_Grey_Outline);
		g.drawLine(105, 203 - location_y, 120 + location_x, 203 - location_y);
		g.drawLine(105, 207 - location_y, 120 + location_x, 207 - location_y);
		g.drawLine(105, 211 - location_y, 120 + location_x, 211 - location_y);
		g.drawLine(105, 215 - location_y, 120 + location_x, 215 - location_y);
		g.drawLine(105, 219 - location_y, 120 + location_x, 219 - location_y);
		g.drawLine(105, 223 - location_y, 120 + location_x, 223 - location_y);
		g.drawLine(105, 227 - location_y, 120 + location_x, 227 - location_y);
		g.drawLine(105, 231 - location_y, 120 + location_x, 231 - location_y);
		g.drawLine(105, 233 - location_y, 120 + location_x, 233 - location_y);
		g.drawLine(105, 237 - location_y, 120 + location_x, 237 - location_y);
		g.drawLine(105, 241 - location_y, 120 + location_x, 241 - location_y);
		g.drawLine(105, 245 - location_y, 120 + location_x, 245 - location_y);
		g.drawLine(105, 249 - location_y, 120 + location_x, 249 - location_y);

		// Hull
		int Hull_x[] = { 110 + location_x, 110 + location_x, 130 + location_x, 240 + location_x, 240 + location_x,
				220 + location_x, 210 + location_x, 200 + location_x, 180 + location_x, 180 + location_x,
				190 + location_x, 190 + location_x, 180 + location_x, 180 + location_x, 190 + location_x,
				190 + location_x, 180 + location_x, 180 + location_x, 200 + location_x, 210 + location_x,
				220 + location_x, 240 + location_x, 240 + location_x, 130 + location_x, 110 + location_x };
		int Hull_y[] = { 190 - location_y, 260 - location_y, 280 - location_y, 280 - location_y, 240 - location_y,
				260 - location_y, 260 - location_y, 270 - location_y, 270 - location_y, 260 - location_y,
				250 - location_y, 240 - location_y, 230 - location_y, 220 - location_y, 210 - location_y,
				200 - location_y, 190 - location_y, 180 - location_y, 180 - location_y, 190 - location_y,
				190 - location_y, 210 - location_y, 170 - location_y, 170 - location_y, 190 - location_y };

		g.setColor(Color_PlayerHull_Light);
		g.fillPolygon(Hull_x, Hull_y, 25);
		g.setColor(Color_PlayerHull);
		g.drawPolygon(Hull_x, Hull_y, 25);

		// Detail
		// < >

		// Wings (Up & Down)
		int WingsUp_x[] = { 110 + location_x, 110 + location_x, 120 + location_x, 120 + location_x, 110 + location_x,
				110 + location_x, 170 + location_x, 130 + location_x, 110 + location_x };
		int WingsUp_y[] = { 190 - location_y, 180 - location_y, 170 - location_y, 140 - location_y, 130 - location_y,
				100 - location_y, 170 - location_y, 170 - location_y, 190 - location_y };
		int WingsDown_x[] = { 110 + location_x, 110 + location_x, 120 + location_x, 120 + location_x, 110 + location_x,
				110 + location_x, 170 + location_x, 130 + location_x, 110 + location_x };
		int WingsDown_y[] = { 260 - location_y, 270 - location_y, 280 - location_y, 310 - location_y, 320 - location_y,
				350 - location_y, 280 - location_y, 280 - location_y, 260 - location_y };

		g.setColor(Color_PlayerHull_Light);
		g.fillPolygon(WingsUp_x, WingsUp_y, 9);
		g.setColor(Color_PlayerHull);
		g.drawPolygon(WingsUp_x, WingsUp_y, 9);

		g.setColor(Color_PlayerHull_Light);
		g.fillPolygon(WingsDown_x, WingsDown_y, 9);
		g.setColor(Color_PlayerHull);
		g.drawPolygon(WingsDown_x, WingsDown_y, 9);

		// Detail
		// < >

		// Guns
		// Guns Base Up

		int GunsBaseUp_x[] = { 175 + location_x, 190 + location_x, 210 + location_x, 220 + location_x };
		int GunsBaseUp_y[] = { 170 - location_y, 150 - location_y, 150 - location_y, 170 - location_y };

		g.setColor(Color_PlayerHull_Light);
		g.fillPolygon(GunsBaseUp_x, GunsBaseUp_y, 4);

		g.setColor(Color_PlayerHull);
		g.drawPolygon(GunsBaseUp_x, GunsBaseUp_y, 4);

		// Guns Base Down

		int GunsBaseDown_x[] = { 175 + location_x, 190 + location_x, 210 + location_x, 220 + location_x };
		int GunsBaseDown_y[] = { 280 - location_y, 300 - location_y, 300 - location_y, 280 - location_y };

		g.setColor(Color_PlayerHull_Light);
		g.fillPolygon(GunsBaseDown_x, GunsBaseDown_y, 4);

		g.setColor(Color_PlayerHull);
		g.drawPolygon(GunsBaseDown_x, GunsBaseDown_y, 4);

		// Guns

		// Up
		g.setColor(Color_PlayerHull);
		g.drawLine(200 + location_x, 150 - location_y, 220 + location_x, 130 - location_y);
		g.drawLine(220 + location_x, 130 - location_y, 230 + location_x, 130 - location_y);

		g.setColor(Color_PlayerHull_Light);
		g.fillRect(230 + location_x, 120 - location_y, 20, 10);
		g.setColor(Color_PlayerHull);
		g.drawRect(230 + location_x, 120 - location_y, 20, 10);

		// Down
		g.setColor(Color_PlayerHull);
		g.drawLine(200 + location_x, 300 - location_y, 220 + location_x, 320 - location_y);
		g.drawLine(220 + location_x, 320 - location_y, 230 + location_x, 320 - location_y);

		g.setColor(Color_PlayerHull_Light);
		g.fillRect(230 + location_x, 320 - location_y, 20, 10);
		g.setColor(Color_PlayerHull);
		g.drawRect(230 + location_x, 320 - location_y, 20, 10);

		// Default Location
		int HullN2_x[] = { 60, 80, 80, 100, 110, 110, 100, 80, 80, 60, 60, 80, 60, 60, 70, 70, 60, 60, 80, 60, 60 };
		int HullN2_y[] = { 270 - location_y, 270 - location_y, 271 - location_y, 271 - location_y, 260 - location_y,
				190 - location_y, 179 - location_y, 179 - location_y, 180 - location_y, 180 - location_y,
				190 - location_y, 190 - location_y, 210 - location_y, 220 - location_y, 220 - location_y,
				230 - location_y, 230 - location_y, 240 - location_y, 260 - location_y, 260 - location_y,
				270 - location_y };

		g.setColor(Color_PlayerHull_Light);
		g.fillPolygon(HullN2_x, HullN2_y, 21);
		g.setColor(Color_PlayerHull);
		g.drawPolygon(HullN2_x, HullN2_y, 21);

		// Hit box
		if (Game.getTESTING_MODE() == true) {
			if (id != ID_System.WallS) {
				g.setColor(Color.green);
				if (shipHIT == true) {
					g.setColor(Color.red);
				}
				g.drawRect(x + 65, (-1 * y) + 45, 210, 250);
			} else {
				g.setColor(Color.green);
				if (shipHIT == true) {
					g.setColor(Color.red);
				}
				g.drawRect(x + 75, (-1 * y) + 45, 210, 250);
			}
		}
	}

	public Rectangle getBounds() {
		if (id != ID_System.WallS) {
			return new Rectangle((x + 5), (-1 * y) + 45, 275, 250);
		} else {
			return new Rectangle((x + 30), (-1 * y) + 45, 275, 250);
		}
	}
}
