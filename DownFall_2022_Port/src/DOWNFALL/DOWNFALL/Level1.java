package DOWNFALL;
//This contains the information/data required for the first level
//This class will spawn in assets and take care of appropriate logic t ensure that the game works as intended

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import DOWNFALL.Game.STATE;

public class Level1 extends MouseAdapter {

	private Game game;
	private Game_Handle handler;

	private int text_timer, waveNum, starTimer, text_timer_graphics, textNum;
	private boolean text_on_CD, waveReady, playerSpawned, text1Render, text2Render, text3Render, stars_spawn_ready;

	// levelwaveNum == ""origin Map""

	Level1(Game game, Game_Handle handler) {
		this.game = game;
		this.handler = handler;

		starTimer = 0;
		playerSpawned = false;
		stars_spawn_ready = true;

		// text
		textNum = 0;
		text1Render = false;
		text2Render = false;
		text3Render = false;

		// error solution
		text_on_CD = false;
		text_timer = 0;
		text_timer_graphics = 0;

		waveNum = 0;
		waveReady = false;
	}

	public void tick() {
		// System.out.println("textNum: " + textNum + "\ntext_on_CD: " + text_on_CD +
		// "\ntext_timer: " + text_timer);

		textUpdate();
		add_stars();

		if (playerSpawned == false) {
			playerSpawned = true;
			handler.addObject(new Player_Space_Ship(50, -175, ID_System.Player_Ship, handler));
		}
		if (waveReady) {
			levelWaveLogic();
		}

		if (text_on_CD == true) {
			text_timer++;
		}
		if (text_timer > 50) {
			text_on_CD = false;
			text_timer = 0;
		}
	}

	public void lose() {
		// code works

		// logic
		waveNum = -1;

		// ends game
		game.gameState = STATE.GameOver;

	}

	public void levelWaveLogic() {
		// Responsible for the logic behind this level's "wave" mechanic
		// Note: Each time an AI ship dies, the wave number is incremented
		//System.out.println("waveNum: " + waveNum);
		if (waveNum == 0) {
			handler.addObject(new AI_Space_Ship(325, 50, ID_System.AI_Ship, handler, AI_Behavoir.Civilian, this));
			waveNum++;
		}
		// if (waveNum == 0) {
		// handler.addObject(new AI_Space_Ship(725, -250, ID_System.AI_Ship, handler,
		// AI_Behavoir.Coward, this));
		// waveNum++;
		// }
		if (waveNum == 1) {
			// null
		}
		if (waveNum == 2) {
			handler.addObject(new AI_Space_Ship(-275, -250, ID_System.AI_Ship, handler, AI_Behavoir.Coward, this));
			waveNum++;
		}
		if (waveNum == 3) {
			// null
		}
		if (waveNum == 4) {
			handler.addObject(new AI_Space_Ship(725, -250, ID_System.AI_Ship, handler, AI_Behavoir.Attacker, this));
			waveNum++;
		}
		if (waveNum == 5) {
			// null
		}
		if (waveNum == 6) {
			handler.addObject(new AI_Space_Ship(125, 50, ID_System.AI_Ship, handler, AI_Behavoir.Civilian, this));
			waveNum++;
		}
		if (waveNum == 7) {
			// null
		}
		if (waveNum == 8) {
			handler.addObject(new AI_Space_Ship(-275, -250, ID_System.AI_Ship, handler, AI_Behavoir.Attacker, this));
			waveNum++;
		}
		if (waveNum == 9) {
			// null
		}
		if (waveNum == 10) {
			handler.addObject(new AI_Space_Ship(-275, -250, ID_System.AI_Ship, handler, AI_Behavoir.Coward, this));
			waveNum++;
		}
		if (waveNum == 11) {
			// null
		}
		if (waveNum == 12) {
			// section complete
			nextText();
			waveNum++;
		}

	}

	public void nextWave() {
		waveNum++;
	}

	public void add_stars() {
		starTimer++;

		if (stars_spawn_ready == true) {
			stars_spawn_ready = false;

			for (int l = 0; l < 35; l++) {
				Random rn = new Random();
				int n = (Game.HEIGHT) - 5 + 1;
				int i = rn.nextInt() & n;
				int num = 1 + i;
				handler.addObject(new Star(Game.WIDTH + num, num, ID_System.Star, handler));
			}
		}
	}

	public void render(Graphics g) {
		Font text_header = new Font("MingLiU_HKSCS", 3, 30);
		Font text_body = new Font("MingLiU_HKSCS", 1, 20);
		Color text = new Color(text_timer_graphics, text_timer_graphics, text_timer_graphics);
		Color text_alt = new Color(text_timer_graphics, 230, text_timer_graphics);
		if (text1Render == true) {
			int n = 1;
			g.setFont(text_header);
			g.setColor(text_alt);
			g.drawString("MISSION OBJECTIVES ", 300, 100);
			g.setFont(text_body);
			g.drawString("1] Infiltrate the hostile mothership ", 300, 130);
			g.drawString("2] Avoid obliterating all fleeing civilian vessels ", 300, 160);
			g.drawString("3] Eliminate all hostile forces ", 300, 190);

			g.setFont(text_header);
			g.setColor(Color.white);
			g.drawString("MISSION OBJECTIVES ", 300 + n, 100);
			g.setFont(text_body);
			g.drawString("1] Infiltrate the hostile mothership ", 300 + n, 130);
			g.drawString("2] Avoid obliterating all fleeing civilian vessels ", 300 + n, 160);
			g.drawString("3] Eliminate all hostile forces ", 300 + n, 190);

			g.setColor(text);
			g.drawString("CLICK TO CONTINUE...", 300 + n, 220);

			// timer logic
			if (text_timer_graphics < 255) {
				text_timer_graphics++;
			} else {
				text_timer_graphics = 0;
			}
		}
		if (text2Render == true) {
			int n = 1;
			g.setFont(text_header);
			g.setColor(text_alt);
			g.drawString("CONTROLS  ", 300, 100);
			g.setFont(text_body);
			g.drawString("W: Increase altitude ", 300, 130);
			g.drawString("S: Decrease altitude ", 300, 160);
			g.drawString("SPACE: Fire dual laser cannons  ", 300, 190);

			g.setFont(text_header);
			g.setColor(Color.white);
			g.drawString("CONTROLS  ", 300 + n, 100);
			g.setFont(text_body);
			g.drawString("W: Increase altitude ", 300 + n, 130);
			g.drawString("S: Decrease altitude ", 300 + n, 160);
			g.drawString("SPACE: Fire dual laser cannons  ", 300 + n, 190);

			g.setColor(text);
			g.drawString("CLICK TO CONTINUE...", 300 + n, 220);

			// timer logic
			if (text_timer_graphics < 255) {
				text_timer_graphics++;
			} else {
				text_timer_graphics = 0;
			}
		}
		if (text3Render == true) {
			int n = 1;
			g.setFont(text_header);
			g.setColor(text_alt);
			g.drawString("ARRIVED AT DESTINATION ", 300, 100);
			g.setFont(text_body);
			g.drawString("In-range of hostile mothership ", 300, 130);

			g.setFont(text_header);
			g.setColor(Color.white);
			g.drawString("ARRIVED AT DESTINATION ", 300 + n, 100);
			g.setFont(text_body);
			g.drawString("In-range of hostile mothership ", 300 + n, 130);

			g.setColor(text);
			g.drawString("CLICK TO CONTINUE...", 300 + n, 160);

			// timer logic
			if (text_timer_graphics < 255) {
				text_timer_graphics++;
			} else {
				text_timer_graphics = 0;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	private boolean mouseOver(int mx, int my, int x, int y, int w, int h) {
		if (game.gameState == STATE.Level1) {
			if (mx > x && mx < x + w) {
				if (my > y && my < y + h) {
					return true;
				}
			}
		}
		return false;
	}

	public void mousePressed(MouseEvent e) {
		if (game.gameState == STATE.Level1) {
			int mx = e.getX();
			int my = e.getY();

			// if (textNum <= 2) {
			if (mouseOver(mx, my, 0, 0, (game.WIDTH), (game.HEIGHT))) {
				if (text_on_CD == false) {
					text_on_CD = true;
					nextText();
				}
			}
			// }
		}
	}

	public void nextText() {
		textNum++;
	}

	public void textUpdate() {
		if (textNum == 0) {
			text1Render = true;
			text2Render = false;
			text3Render = false;
		}
		if (textNum == 1) {
			text1Render = false;
			text2Render = true;
			text3Render = false;
		}
		if (textNum == 2) {
			text1Render = false;
			text2Render = false;
			text3Render = false;
			// ready to begin starting waves...
			waveReady = true;
		}
		if (textNum == 3) {
			text1Render = false;
			text2Render = false;
			text3Render = true;
		}
		if (textNum > 3) {
			game.gameState = STATE.Victory;
		}
	}

	public void resetAll() {
		while (handler.object.size() > 1) {
			starTimer = 0;
			playerSpawned = false;
			stars_spawn_ready = true;

			// text
			textNum = 0;
			text1Render = false;
			text2Render = false;
			text3Render = false;

			// error solution
			text_on_CD = false;
			text_timer = 0;
			text_timer_graphics = 0;

			waveNum = 0;
			waveReady = false;

			// kills ALL objects
			for (int l = 0; l < handler.object.size(); l++) {
				Game_Object tempObject = handler.object.get(l);
				handler.removeObject(tempObject);
			}
		}
	}
}
