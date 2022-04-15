package DOWNFALL;
//This class will listen to the keyboard for input and conduct necessary actions to translate a key pressed on the keyboard to the appropriate class for commands
import java.awt.event.KeyAdapter;

import java.awt.event.KeyEvent;

public class Key_Input extends KeyAdapter {
	private Game_Handle handler;

	private boolean[] pressedKey_Active = new boolean[5];

	public Key_Input(Game_Handle handler) {
		this.handler = handler;

		pressedKey_Active[0] = false;
		pressedKey_Active[1] = false;
		pressedKey_Active[2] = false;
		pressedKey_Active[3] = false;
		pressedKey_Active[4] = false;
	}

	public void keyPressed(KeyEvent e) {
		// while (input == true) {
		int key = e.getKeyCode();
		int correctionV, correctionVal_WS_LEFT_RIGHT, correctionVal_WS_UP_DOWN, correctionVal_Alt;
		for (int i = 0; i < handler.object.size(); i++) {
			Game_Object tempObject = handler.object.get(i);

			// Ship controll's
			if (tempObject.getId() == ID_System.Player_Ship) {
				// testing mode toggle
				if (key == KeyEvent.VK_L) {
					if (Game.getTESTING_MODE() == false) {
						Game.setTESTING_MODE(true);
					} else {
						Game.setTESTING_MODE(false);
					}
				}

				if (key == KeyEvent.VK_W) {
					tempObject.setVelY(-(tempObject.getSpeed()));
					pressedKey_Active[0] = true;
				}
				if (key == KeyEvent.VK_S) {
					tempObject.setVelY(tempObject.getSpeed());
					pressedKey_Active[1] = true;
				}
				if (key == KeyEvent.VK_D) {
					tempObject.setVelX(tempObject.getSpeed());
					pressedKey_Active[2] = true;
				}
				if (key == KeyEvent.VK_A) {
					tempObject.setVelX(-(tempObject.getSpeed()));
					pressedKey_Active[3] = true;
				}

				if (key == KeyEvent.VK_SPACE) {
					pressedKey_Active[4] = true;
					if (tempObject.getGunReady() == true) {
						tempObject.setGunReady(false);
						// up
						handler.addObject(
								new Player_Projectile((tempObject.getX() + 270), ((tempObject.getY() * -1) + 66),
										ID_System.Projectile, handler, Direction_System.Right));
						// down
						handler.addObject(
								new Player_Projectile((tempObject.getX() + 270), ((tempObject.getY() * -1) + 264),
										ID_System.Projectile, handler, Direction_System.Right));
					}
				}
			}
			// 3D controll's
			if (tempObject.getId() == ID_System.Player_3D) {
				// testing mode toggle
				if (key == KeyEvent.VK_L) {
					if (Game.getTESTING_MODE() == false) {
						Game.setTESTING_MODE(true);
					} else {
						Game.setTESTING_MODE(false);
					}
				}

				if (key == KeyEvent.VK_W) {
					tempObject.setVelY(-(tempObject.getSpeed()));
					pressedKey_Active[0] = true;
				}
				if (key == KeyEvent.VK_S) {
					tempObject.setVelY(tempObject.getSpeed());
					pressedKey_Active[1] = true;
				}
				if (key == KeyEvent.VK_D) {
					tempObject.setVelX(tempObject.getSpeed());
					pressedKey_Active[2] = true;
				}
				if (key == KeyEvent.VK_A) {
					tempObject.setVelX(-(tempObject.getSpeed()));
					pressedKey_Active[3] = true;
				}

				if (key == KeyEvent.VK_UP) {
					if (tempObject.getGunReady() == true) {
						handler.addObject(new Player_Projectile((tempObject.getX() + 10), (tempObject.getY() - 12),
								ID_System.Projectile, handler, Direction_System.Up));
						tempObject.setGunReady(false);
					}
				}
				if (key == KeyEvent.VK_DOWN) {
					if (tempObject.getGunReady() == true) {
						handler.addObject(new Player_Projectile((tempObject.getX() + 10),
								((tempObject.getY() + 20) + 12), ID_System.Projectile, handler, Direction_System.Down));
						tempObject.setGunReady(false);
					}
				}

				if (key == KeyEvent.VK_LEFT) {
					if (tempObject.getGunReady() == true) {
						handler.addObject(new Player_Projectile((tempObject.getX() - 12), (tempObject.getY() + 10),
								ID_System.Projectile, handler, Direction_System.Left));
						tempObject.setGunReady(false);
					}
				}
				if (key == KeyEvent.VK_RIGHT) {
					if (tempObject.getGunReady() == true) {
						handler.addObject(new Player_Projectile(((tempObject.getX() + 20) + 12),
								(tempObject.getY() + 10), ID_System.Projectile, handler, Direction_System.Right));
						tempObject.setGunReady(false);
					}
				}

			}

			// movement
			if (tempObject.getId() == ID_System.Player_3D_TEST) {
				// Math logic
				correctionVal_WS_LEFT_RIGHT = (int) ((6 * (tempObject.getSpeed() / 2) * Math.PI
						+ (tempObject.getSpeed() / 4)));
				correctionVal_WS_UP_DOWN = (int) ((tempObject.getSpeed() / Math.PI) + (tempObject.getSpeed() / 7)
						+ ((tempObject.getSpeed() + 2) * 3));
				correctionVal_Alt = (int) ((tempObject.getSpeed() / 6) * tempObject.getSpeed() + 1);
				correctionV = (int) (correctionVal_Alt + (Math.PI / tempObject.getSpeed()));
				if (key == KeyEvent.VK_W) {
					tempObject.setVelY(-(tempObject.getSpeed()));
					pressedKey_Active[0] = true;
				}
				if (key == KeyEvent.VK_S) {
					tempObject.setVelY(tempObject.getSpeed());
					pressedKey_Active[1] = true;
				}
				if (key == KeyEvent.VK_D) {
					tempObject.setVelX(tempObject.getSpeed());
					pressedKey_Active[2] = true;
				}
				if (key == KeyEvent.VK_A) {
					tempObject.setVelX(-(tempObject.getSpeed()));
					pressedKey_Active[3] = true;
				}

				// gun
				// CASE 0: No keys are pressed
				if (pressedKey_Active[0] == false && pressedKey_Active[1] == false && pressedKey_Active[2] == false
						&& pressedKey_Active[3] == false) {
					if (key == KeyEvent.VK_UP) {
						if (tempObject.getGunReady() == true) {
							handler.addObject(new Player_Projectile((tempObject.getX() + 10), (tempObject.getY() - 12),
									ID_System.Projectile, handler, Direction_System.Up));
							tempObject.setGunReady(false);
						}
					}
					if (key == KeyEvent.VK_DOWN) {
						if (tempObject.getGunReady() == true) {
							handler.addObject(
									new Player_Projectile((tempObject.getX() + 10), ((tempObject.getY() + 20) + 12),
											ID_System.Projectile, handler, Direction_System.Down));
							tempObject.setGunReady(false);
						}
					}

					if (key == KeyEvent.VK_LEFT) {
						if (tempObject.getGunReady() == true) {
							handler.addObject(new Player_Projectile((tempObject.getX() - 12), (tempObject.getY() + 10),
									ID_System.Projectile, handler, Direction_System.Left));
							tempObject.setGunReady(false);
						}
					}
					if (key == KeyEvent.VK_RIGHT) {
						if (tempObject.getGunReady() == true) {
							handler.addObject(new Player_Projectile(((tempObject.getX() + 20) + 12),
									(tempObject.getY() + 10), ID_System.Projectile, handler, Direction_System.Right));
							tempObject.setGunReady(false);
						}
					}
				}
				// CASE 1: Player moving UP
				if (pressedKey_Active[0] == true && pressedKey_Active[1] == false && pressedKey_Active[2] == false
						&& pressedKey_Active[3] == false) {
					if (key == KeyEvent.VK_UP) {
						if (tempObject.getGunReady() == true) {
							handler.addObject(new Player_Projectile((tempObject.getX() + 10),
									(tempObject.getY() - 12) - (tempObject.getSpeed() + correctionVal_WS_UP_DOWN),
									ID_System.Projectile, handler, Direction_System.Up));
							tempObject.setGunReady(false);
						}
					}
					if (key == KeyEvent.VK_DOWN) {
						if (tempObject.getGunReady() == true) {
							handler.addObject(new Player_Projectile((tempObject.getX() + 10),
									((tempObject.getY() + 20) + 12) - (tempObject.getSpeed() + correctionV),
									ID_System.Projectile, handler, Direction_System.Down));
							tempObject.setGunReady(false);
						}
					}

					if (key == KeyEvent.VK_LEFT) {
						if (tempObject.getGunReady() == true) {
							handler.addObject(new Player_Projectile((tempObject.getX() - 12),
									(tempObject.getY() + 10) - (tempObject.getSpeed() + correctionVal_WS_LEFT_RIGHT),
									ID_System.Projectile, handler, Direction_System.Left));
							tempObject.setGunReady(false);
						}
					}
					if (key == KeyEvent.VK_RIGHT) {
						if (tempObject.getGunReady() == true) {
							handler.addObject(new Player_Projectile(((tempObject.getX() + 20) + 12),
									(tempObject.getY() + 10) - (tempObject.getSpeed() + correctionVal_WS_LEFT_RIGHT),
									ID_System.Projectile, handler, Direction_System.Right));
							tempObject.setGunReady(false);
						}
					}
				} // CASE 2: Player moving DOWN
				if (pressedKey_Active[0] == false && pressedKey_Active[1] == true && pressedKey_Active[2] == false
						&& pressedKey_Active[3] == false) {
					if (key == KeyEvent.VK_UP) {
						if (tempObject.getGunReady() == true) {
							handler.addObject(new Player_Projectile((tempObject.getX() + 10),
									(tempObject.getY() - 12) + (tempObject.getSpeed() + correctionV),
									ID_System.Projectile, handler, Direction_System.Up));
							tempObject.setGunReady(false);
						}
					}
					if (key == KeyEvent.VK_DOWN) {
						if (tempObject.getGunReady() == true) {
							handler.addObject(new Player_Projectile((tempObject.getX() + 10),
									((tempObject.getY() + 20) + 12)
											+ (tempObject.getSpeed() + correctionVal_WS_UP_DOWN),
									ID_System.Projectile, handler, Direction_System.Down));
							tempObject.setGunReady(false);
						}
					}

					if (key == KeyEvent.VK_LEFT) {
						if (tempObject.getGunReady() == true) {
							handler.addObject(new Player_Projectile((tempObject.getX() - 12),
									(tempObject.getY() + 10) + (tempObject.getSpeed() + correctionVal_WS_LEFT_RIGHT),
									ID_System.Projectile, handler, Direction_System.Left));
							tempObject.setGunReady(false);
						}
					}
					if (key == KeyEvent.VK_RIGHT) {
						if (tempObject.getGunReady() == true) {
							handler.addObject(new Player_Projectile(((tempObject.getX() + 20) + 12),
									(tempObject.getY() + 10) + (tempObject.getSpeed() + correctionVal_WS_LEFT_RIGHT),
									ID_System.Projectile, handler, Direction_System.Right));
							tempObject.setGunReady(false);
						}
					}
				} // CASE 3: Player moving LEFT
				if (pressedKey_Active[0] == false && pressedKey_Active[1] == false && pressedKey_Active[2] == true
						&& pressedKey_Active[3] == false) {
					if (key == KeyEvent.VK_UP) {
						if (tempObject.getGunReady() == true) {
							handler.addObject(new Player_Projectile(
									(tempObject.getX() + 10) - (tempObject.getSpeed() + correctionV),
									(tempObject.getY() - 12), ID_System.Projectile, handler, Direction_System.Up));
							tempObject.setGunReady(false);
						}
					}
					if (key == KeyEvent.VK_DOWN) {
						if (tempObject.getGunReady() == true) {
							handler.addObject(new Player_Projectile(
									(tempObject.getX() + 10) - (tempObject.getSpeed() + correctionV),
									((tempObject.getY() + 20) + 12), ID_System.Projectile, handler,
									Direction_System.Down));
							tempObject.setGunReady(false);
						}
					}

					if (key == KeyEvent.VK_LEFT) {
						if (tempObject.getGunReady() == true) {
							handler.addObject(new Player_Projectile(
									(tempObject.getX() - 12) - (tempObject.getSpeed() + correctionVal_Alt),
									(tempObject.getY() + 10), ID_System.Projectile, handler, Direction_System.Left));
							tempObject.setGunReady(false);
						}
					}
					if (key == KeyEvent.VK_RIGHT) {
						if (tempObject.getGunReady() == true) {
							handler.addObject(new Player_Projectile(
									((tempObject.getX() + 20) + 12) - (tempObject.getSpeed() + correctionVal_Alt),
									(tempObject.getY() + 10), ID_System.Projectile, handler, Direction_System.Right));
							tempObject.setGunReady(false);
						}
					}
				} // CASE 4: Player moving RIGHT
				if (pressedKey_Active[0] == false && pressedKey_Active[1] == false && pressedKey_Active[2] == false
						&& pressedKey_Active[3] == true) {
					if (key == KeyEvent.VK_UP) {
						if (tempObject.getGunReady() == true) {
							handler.addObject(new Player_Projectile(
									(tempObject.getX() + 10) + (tempObject.getSpeed() + correctionV),
									(tempObject.getY() - 12), ID_System.Projectile, handler, Direction_System.Up));
							tempObject.setGunReady(false);
						}
					}
					if (key == KeyEvent.VK_DOWN) {
						if (tempObject.getGunReady() == true) {
							handler.addObject(new Player_Projectile(
									(tempObject.getX() + 10) + (tempObject.getSpeed() + correctionV),
									((tempObject.getY() + 20) + 12), ID_System.Projectile, handler,
									Direction_System.Down));
							tempObject.setGunReady(false);
						}
					}

					if (key == KeyEvent.VK_LEFT) {
						if (tempObject.getGunReady() == true) {
							handler.addObject(new Player_Projectile(
									(tempObject.getX() - 12) + (tempObject.getSpeed() + correctionV),
									(tempObject.getY() + 10), ID_System.Projectile, handler, Direction_System.Left));
							tempObject.setGunReady(false);
						}
					}
					if (key == KeyEvent.VK_RIGHT) {
						if (tempObject.getGunReady() == true) {
							handler.addObject(new Player_Projectile(
									((tempObject.getX() + 20) + 12) + (tempObject.getSpeed() + correctionV),
									(tempObject.getY() + 10), ID_System.Projectile, handler, Direction_System.Right));
							tempObject.setGunReady(false);
						}
					}
				}
			}
		}
	}

	// }

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		for (int i = 0; i < handler.object.size(); i++) {
			Game_Object tempObject = handler.object.get(i);

			if (tempObject.getId() == ID_System.Player_Ship) {
				// key events for Ship Player
				// Movement: |W.A.S.D|

				if (key == KeyEvent.VK_W) {
					pressedKey_Active[0] = false; // tempObject.setVelY(0);
				}
				if (key == KeyEvent.VK_S) {
					pressedKey_Active[1] = false; // tempObject.setVelY(0);
				}
				if (key == KeyEvent.VK_D) {
					pressedKey_Active[2] = false; // tempObject.setVelX(0);
				}
				if (key == KeyEvent.VK_A) {
					pressedKey_Active[3] = false; // tempObject.setVelX(0);
				} // vertical movement

				if (key == KeyEvent.VK_SPACE) {
					pressedKey_Active[4] = false;
				}

				if (!pressedKey_Active[0] && !pressedKey_Active[1]) {
					tempObject.setVelY(0);
				}
				// horizontal movement
				if (!pressedKey_Active[2] && !pressedKey_Active[3]) {
					tempObject.setVelX(0);
				}
			}

			if (tempObject.getId() == ID_System.Player_3D) {
				// key events for 3D Player
				// Movement: |W.A.S.D|

				if (key == KeyEvent.VK_W) {
					pressedKey_Active[0] = false; // tempObject.setVelY(0);
				}
				if (key == KeyEvent.VK_S) {
					pressedKey_Active[1] = false; // tempObject.setVelY(0);
				}
				if (key == KeyEvent.VK_D) {
					pressedKey_Active[2] = false; // tempObject.setVelX(0);
				}
				if (key == KeyEvent.VK_A) {
					pressedKey_Active[3] = false; // tempObject.setVelX(0);
				} // vertical movement

				if (!pressedKey_Active[0] && !pressedKey_Active[1]) {
					tempObject.setVelY(0);
				}
				// horizontal movement
				if (!pressedKey_Active[2] && !pressedKey_Active[3]) {
					tempObject.setVelX(0);
				}
			}
		}
	}
}
