package DOWNFALL;

//A class designed for creating an object that cannot be passed by any other object
//Used within level 2
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.awt.Rectangle;

public class Wall extends Game_Object {

	Random r = new Random();
	private Game game;
	Game_Handle handler;
	private boolean breakable;
	// the dimensions of the wall: length and width
	private int wallVal, timer;

	// Notes: PERHAPS DIVIDE ID "WALLS" INTO "X_WALLS' AND "Y_WALLS"
	// FIX CLAMP

	// Default constructor
	public Wall(int x, int y, ID_System id, Game_Handle handler) {
		super(x, y, id);
		this.handler = handler;

		// graphics: display red
		timer = 200;

		breakable = false;
		// logic
		setX(x);
		setY(y);

		// size of the wall
		wallVal = 50;
	}

	// Special Constructor
	public Wall(int x, int y, ID_System id, Game_Handle handler, int wallVal) {
		super(x, y, id);
		this.handler = handler;

		breakable = false;
		// logic
		setX(x);
		setY(y);

		// size of the wall
		this.wallVal = wallVal;
	}

	// Special Constructor: Breakable Wall
	public Wall(int x, int y, ID_System id, Game_Handle handler, int wallVal, boolean breakable) {
		super(x, y, id);
		this.handler = handler;

		this.breakable = breakable;

		// logic
		setX(x);
		setY(y);

		// size of the wall
		this.wallVal = wallVal;
	}

	public void tick() {

		if (breakable == true) {
			setHealth(-1);
		}

		if (timer <= 1) {
			timer = 200;
		}

		timer = (timer - 5);
	}

	public void render(Graphics g) {
		if (breakable == false) {
			g.setColor(Color.white);
			g.drawRect(x, y, wallVal, wallVal);
		}
		if (breakable == true) {
			g.setColor(new Color(timer, 0, 0));
			g.fillRect(x, y, wallVal, wallVal);
			g.setColor(Color.white);
			g.drawRect(x, y, wallVal, wallVal);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, wallVal, wallVal);
	}
}
