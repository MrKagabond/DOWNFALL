package DOWNFALL;
//Not actually used anywhere else (was used in the tech_demo)
//A concept that was theorized for the Menu before the star class was developed

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Menu_Object extends Game_Object {
	private Game_Handle handler;
	int currentWave;

	public Menu_Object(int x, int y, ID_System id, Game_Handle handler) {
		super(x, y, id);

		this.handler = handler;

		int xx = 0;
		int yy = 7;

		velX = xx;
		velY = yy;
	}
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 16, 16);
	}

	@Override
	public void tick() {
		x += velX;

		y += velY;

		if (y <= 0 || y >= Game.HEIGHT - 32) {
			velY *= -1;
		}
		if (x <= 0 || x >= Game.WIDTH - 16) {
			velX *= -1;
		}

		handler.addObject(new Trail((int) x, (int) y, ID_System.Projectile, Color.blue, 16, 16, 0.0875f, handler));
	}

	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect((int) x-2, (int) y, 16, 16);

	}

}