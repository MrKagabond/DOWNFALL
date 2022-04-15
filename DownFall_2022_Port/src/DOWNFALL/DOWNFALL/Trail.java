package DOWNFALL;
//Note: NOT MY CODE!

//Adds a cool particle effect to projectiles and stars 

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Trail extends Game_Object {

	private float alpha = 1;
	private Game_Handle handler;
	private Color color;
	private int width, height;
	private float life;

	// life = 0.01 - 0.1

	public Trail(int x, int y, ID_System id, Color color, int width, int height, float life, Game_Handle handler) {
		super(x, y, id);
		this.handler = handler;
		this.color = color;
		this.width = width;
		this.height = height;
		this.life = life;
		this.id = id;
	}

	public void tick() {
		if (alpha > life) {
			alpha -= (life - 0.0001f);
		} else
			handler.removeObject(this);
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransparent(alpha));
		g.setColor(color);
		g.fillRect((int) x, (int) y, width, height);

		if (id == ID_System.Projectile) {
			g.setColor(Color.green);
			g.fillRect(x, y, 13, 13);
		}
		if (id == ID_System.AI_Projectile) {
			g.setColor(Color.red);
			g.fillRect(x, y, 13, 13);
		}
		if (id == ID_System.Star) {
			g.setColor(Color.white);
			g.fillOval(x, y, width, height);
		}

		g2d.setComposite(makeTransparent(1));
	}

	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));
	}

	public Rectangle getBounds() {
		return null;
	}

}
