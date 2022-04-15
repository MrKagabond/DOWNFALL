package DOWNFALL;
//This class is the projectile that the KeyInput creates when the player decides to fire

//Logic is contained with this class to ensure that if any AI encounters and intersects the projectile, the AI will lose health

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player_Projectile extends Game_Object {

	Game_Handle handler;
	private int lifeTimer, player_velVal, playerDamage;
	private Direction_System ds;
	// damageLOGIC; stops a logical error in which the health is constantly reduced
	// when the
	// projectile comes into contact with an object
	private boolean damageLOGIC, hit;

	public Player_Projectile(int x, int y, ID_System id, Game_Handle handler, Direction_System ds) {
		super(x, y, id);
		this.handler = handler;
		this.ds = ds;
		lifeTimer = 0;

		player_velVal = 0;

		hit = false;
		damageLOGIC = true;
	}

	public void tick() {
		for (int i = 0; i < handler.object.size(); i++) {
			Game_Object tempObject = handler.object.get(i);
			if (tempObject.getId() == ID_System.Player_3D) {
				// variable decloration
				player_velVal = tempObject.getProjectileSpeed();
				playerDamage = (int) (tempObject.getDamage());
				if (((lifeTimer * tempObject.getProjectileSpeed()) + tempObject.getProjectileSpeed()) > tempObject
						.getAwareness()) {
					handler.removeObject(this);
				}
			}
			if (tempObject.getId() == ID_System.Player_Ship) {
				// variable decloration
				// to fix an error....
				setProjectileSpeed(30);
				player_velVal = tempObject.getProjectileSpeed();
				playerDamage = (int) (tempObject.getDamage());
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

		// hit AI
		for (int l = 0; l < handler.object.size(); l++) {
			Game_Object tempObject = handler.object.get(l);
			if (tempObject.getId() == ID_System.Projectile) {
				for (int i = 0; i < handler.object.size(); i++) {
					Game_Object tempObject2 = handler.object.get(i);
					if (tempObject2.getId() == ID_System.AI) {
						if (getBounds().intersects(tempObject2.getBounds())) {
							// handler.removeObject(this);
							if (damageLOGIC) {
								if (hit == false) {
									tempObject2.setHealth(tempObject2.getHealth() - playerDamage);
									damageLOGIC = false;
									hit = true;
								}
							}
						}
					}
				}
			}
		}

		// hit AI Ship
		for (int l = 0; l < handler.object.size(); l++) {
			Game_Object tempObject = handler.object.get(l);
			if (tempObject.getId() == ID_System.Projectile) {
				for (int i = 0; i < handler.object.size(); i++) {
					Game_Object tempObject2 = handler.object.get(i);
					if (tempObject2.getId() == ID_System.AI_Ship) {
						if (getBounds().intersects(tempObject2.getBounds())) {
							// handler.removeObject(this);
							if (damageLOGIC) {
								if (hit == false) {
									tempObject2.setHealth(tempObject2.getHealth() - playerDamage);
									damageLOGIC = false;
									hit = true;
								}
							}
						}
					}
				}
			}
		}
	}

	public void render(Graphics g) {
		if (id == ID_System.Projectile) {
			handler.addObject(new Trail(getX(), getY(), ID_System.Projectile, Color.green, 3, 3, 0.0575f, handler));
			g.setColor(Color.green);
			g.drawRect(x, y, 12, 12);
		}
	}

	public void move() {
		if (ds == Direction_System.Up) {
			setVelY(-player_velVal);
			y = y + velY;
		}
		if (ds == Direction_System.Down) {
			setVelY(player_velVal);
			y = y + velY;
		}
		if (ds == Direction_System.Left) {
			setVelX(-player_velVal);
			x = x + velX;
		}
		if (ds == Direction_System.Right) {
			setVelX(player_velVal);
			x = x + velX;
		}
		// if (id == ID_System.ProjectileS) {
		// y = y + 5;
		// }
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 12, 12);
	}
}
