package DOWNFALL;
//This contains the information/data required for the final level
//This class will spawn in assets and take care of appropriate logic t ensure that the game works as intended

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import DOWNFALL.Game.STATE;

public class Level3 extends MouseAdapter {

	private Game game;
	private Game_Handle handler;
	private boolean logic_N;
	private int x, y;

	Level3(Game game, Game_Handle handler) {
		this.game = game;
		this.handler = handler;

		logic_N = true;
		x = 0;
		y = 0;
	}

	public void tick() {
		if (-515 < y) {
			y = y - 5;
		} else {
			logic_N = false;
		}
		// handler.addObject(new Player_Projectile((((Game.WIDTH / 2) - (49)) + x + 10),
		// (650 + y),
		// ID_System.ProjectileS, handler, Direction_System.Up));

	}

	public void render(Graphics g) {
		Font text_header = new Font("MingLiU_HKSCS", 3, 30);
		Font text_body = new Font("MingLiU_HKSCS", 1, 20);

		int n = 12;
		for (int i = 0; i < Game.WIDTH / n; i++) {
			g.setColor(Color.white);
			g.drawRect(0 + (i * n), 0, n, n);
		}
		for (int i2 = 0; i2 < Game.WIDTH / n; i2++) {
			g.setColor(Color.white);
			g.drawRect(0 + (i2 * n), (Game.HEIGHT - 45), n, n);
		}

		int n2 = 12;
		for (int i3 = 0; i3 < (Game.HEIGHT / n2) - 2; i3++) {
			g.setColor(Color.white);
			g.drawRect(0, 0 + (i3 * n2), n2, n2);
		}

		for (int i4 = 0; i4 < (Game.HEIGHT / n2) - 2; i4++) {
			g.setColor(Color.white);
			g.drawRect((Game.WIDTH - 20), 0 + (i4 * n2), n2, n2);
		}
		// player
		g.setColor(Color.white);
		g.fillRect(((Game.WIDTH / 2) - (49)) + x, 650 + y, 32, 32);
		g.setColor(Color.green);
		g.drawRect(((Game.WIDTH / 2) - (49)) + x, 650 + y, 32, 32);

		// Door
		g.setColor(Color.gray);
		g.fillRect(((Game.WIDTH / 2) - (32 * 5) + 42), (Game.HEIGHT - 65), (32 * 5) + 8, 32);
		g.setColor(Color.GREEN);
		g.drawRect(((Game.WIDTH / 2) - (32 * 5) + 42), (Game.HEIGHT - 65), (32 * 5) + 8, 32);

		// Object
		Random randomGenerator = new Random();
		int n11 = (int) Math.random() * 254 + 1;
		int n21 = randomGenerator.nextInt(255);
		int n31 = randomGenerator.nextInt(255);
		g.setColor(new Color(n11, n21, n31));
		g.setColor(new Color(202, 15, 183));
		g.fillRect(((Game.WIDTH / 2) - (49)) + x, 100, 32, 32);
		g.setColor(new Color(n11, n21, n31));
		g.drawRect(((Game.WIDTH / 2) - (49)) + x - 1, 100 - 1, 33, 33);
		g.drawRect(((Game.WIDTH / 2) - (49)) + x, 100, 31, 31);

		//
		if (logic_N != true) {
			g.setColor(Color.black);
			g.fillRect(0, 0, game.WIDTH, game.HEIGHT);

			g.setFont(text_header);
			g.setColor(Color.white);
			g.drawString("Mission Complete", 345, 55);

			g.setFont(text_body);
			g.drawString("With the enemy’s primary mother ship’s core disabled, the federation may still have ", 30,
					130);
			g.drawString("hope yet. It has been unfortunate, our situation over the last seven years. War, ", 30, 160);
			g.drawString("famine and corruption have taken the lives of many of our soldiers and civilians. It  ", 30,
					190);
			g.drawString("is obvious that we can not undo the tragic damage that has erupted on our planet. ", 30, 220);
			//
			g.drawString("But despite this, you have managed to persevere in our darkest hour. Even though what  ", 30,
					320);
			g.drawString("we lost can never be replaced, at least something new can be forged from the carnage  ", 30,
					350);
			g.drawString("that has occurred on Mother Earth.  ", 30, 380);
			//
			g.drawString("The symbolic meaning of hope, as we know it has been restored this day due to your  ", 30,
					480);
			g.drawString("courageous actions. On behalf of the galactic council we thank you for your services   ", 30,
					510);
			g.drawString("and contributions to the cause.   ", 30, 540);

			g.drawString("CLICK TO CONTINUE…  ", 30, 640);

		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	private boolean mouseOver(int mx, int my, int x, int y, int w, int h) {
		if (game.gameState == STATE.Level3) {
			if (mx > x && mx < x + w) {
				if (my > y && my < y + h) {
					return true;
				}
			}
		}
		return false;
	}

	public void mousePressed(MouseEvent e) {
		if (game.gameState == STATE.Level3) {
			int mx = e.getX();
			int my = e.getY();

			// if (textNum <= 2) {
			if (mouseOver(mx, my, 0, 0, (game.WIDTH), (game.HEIGHT))) {
				if (logic_N == false) {
					game.gameState = STATE.Menu;
				}
			}
			// }
		}
	}

}