package DOWNFALL;
//Not actually used anywhere else
//A concept that was theorized for the AI class

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class logicProjectile extends Game_Object {

	Game_Handle handler;
	private int lifeTimer, life, velVal;
	private Direction_System ds;
	private boolean hitWall;

	public logicProjectile(int x, int y, ID_System id, Game_Handle handler, Direction_System ds) {
		super(x, y, id);
		this.handler = handler;
		this.ds = ds;
		lifeTimer = 0;
		life = 5;

		// speed of projectile
		velVal = 51;
		hitWall = false;
	}

	public void tick() {
		lifeTimer++;
		if (lifeTimer > life) {
			if (shotApproved() == true) {
				handler.addObject(
						new logicProjectile((getX() + 10), getY(), ID_System.Projectile, handler, Direction_System.Up));
			}

			handler.removeObject(this);
		}
		move();

		// hit wall
		for (int i = 0; i < handler.object.size(); i++) {
			Game_Object tempObject = handler.object.get(i);
			if (tempObject.getId() == ID_System.Wall) {
				if (getBounds().intersects(tempObject.getBounds())) {
					hitWall = true;
					// DO not need to remove object here
					// handler.removeObject(this);
				}
			}
		}
	}

	public void render(Graphics g) {
		if (Game.getTESTING_MODE() == true) {
			if (id == ID_System.AI_Projectile) {
				g.setColor(Color.yellow);
				g.fillRect(x, y, 12, 12);
			}
		}
	}

	public void move() {
		if (ds == Direction_System.Up) {
			setVelY(-velVal);
			y = y + velY;
		}
		if (ds == Direction_System.Down) {
			setVelY(velVal);
			y = y + velY;
		}
		if (ds == Direction_System.Left) {
			setVelX(-velVal);
			x = x + velX;
		}

		if (ds == Direction_System.Left_Up) {
			setVelX(-velVal);
			x = x + velX;
			setVelY(-velVal);
			y = y + velY;
		}

		if (ds == Direction_System.Left_Down) {
			setVelX(-velVal);
			x = x + velX;
			setVelY(velVal);
			y = y + velY;
		}
		if (ds == Direction_System.Right) {
			setVelX(velVal);
			x = x + velX;
		}
	}

	public void setSpeed(int velVal) {
		this.velVal = velVal;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 12, 12);
	}

	public boolean shotApproved() {
		if (hitWall == false) {
			return true;
		} else {
			return false;
		}
	}
}
