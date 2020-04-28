package states.playstate.game.map;

import engine.renderitems.Color;

/**
 * An Entity represents an object placed on the map.
 */
public class Entity {
	//A nonunique, human-readable name.
	private final String name;
	private final Color color;
	
	public Entity(String name, Color color) {
		this.name = name;
		this.color = color;
	}
	
	public String getName() { 
		return name; 
	}
	
	public Color getColor() {
		return color;
	}

}
