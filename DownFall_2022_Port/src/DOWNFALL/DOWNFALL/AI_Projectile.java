package DOWNFALL;
//This class is the projectile that the AI creates when it decides to fire upon the player

//Logic is contained with this class to ensure that if the player encounters and intersects the projectile, the player will lose health

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class AI_Projectile extends Game_Object {

	Game_Handle handler;
	private int lifeTimer, AI_velVal, AI_Damage;
	private Direction_System ds;
	// damageLOGIC; stops a logical error in which the health is constantly reduced
	// when the
	// projectile comes into contact with an object
	private boolean damageLOGIC;

	public AI_Projectile(int x, int y, ID_System id, Game_Handle handler, Direction_System ds) {
		super(x, y, id);
		this.handler = handler;
		this.ds = ds;
		lifeTimer = 0;

		AI_velVal = 0;

		damageLOGIC = true;
	}

	public void tick() {
		for (int i = 0; i < handler.object.size(); i++) {
			Game_Object tempObject = handler.object.get(i);
			if (tempObject.getId() == ID_System.AI) {
				AI_velVal = tempObject.getProjectileSpeed();
				AI_Damage = (int) (tempObject.getDamage());
				if (((lifeTimer * tempObject.getProjectileSpeed()) + tempObject.getProjectileSpeed()) > tempObject
						.getAwareness()) {
					handler.removeObject(this);
				}
			}
			if (tempObject.getId() == ID_System.AI_Ship) {
				AI_velVal = tempObject.getProjectileSpeed();
				AI_Damage = (int) (tempObject.getDamage());
				if (((lifeTimer * tempObject.getProjectileSpeed()) + tempObject.getProjectileSpeed()) > tempObject
						.getAwareness()) {
					handler.removeObject(this);
				}
			}
		}

		// increments timer
		lifeTimer++;

		move();

		// hit wall
		for (int i = 0; i < handler.object.size(); i++) {
			Game_Object tempObject = handler.object.get(i);
			if (tempObject.getId() == ID_System.Wall) {
				if (getBounds().intersects(tempObject.getBounds())) {
					if (tempObject.getHealth() > 0) {
						handler.removeObject(this);
					}
					if (tempObject.getHealth() < 0) {
						handler.removeObject(tempObject);
						handler.removeObject(this);
					}
				}
			}
		}

		// hit player
		for (int l = 0; l < handler.object.size(); l++) {
			Game_Object tempObject = handler.object.get(l);
			if (tempObject.getId() == ID_System.AI_Projectile) {
				for (int i = 0; i < handler.object.size(); i++) {
					Game_Object tempObject2 = handler.object.get(i);
					if (tempObject2.getId() == ID_System.Player_3D) {
						if (getBounds().intersects(tempObject2.getBounds())) {
							// handler.removeObject(this);
							if (damageLOGIC) {
								tempObject2.setHealth(tempObject2.getHealth() - AI_Damage);
								damageLOGIC = false;
							}
						}
					}
				}
			}
		}
		// hit player ship
		for (int l = 0; l < handler.object.size(); l++) {
			Game_Object tempObject = handler.object.get(l);
			if (tempObject.getId() == ID_System.AI_Projectile) {
				for (int i = 0; i < handler.object.size(); i++) {
					Game_Object tempObject2 = handler.object.get(i);
					if (tempObject2.getId() == ID_System.Player_Ship) {
						if (getBounds().intersects(tempObject2.getBounds())) {
							// handler.removeObject(this);
							if (damageLOGIC) {
								tempObject2.setHealth(tempObject2.getHealth() - AI_Damage);
								damageLOGIC = false;
							}
						}
					}
				}
			}
		}
	}

	public void render(Graphics g) {
		if (ds == Direction_System.Left_Up || ds == Direction_System.Left_Down) {
			handler.addObject(new Trail(getX(), getY(), ID_System.AI_Projectile, Color.red, 3, 3, 0.175f, handler));
		} else {
			handler.addObject(new Trail(getX(), getY(), ID_System.AI_Projectile, Color.red, 3, 3, 0.0575f, handler));
		}
		g.setColor(Color.red);
		g.drawRect(x, y, 12, 12);
	}

	public void move() {
		if (ds == Direction_System.Up) {
			setVelY(-AI_velVal);
			y = y + velY;
		}
		if (ds == Direction_System.Down) {
			setVelY(AI_velVal);
			y = y + velY;
		}
		if (ds == Direction_System.Left) {
			setVelX(-AI_velVal);
			x = x + velX;
		}
		if (ds == Direction_System.Left_Up) {
			setVelX(-AI_velVal);
			x = x + velX;

			setVelY(-AI_velVal);
			y = (int) (y + velY / 2);
		}
		if (ds == Direction_System.Left_Down) {
			setVelX(-AI_velVal);
			x = x + velX;

			setVelY(AI_velVal);
			y = (int) (y + velY / 2);
		}
		if (ds == Direction_System.Right) {
			setVelX(AI_velVal);
			x = x + velX;
		}

	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 12, 12);
	}
}
