package DOWNFALL;
//Class containing “linked list” logic
//Import: "import java.util.LinkedList;" was used for practicality and simplicity 

import java.awt.Graphics;
import java.util.LinkedList;

public class Game_Handle {

	// loop through all objects and update & render
	LinkedList<Game_Object> object = new LinkedList<Game_Object>();

	public void tick() {
		// loops through each GameObject
		for (int i = 0; i < object.size(); i++) {
			// returns id
			Game_Object tempObject = object.get(i);

			tempObject.tick();
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < object.size(); i++) {
			Game_Object tempObject = object.get(i);
			tempObject.render(g);
		}
	}

	public void addObject(Game_Object object) {
		this.object.add(object);
	}

	public void removeObject(Game_Object object) {
		this.object.remove(object);
	}
}
