package DOWNFALL;
//The parent class to EVERYTHING!
//Contains ALL essential data, such as positions, id’s and STATS 

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Game_Object {

	// abstract class
	// players, enemies, everything!
	// will carry all functions!

	protected int x, y;
	protected ID_System id;
	protected int velX, velY;
	private boolean gunReady;
	// STATS
	private int awareness, combatFrequency, health, speed, projectileSpeed, damage;

	public Game_Object(int x, int y, ID_System id) {
		this.x = x;
		this.y = y;
		this.id = id;

		// is the gun ready to be fired?
		gunReady = true;

		// start of: "STATS"
		// Note: The following are "BASE" statistical values

		// 1] STAT: Awareness
		// the length (or width) of the AI's "scan BOX"
		// the range of the player's attacks
		awareness = 450;

		// 2] STAT: Attack Frequency
		// the greater the timer, the longer the intervals in which the object is able
		// to attack
		combatFrequency = 45;

		// 3] STAT: AI_HP
		// the greater the value, the more hits the object is able to take
		health = 100;

		// 4] STAT: Movement Speed
		// the greater the speed, the faster the object is able to move
		speed = 3;

		// 5] STAT: Projectile Speed
		// the greater the speed, the faster the object's projectile is able to move
		projectileSpeed = 11;

		// 6] STAT: Projectile Damage
		// the greater the damage, the more health is removed from the target
		damage = 20;

		// end of: "STATS"
	}

	// STAT methods

	public int getAwareness() {
		return awareness;
	}

	public void setAwareness(int awareness) {
		this.awareness = awareness;
	}

	public int getCombatFrequency() {
		return combatFrequency;
	}

	public void setCombatFrequency(int combatFrequency) {
		this.combatFrequency = combatFrequency;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getProjectileSpeed() {
		return projectileSpeed;
	}

	public void setProjectileSpeed(int projectileSpeed) {
		this.speed = projectileSpeed;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public abstract Rectangle getBounds();

	public abstract void tick();

	public abstract void render(Graphics g);

	// collision detection
	// public abstract Rectangle getBounds();

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public void setId(ID_System id) {
		this.id = id;
	}

	public ID_System getId() {
		return id;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getVelX(int velX) {
		return velX;
	}

	public int getVelY(int velY) {
		return velY;
	}

	public boolean getGunReady() {
		return gunReady;
	}

	public void setGunReady(boolean gunReady) {
		this.gunReady = gunReady;
	}

}
