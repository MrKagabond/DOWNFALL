package DOWNFALL;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1550691097823471818L;
	public static final int WIDTH = 980, HEIGHT = ((WIDTH / 12) * 9);
	private int gF, lastLevelPlayed;
	private boolean mouseLogic;
	// Width == 980
	// Height == 735
	boolean debugLogic_FPS = true;
	static boolean TESTING_MODE = false;

	// States
	private Game_Menu menu;
	private Level1 level_1;
	private Level2 level_2;
	private Level3 level_3;
	// private Tech_Demo_Level td;
	private Victory victory;
	private GameOver gameOver;

	// private Key_Input key;

	private Thread thread;
	private boolean running = false;
	// private Random r;
	private Game_Handle handler;

	// Will controll the state of the application
	// ie. will switch from the menu to the game
	public enum STATE {
		Menu, Victory, GameOver, Level1, ResetLevel1, Level2, ResetLevel2, Level3
	};

	// Initalises the state as the menu
	// public STATE gameState = STATE.Menu;
	public STATE gameState = STATE.Menu;

	// The "GAME"
	public Game() {
		handler = new Game_Handle();
		menu = new Game_Menu(this, handler);
		level_1 = new Level1(this, handler);
		level_2 = new Level2(this, handler);
		level_3 = new Level3(this, handler);
		// td = new Tech_Demo_Level(this, handler);
		victory = new Victory(this, handler);
		gameOver = new GameOver(this, handler);

		// will display the FPS on to the JFrame
		gF = 0;

		lastLevelPlayed = 0;

		// this.addMouseListener(menu);
		// this.addMouseListener(victory);
		// this.addMouseListener(gameOver);
		// this.addMouseListener(level_1);

		this.addKeyListener(new Key_Input(handler));

		new Window(WIDTH, HEIGHT, "DOWNFALL: A Joshua Nelson Production ", this);
	}

	// ***
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		if (gameState == STATE.Menu) {
			menu.render(g);
		}

		if (gameState == STATE.Level1) {
			level_1.render(g);
		}

		if (gameState == STATE.Level2) {
			level_2.render(g);
		}
		if (gameState == STATE.Level3) {
			level_3.render(g);
		}

		// if (gameState == STATE.Tech_Demo_Level) {
		// td.render(g);
		// }

		if (gameState == STATE.Victory) {
			victory.render(g);
		}

		if (gameState == STATE.GameOver) {
			gameOver.render(g);
		}

		handler.render(g);

		// Will display the FPS count to the player in a graphical sence on the JFrame
		Font fps = new Font("Arial Unicode MS", 1, 15);
		g.setFont(fps);
		if (gameState != STATE.Level3) {
			if (gF > 60) {
				g.setColor(Color.green);
			} else if (gF > 30) {
				g.setColor(Color.yellow);
			} else {
				g.setColor(Color.red);
			}
			g.drawString("FPS: " + gF, 13, (Game.HEIGHT) - 46);
		}

		g.dispose();
		bs.show();
	}

	// ***
	private void tick() {
		handler.tick();
		for (int i = 0; i < handler.object.size(); i++) {
			Game_Object tempObject = handler.object.get(i);
			if (tempObject.getId() == ID_System.Player_3D || tempObject.getId() == ID_System.Player_Ship) {
				if (tempObject.getHealth() <= 0) {
					gameState = STATE.GameOver;
				}
			}
		}
		if (gameState == STATE.Menu) {
			menu.tick();
			this.addMouseListener(menu);
		}

		if (gameState == STATE.ResetLevel1) {
			level_1.resetAll();
			level_2.resetAll();
			gameState = STATE.Level1;
		}

		if (gameState == STATE.Level1) {
			level_1.tick();
			this.addMouseListener(level_1);
		}

		if (gameState == STATE.ResetLevel2) {
			level_1.resetAll();
			level_2.resetAll();
			gameState = STATE.Level2;
		}

		if (gameState == STATE.Level2) {
			level_2.tick();
			// this.addMouseListener(level_2);
		}

		if (gameState == STATE.Level3) {
			level_3.tick();
			 this.addMouseListener(level_3);
		}

		if (gameState == STATE.Victory) {
			victory.tick();
			this.addMouseListener(victory);
		}
		if (gameState == STATE.GameOver) {
			gameOver.tick();
			this.addMouseListener(gameOver);
		}
	}

	// ***
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	// ***
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ***
	// game loop
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		// 60
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running) {
				render();
			}
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				if (debugLogic_FPS) {
					System.out.println("FPS: " + frames);
					gF = frames;
				}
				frames = 0;
			}
		}
	}

	// ***
	public static float clamp(float var, float min, int max) {
		//
		if (var >= max) {
			return var = max;
		} else if (var <= min) {
			return var = min;
		} else {
			return var;
		}
	}

	public void toggleFPS() {
		if (debugLogic_FPS) {
			setDebugLogic_FPS_F();
		} else {
			setDebugLogic_FPS_T();
		}
	}

	private void setDebugLogic_FPS_T() {
		debugLogic_FPS = true;
	}

	private void setDebugLogic_FPS_F() {
		debugLogic_FPS = false;
	}

	public static void main(String[] args) {
		System.out.println("Console Window\nVersion_1.0");
		// Launches the game
		//
		new Game();
	}

	public static void setTESTING_MODE(boolean TESTING_MODE_NEW) {
		TESTING_MODE = TESTING_MODE_NEW;
	}

	public static boolean getTESTING_MODE() {
		return TESTING_MODE;
	}

	public void saveLevel(int num) {
		lastLevelPlayed = num;
	}

	public int getLevel() {
		return lastLevelPlayed;
	}

	public void setLevel(int lastLevelPlayed) {
		this.lastLevelPlayed = lastLevelPlayed;
	}

}
