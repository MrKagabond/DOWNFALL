package DOWNFALL;
//This contains the information/data required for the second level
//This class will spawn in assets and take care of appropriate logic t ensure that the game works as intended

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import DOWNFALL.Game.STATE;

public class Level2 {

	private Game game;
	private Game_Handle handler;

	private boolean specialCase, door_active, playerSpawned, show_controlls1, show_controlls2, ready_to_spawn_player,
			spawn1, spawn2, spawn3, spawn4, AI1S, AI2S, AI3S;
	private int graphic_timer, waveNum, lastWaveNum, playerX, playerY, playerHealth, timer, text_timer_graphics;

	Level2(Game game, Game_Handle handler) {
		this.game = game;
		this.handler = handler;

		graphic_timer = 255;

		door_active = true;
		specialCase = true;

		// playerSpawned = false;
		ready_to_spawn_player = false;

		show_controlls1 = true;
		show_controlls2 = false;

		waveNum = 1;
		lastWaveNum = -1;

		playerX = ((Game.WIDTH / 2) - 52);
		playerY = ((Game.HEIGHT / 2) - 52);
		playerHealth = 100;

		spawn1 = true;
		spawn2 = false;
		spawn3 = false;
		spawn4 = false;

		// graphics
		text_timer_graphics = 0;

		// logic, prevents the re-spawning of AI if the player backtracks
		// remember to UPDATE!!!
		AI1S = true;
		AI2S = true;
		AI3S = true;
	}

	// ~outdated~
	// USE!!!!
	// Required for Victory and GmaeOver menu systems!
	public void reset() {
		// resets ALL key values
		// essentially re-construct constructor
		ready_to_spawn_player = false;

		waveNum = 1;
		lastWaveNum = -1;

		spawn1 = true;
		spawn2 = false;
		spawn3 = false;
		spawn4 = false;
	}

	public void spawnLogic_enable() {
		// Will enable the spawning of a room

		show_controlls1 = false;
		show_controlls2 = false;
		// enable
		if (waveNum == 1) {
			spawn1 = true;
			show_controlls1 = true;
		}
		if (waveNum == 2) {
			spawn2 = true;
			show_controlls2 = true;
		}
		if (waveNum == 3) {
			spawn3 = true;
		}
		if (waveNum == 4) {
			spawn4 = true;
		}
		if (waveNum > 4) {
			game.gameState = STATE.Victory;
		}
	}

	public void spawnLogic_disable() {
		// Will disable the spawning of a room

		// disable
		lastWaveNum = waveNum;
	}

	public void spawnRoom() {
		ready_to_spawn_player = false;

		// Note: Please spawn player AFTER walls
		if (waveNum == 1 && (lastWaveNum != waveNum)) {
			show_controlls1 = true;
			if (specialCase == true) {
				// Walls
				boarder();

				// player ship
				handler.addObject(
						new Player_Space_Ship(((Game.WIDTH / 2) - (32 * 5) + 42), 0, ID_System.Wall, handler));

				// Once ALL walls have been spawned
				// timer
				// player logic <<HERE>>

				// Doors
				handler.addObject(new Door(((Game.WIDTH / 2) - (32 * 5) + 42), 0, ID_System.Door_Up, handler, this));
			} else {
				// Walls
				boarder();

				// player ship
				handler.addObject(
						new Player_Space_Ship(((Game.WIDTH / 2) - (32 * 5) + 42), 0, ID_System.WallS, handler));

				// Once ALL walls have been spawned
				ready_to_spawn_player = true;

				// Doors
				handler.addObject(new Door(((Game.WIDTH / 2) - (32 * 5) + 42), 0, ID_System.Door_Up, handler, this));

			}
		} // end of waveNum 1

		// Potential issue with "(lastWaveNum != waveNum)"
		if (waveNum == 2 && (lastWaveNum != waveNum)) {
			// fixes errors involving the first room
			specialCase = false;
			show_controlls2 = true;

			// Walls
			int n = 12;
			// for (int i = 0; i < ((Game.WIDTH - (n * 2)) / n); i++) {
			// handler.addObject(new Wall(12 + (i * n), 144, ID_System.Wall, handler, n,
			// true));
			// }
			boarder();

			if (AI1S == true) {
				// AI
				handler.addObject(
						new New_AI(50, (((int) Game.HEIGHT / 2)), ID_System.AI, handler, AI_Behavoir.Patrol_Hor));
				handler.addObject(
						new New_AI(50, (((int) Game.HEIGHT / 2)), ID_System.AI, handler, AI_Behavoir.Patrol_Vert));
				AI1S = false;
			}
			// Once ALL walls have been spawned
			ready_to_spawn_player = true;

			// Doors
			handler.addObject(new Door(((Game.WIDTH / 2) - (32 * 5) + 42), 0, ID_System.Door_Up, handler, this));
			handler.addObject(new Door(((Game.WIDTH / 2) - (32 * 5) + 42), (Game.HEIGHT - 65), ID_System.Door_Down,
					handler, this));

		} // end of waveNum 3
		if (waveNum == 3 && (lastWaveNum != waveNum)) {
			show_controlls2 = true;

			// Walls
			boarder();

			// Once ALL walls have been spawned
			ready_to_spawn_player = true;

			if (AI2S == true) {
				// AI
				handler.addObject(new AI(100, (((int) Game.HEIGHT / 2)), ID_System.AI, handler));
				AI2S = false;
			}
			// Doors
			handler.addObject(new Door(((Game.WIDTH / 2) - (32 * 5) + 42), 0, ID_System.Door_Up, handler, this));
			handler.addObject(new Door(((Game.WIDTH / 2) - (32 * 5) + 42), (Game.HEIGHT - 65), ID_System.Door_Down,
					handler, this));

		} // end of waveNum 3

		if (waveNum == 4 && (lastWaveNum != waveNum)) {
			show_controlls2 = true;

			// Walls
			int n = 50;
			for (int i = -1; i < 5; i++) {
				handler.addObject(new Wall((12 * 20), (n + 12) + (i * n), ID_System.Wall, handler, n));
			}
			boarder();

			// Once ALL walls have been spawned
			ready_to_spawn_player = true;

			if (AI3S == true) {
				// AI
				handler.addObject(new AI(150, 300, ID_System.AI, handler));
				handler.addObject(new AI(150, 200, ID_System.AI, handler));
				handler.addObject(new AI(150, 100, ID_System.AI, handler));
				AI3S = false;
			}
			// Doors
			handler.addObject(new Door(((Game.WIDTH / 2) - (32 * 5) + 42), 0, ID_System.Door_Up, handler, this));
			handler.addObject(new Door(((Game.WIDTH / 2) - (32 * 5) + 42), (Game.HEIGHT - 65), ID_System.Door_Down,
					handler, this));

		} // end of waveNum 4

	}

	public void request_player_spawn(int x, int y, int hp) {
		// handler.addObject(new Player((x), (y), ID_System.Player_3D, handler, this));
		// set hp..........
		playerX = x;
		playerY = y;
		playerHealth = hp;
	}

	public void tick() {
		/*
		 * if (timer > 161) { System.out.println(timer); if (timer > 160) {
		 * ready_to_spawn_player = true; request_player_spawn(120, (145 + 159), 100); }
		 * } else { timer++; } if (timer == 162) { ready_to_spawn_player = false;
		 * timer=-1; }
		 * 
		 * if (graphic_timer <= 1) { graphic_timer = 255; } else { graphic_timer =
		 * graphic_timer - 3; }
		 */

		// System.out.println(timer);
		// Special case: for animation ONLY!
		if (timer > 75) {
			ready_to_spawn_player = true;
			request_player_spawn(120, (145 + 159), 100);
		}
		if (timer >= 0) {
			timer++;
		}
		if (timer > 77) {
			ready_to_spawn_player = false;
			timer = -1;
		}

		// Player Logic
		// ERROR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		if (ready_to_spawn_player == true) {
			door_active = false;

			handler.addObject(new Player((playerX), (playerY), ID_System.Player_3D, handler, this, false));

			// setting health
			for (int dae = 0; dae < handler.object.size(); dae++) {
				Game_Object tempObject = handler.object.get(dae);
				if (tempObject.getId() == ID_System.Player_3D) {
					tempObject.setHealth(playerHealth);
				}
			}
		}

		// Door active logic
		// 1] disable doors
		// 2] scan for enemies
		// 3] If scan == false, enable doors
		setDoorActive(false);
		boolean doorLogic = hostileScan();
		// doorLogic == was an AI found
		if (doorLogic == false) {
			setDoorActive(true);
		}

		spawnRoom();
		spawnLogic_enable();
		spawnLogic_disable();
	}

	public boolean getDoorActive() {
		return door_active;
	}

	public void setDoorActive(boolean door_active) {
		this.door_active = door_active;
	}

	public void killALL() {
		while (handler.object.size() > 0) {
			for (int i = 0; i < handler.object.size(); i++) {
				Game_Object tempObject = handler.object.get(i);
				handler.removeObject(tempObject);
			}
		}
	}

	public int getwaveNum() {
		return waveNum;
	}

	public void setwaveNum(int waveNum) {
		this.waveNum = waveNum;
	}

	public void render(Graphics g) {
		// show controlls

		Font text_header = new Font("MingLiU_HKSCS", 3, 30);
		Font text_body = new Font("MingLiU_HKSCS", 1, 20);
		Color text = new Color(text_timer_graphics, text_timer_graphics, text_timer_graphics);
		Color text_alt = new Color(text_timer_graphics, 230, text_timer_graphics);

		if (show_controlls1 == true) {
			int n = 1;
			g.setFont(text_header);
			g.setColor(text_alt);
			g.drawString("CONTROLS  ", 650, 100);
			g.setFont(text_body);
			g.drawString("1] W A S D to move", 650, 130);
			g.drawString("2] Arrow Keys to shoot", 650, 160);

			g.setFont(text_header);
			g.setColor(Color.white);
			g.drawString("CONTROLS  ", 650 + n, 100);
			g.setFont(text_body);
			g.drawString("1] W A S D to move", 650 + n, 130);
			g.drawString("2] Arrow Keys to shoot", 650 + n, 160);

			/*
			 * Font fnt = new Font("Arial Unicode MS", 1, 30); g.setFont(fnt);
			 * 
			 * g.setColor(Color.blue); g.drawString("    [W]    ", ((Game.WIDTH / 2) - 101 +
			 * 1), ((Game.HEIGHT / 2) - 82)); g.drawString("[A]     [D]", ((Game.WIDTH / 2)
			 * - 103 + 1), ((Game.HEIGHT / 2) - 30)); g.drawString("    [S]    ",
			 * ((Game.WIDTH / 2) - 97 + 1), ((Game.HEIGHT / 2) + 22));
			 * 
			 * g.setColor(new Color(graphic_timer, graphic_timer, graphic_timer));
			 * g.drawString("    [W]    ", ((Game.WIDTH / 2) - 101), ((Game.HEIGHT / 2) -
			 * 82)); g.drawString("[A]     [D]", ((Game.WIDTH / 2) - 103), ((Game.HEIGHT /
			 * 2) - 30)); g.drawString("    [S]    ", ((Game.WIDTH / 2) - 97), ((Game.HEIGHT
			 * / 2) + 22));
			 * 
			 */
			// } else if (show_controlls2 == true) {
			/*
			 * Font fnt = new Font("Arial Unicode MS", 1, 30); g.setFont(fnt); int n = 270;
			 * int n2 = 250;
			 * 
			 * g.setColor(Color.blue); g.drawString("USE THE ARROW", n + 51, n2 + 50);
			 * g.drawString("KEYS TO SHOOT", n + 56, n2 + 80); g.setColor(new
			 * Color(graphic_timer, graphic_timer, graphic_timer));
			 * g.drawString("USE THE ARROW", n + 50, n2 + 50); g.drawString("KEYS TO SHOOT",
			 * n + 55, n2 + 80);
			 */
			// }

			// "Water-Mark"
			/*
			 * Font fnt = new Font("Courier New", 2, 23); g.setFont(fnt);
			 * g.setColor(Color.blue); g.drawString("TECH DEMO: Version 1.3", 14,
			 * Game.HEIGHT - 50); g.setFont(fnt); g.setColor(new Color(graphic_timer,
			 * graphic_timer, graphic_timer)); g.drawString("TECH DEMO: Version 1.3", 15,
			 * Game.HEIGHT - 50);
			 */
		}
	}

	public void boarder() {
		// Top
		int n = 12;
		for (int i = 0; i < Game.WIDTH / n; i++) {
			handler.addObject(new Wall(0 + (i * n), 0, ID_System.Wall, handler, n));
		} // Bottom
		for (int i2 = 0; i2 < Game.WIDTH / n; i2++) {
			handler.addObject(new Wall(0 + (i2 * n), (Game.HEIGHT - 45), ID_System.Wall, handler, n));
		}

		int n2 = 12;
		// Left
		for (int i3 = 0; i3 < (Game.HEIGHT / n2) - 2; i3++) {
			handler.addObject(new Wall(0, 0 + (i3 * n2), ID_System.Wall, handler, n2));
		}
		// Right
		for (int i4 = 0; i4 < (Game.HEIGHT / n2) - 2; i4++) {
			handler.addObject(new Wall((Game.WIDTH - 20), 0 + (i4 * n2), ID_System.Wall, handler, n2));
		}
	}

	public static void delay(int millisecs) {
		try {
			Thread.currentThread().sleep(millisecs);
		} catch (InterruptedException e) {
		}
	} // delay Method

	public boolean hostileScan() {
		boolean AI_found = false;

		for (int i = 0; i < handler.object.size(); i++) {
			Game_Object tempObject = handler.object.get(i);
			if (tempObject.getId() == ID_System.AI) {
				AI_found = true;
				break;
			}
		}

		return AI_found;
	}

	public void resetAll() {
		// AI logic
		AI1S = true;
		AI2S = true;
		AI2S = true;

		// logic
		graphic_timer = 255;

		door_active = true;

		// playerSpawned = false;
		ready_to_spawn_player = false;

		show_controlls1 = true;
		show_controlls2 = false;

		waveNum = 1;
		lastWaveNum = -1;

		playerX = ((Game.WIDTH / 2) - 52);
		playerY = ((Game.HEIGHT / 2) - 52);
		playerHealth = 100;

		spawn1 = true;
		spawn2 = false;
		spawn3 = false;
		spawn4 = false;
		// kills ALL objects
		for (int l = 0; l < handler.object.size(); l++) {
			Game_Object tempObject = handler.object.get(l);
			handler.removeObject(tempObject);
		}

	}
}
