package DOWNFALL;
//The class responsible for creating stars onto the screen
//Uses random values to constantly change appearance and speed

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Star extends Game_Object {

	private Game_Handle handler;
	int randomNum, randomNum2, randomNum_y, speed;

	public Star(int x, int y, ID_System id, Game_Handle handler) {
		super(x, y, id);
		this.handler = handler;

		// num = (int) (Math.random() * 1) + 10;
		speed = (int) (Math.random() * 1) + 8;

		Random rn = new Random();
		int n = 4 - 1 + 1;
		int i = rn.nextInt() & n;
		randomNum = 1 + i;

		Random rn2 = new Random();
		int n2 = 9 - 2 + 1;
		int i2 = rn2.nextInt() & n2;
		randomNum2 = 1 + i2;
		speed = randomNum2;

		randomNum_y = y;

		// Random Sample
		/*
		 * Random rn = new Random(); int n = maximum - minimum + 1; int i = rn.nextInt()
		 * % n; randomNum = minimum + i;
		 */}

	public void tick() {
		x = x - speed;

		if (x < 0) {

			Random rn3 = new Random();
			int n3 = (Game.HEIGHT) - 0 + 1;
			int i3 = rn3.nextInt() & n3;
			randomNum_y = 1 + i3;

			Random rn4 = new Random();
			int n4 = 9 - 2 + 1;
			int i4 = rn4.nextInt() & n4;
			randomNum2 = 1 + i4;
			speed = randomNum2;
			x = Game.WIDTH;
		}

	}

	public void render(Graphics g) {
		// added trail effect
		handler.addObject(
				new Trail(x, randomNum_y, ID_System.Star, Color.white, randomNum, randomNum, 0.975f, handler));

		// complicates code: used for depth
		/*
		 * for (int i = 0; i < handler.object.size(); i++) { Game_Object tempObject =
		 * handler.object.get(i); if (tempObject.getId() == ID_System.Player_Ship) { if
		 * (!(getBounds().intersects(tempObject.getBounds()))) { } else {
		 * g.setColor(Color.white); g.fillOval(x, randomNum_y, randomNum, randomNum); }
		 * } }
		 */

		// simple variation
		g.setColor(Color.white);
		g.fillOval(x, randomNum_y, randomNum, randomNum);

	}

	public Rectangle getBounds() {
		return new Rectangle(x, randomNum_y, randomNum, randomNum);
	}

}
