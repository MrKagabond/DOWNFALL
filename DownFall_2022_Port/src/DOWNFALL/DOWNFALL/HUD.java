package DOWNFALL;

//The H.U.D (Heads Up Display) shows the amount of health to the player via a graphical bar and a percentage meter 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class HUD extends Game_Object {

	public static int HEALTH = 100;

	private Game_Handle handler;
	private float greenValue = 255;

	private Player_Space_Ship player;
	private Player player_3D;

	public HUD(int x, int y, ID_System id, Game_Handle handler, Player_Space_Ship player) {
		super(x, y, id);
		this.handler = handler;
		this.player = player;
		this.player_3D = null;
	}

	public HUD(int x, int y, ID_System id, Game_Handle handler, Player player_3D) {
		super(x, y, id);
		this.handler = handler;
		this.player = null;
		this.player_3D = player_3D;
	}

	public void tick() {
		if (player != null) {
			if (player.getHealth() > 0) {
				greenValue = (int) Game.clamp(greenValue, 7, 255);
				greenValue = (player.getHealth()) * 2;
			}
		}
		if (player_3D != null) {
			if (player_3D.getHealth() > 0) {
				greenValue = (int) Game.clamp(greenValue, 7, 255);
				greenValue = (player_3D.getHealth()) * 2;
			}
		}
	}

	public void render(Graphics g) {
		if (player != null) {
			Font fnt = new Font("CourierNew", 1, 12);
			g.setFont(fnt);

			g.setColor(Color.gray);
			g.fillRect(15, 15, 200, 32);

			g.setColor(new Color(7, (int) greenValue, 7));
			g.fillRect(15, 15, ((player.getHealth()) * 2), 32);

			g.setColor(Color.white);
			g.drawRect(15, 15, 200, 32);
			if (player.getHealth() > 0) {
				g.drawString("Health: " + (player.getHealth()) + "%", 15, 64);
			} else {
				g.drawString("Health: " + 0 + "%", 15, 64);
			}
		}
		if (player_3D != null) {
			Font fnt = new Font("CourierNew", 1, 12);
			g.setFont(fnt);

			g.setColor(Color.gray);
			g.fillRect(15, 15, 200, 32);

			g.setColor(new Color(7, (int) greenValue, 7));
			g.fillRect(15, 15, ((player_3D.getHealth()) * 2), 32);

			g.setColor(Color.white);
			g.drawRect(15, 15, 200, 32);
			if (player_3D.getHealth() > 0) {
				g.drawString("Health: " + (player_3D.getHealth()) + "%", 15, 64);
			} else {
				g.drawString("Health: " + 0 + "%", 15, 64);
			}
		}
	}

	public Rectangle getBounds() {
		return null;
	}
}
