package DOWNFALL;
//This class is essentially a door into the next (or previous) room
//Used in the second level for progression purposes
//Can not be used/activated if there are any hostile AI’s within the room

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Door extends Game_Object {
	Game_Handle handler;
	Level2 level;

	private boolean l;

	public Door(int x, int y, ID_System id, Game_Handle handler, Level2 level) {
		super(x, y, id);
		this.handler = handler;
		this.level = level;
		l = false;
	}

	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), (32 * 5), 32);
	}

	public void tick() {
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Random randomGenerator = new Random();
		int n1 = (int) Math.random() * 254 + 1;
		int n2 = randomGenerator.nextInt(255);
		int n3 = randomGenerator.nextInt(255);
		// g.setColor(new Color(n1, n2, n3));
		g2d.draw(getBounds());
		g.setColor(Color.gray);
		g.fillRect((int) x, (int) y, (32 * 5) + 8, 32);

		if (level.getDoorActive() == true) {
			g.setColor(Color.GREEN);
			// g.setColor(new Color(n1, n2, n3));
			g.drawRect(x, y, (32 * 5) + 8, 32);
			g.drawRect(x, y, (32 * 5) + 8, 32);
		} else {
			g.setColor(Color.red);
			g.drawRect(x, y, (32 * 5) + 8, 32);
			g.drawRect(x, y, (32 * 5) + 8, 32);
		}
	}

}
