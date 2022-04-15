package DOWNFALL;
//The menu for the game

//Allows “passage” to the first and second level via the mouse
//Can also close/quit/exit the program 

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import DOWNFALL.Game.STATE;
//Note: "Continue" == BUGGED 

public class Game_Menu extends MouseAdapter {
	private Game game;
	private Game_Handle handler;
	private int starTimer;
	private boolean stars_spawn_ready;

	public Game_Menu(Game game, Game_Handle handler) {
		this.game = game;
		this.handler = handler;

		// Star Logic
		starTimer = 0;
		stars_spawn_ready = true;
	}

	public void add_stars() {
		starTimer++;

		if (stars_spawn_ready == true) {
			stars_spawn_ready = false;

			for (int l = 0; l < 47; l++) {
				Random rn = new Random();
				int n = (Game.HEIGHT) - 5 + 1;
				int i = rn.nextInt() & n;
				int num = 1 + i;
				handler.addObject(new Star(Game.WIDTH + num, num, ID_System.Star, handler));
			}
		}
	}

	public void render(Graphics g) {

		Font fnt = new Font("Impact", 3, 60);
		Font fnt2 = new Font("CourierNew", 2, 19);
		Font fnt3 = new Font("Arial", 1, 30);

		g.setColor(Color.black);
		g.fillRect(0, 0, game.WIDTH, game.HEIGHT);
		g.setColor(Color.blue);
		g.setFont(fnt);
		g.drawString("DOWNFALL", ((game.WIDTH) / 2) - 150, ((game.HEIGHT) / 2) - 50);
		g.setColor(Color.white);
		g.setFont(fnt);
		g.drawString("DOWNFALL", ((game.WIDTH) / 2) - 150 + 5, ((game.HEIGHT) / 2) - 50);

		g.setFont(fnt2);
		g.setColor(Color.white);
		// g.drawString("YOU DIDN'T MAKE IT BACK...", ((game.WIDTH) / 2) - 135 + 5,
		// ((game.HEIGHT) / 2) - 30);

		g.setColor(Color.white);
		g.drawRect(5, 5, (game.WIDTH) - 17, (game.HEIGHT) - 40);

		g.setColor(Color.white);
		g.drawRect((game.WIDTH) / 2 - 100, (game.HEIGHT) / 2, 200, 60);
		g.drawRect((game.WIDTH) / 2 - 100, (game.HEIGHT) / 2 + 100, 200, 60);
		g.drawRect((game.WIDTH) / 2 - 100, (game.HEIGHT) / 2 + 200, 200, 60);

		g.setFont(fnt3);
		g.setColor(Color.blue);
		g.drawString("Level 1", (game.WIDTH) / 2 - 100 + 51, (game.HEIGHT) / 2 + 40);
		g.drawString("Level 2", (game.WIDTH) / 2 - 100 + 51, (game.HEIGHT) / 2 + 140);
		g.drawString("Quit", (game.WIDTH) / 2 - 100 + 67, (game.HEIGHT) / 2 + 240);
	}

	public void tick() {
		add_stars();
	}

	public void mousePressed(MouseEvent e) {
		if (game.gameState == STATE.Menu) {
			int mx = e.getX();
			int my = e.getY();

			// if (getLogicL() == 0) {
			if (mouseOver(mx, my, (game.WIDTH) / 2 - 100, (game.HEIGHT) / 2, 200, 60)) {
				game.gameState = STATE.ResetLevel1;
				// save value to game
				game.saveLevel(1);
			}

			if (mouseOver(mx, my, (game.WIDTH) / 2 - 100, (game.HEIGHT) / 2 + 100, 200, 60)) {
				game.gameState = STATE.ResetLevel2;
				// save value to game
				game.saveLevel(2);
			}

			if (mouseOver(mx, my, (game.WIDTH) / 2 - 100, (game.HEIGHT) / 2 + 200, 200, 60)) {
				System.exit(0);
			}

		}
	}

	// else if (getLogicL() == 1) {
	// if (mouseOver(mx, my, 0, 0, 640, 480)) {
	// game.gameState = STATE.Tech_Demo_Level;
	// }
	// }
	// }

	public void mouseReleased(MouseEvent e) {
	}

	private boolean mouseOver(int mx, int my, int x, int y, int w, int h) {
		if (mx > x && mx < x + w) {
			if (my > y && my < y + h) {
				return true;
			}
		}
		return false;
	}

	// public void setLogicL(int logicL) {
	// this.logicL = logicL;
	// }

	// public int getLogicL() {
	// return logicL;
	// }
}
