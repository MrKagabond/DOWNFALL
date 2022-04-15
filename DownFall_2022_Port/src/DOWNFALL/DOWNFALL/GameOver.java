package DOWNFALL;
//The screen that occurs when: the player has 0 health, or the player kills a civilian
//Will allow the player to continue (retry), exit to the menu and or exit the program  

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import DOWNFALL.Game.STATE;
//Note: "Continue" == BUGGED 

public class GameOver extends MouseAdapter {
	private Game game;
	private Game_Handle handler;
	// private int logicL;

	public GameOver(Game game, Game_Handle handler) {
		this.game = game;
		this.handler = handler;
	}

	public void render(Graphics g) {

		Font fnt = new Font("Impact", 3, 60);
		Font fnt2 = new Font("CourierNew", 2, 19);
		Font fnt3 = new Font("Arial", 1, 30);

		g.setColor(Color.black);
		g.fillRect(0, 0, game.WIDTH, game.HEIGHT);
		g.setColor(Color.red);
		g.setFont(fnt);
		g.drawString("GAME OVER", ((game.WIDTH) / 2) - 150, ((game.HEIGHT) / 2) - 50);
		g.setColor(Color.white);
		g.setFont(fnt);
		g.drawString("GAME OVER", ((game.WIDTH) / 2) - 150 + 5, ((game.HEIGHT) / 2) - 50);

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
		g.setColor(Color.red);
		g.drawString("Continue", (game.WIDTH) / 2 - 100 + 34, (game.HEIGHT) / 2 + 40);
		g.drawString("Menu", (game.WIDTH) / 2 - 100 + 55, (game.HEIGHT) / 2 + 140);
		g.drawString("Quit", (game.WIDTH) / 2 - 100 + 65, (game.HEIGHT) / 2 + 240);
	}

	public void tick() {
	}

	public void mousePressed(MouseEvent e) {
		if (game.gameState == STATE.GameOver) {
			int mx = e.getX();
			int my = e.getY();

			// if (getLogicL() == 0) {
			if (mouseOver(mx, my, (game.WIDTH) / 2 - 100, (game.HEIGHT) / 2, 200, 60)) {
				// Get previous game state and set this to the previous
				// game.gameState = STATE.Tech_Demo_Level;
				// game.resetTech_Demo_Level();

				// Since player failed to win, this logic differs from the Victory class...
				if (game.getLevel() == 1) {
					game.gameState = STATE.ResetLevel1;
					game.setLevel(1);
				}

				if (game.getLevel() == 2) {
					game.gameState = STATE.ResetLevel2;
					game.setLevel(2);
				}
			}

			if (mouseOver(mx, my, (game.WIDTH) / 2 - 100, (game.HEIGHT) / 2 + 100, 200, 60)) {
				// buggy
				game.gameState = STATE.Menu;
				// game.resetTech_Demo_Level();
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
