package states.playstate.game.map;

import engine.renderitems.Color;

public class AbstractEntityFactory {
	
	public static AbstractEntity createAbstractEntity(String[] s) {
		AbstractEntity abstractEntity = null;
		String type = null;
		String name = null;
		Color color = null;
		float x = 0;
		float y = 0;
		
		
		// Collect name, color and position for Avatars (PlacedEntity) and Walls (Entity) 
		if (s.length == 8) {
			type = s[0].toLowerCase();
			name = s[1];
			color = new Color(Integer.parseInt(s[4]), Integer.parseInt(s[5]), Integer.parseInt(s[6]), Integer.parseInt(s[7]));
			x = Float.parseFloat(s[2]);
			y = Float.parseFloat(s[3]);
			
			switch (type) {
				case "playeravatar":
					abstractEntity = new PlayerAvatar(new Entity(name, color), x, y);
					break;
				case "monsteravatar":
					abstractEntity = new MonsterAvatar(new Entity(name, color), x, y);
					break;
				case "wall":
					abstractEntity = new Wall(name, color, (int)x, (int)y);
					break;
				case "destructiblewall":
					abstractEntity = new DestructibleWall(name, color, (int)x, (int)y);
					break;	
				default:
					throw new IllegalArgumentException("Unknown type for non-powerUp abstractEntity");
			}
			
		}
		
		// Collect name, color and position for PowerUp (PlacedEntity) 
		else if (s.length == 10) {
			type = s[1].toLowerCase();
			name = s[2];
			float attribute = Float.parseFloat(s[3]);
			color = new Color(Integer.parseInt(s[6]), Integer.parseInt(s[7]), Integer.parseInt(s[8]), Integer.parseInt(s[9]));
			x = Float.parseFloat(s[4]);
			y = Float.parseFloat(s[5]);
			
			switch (type) {
				case "changeavatarspeed":
					abstractEntity = new ChangeAvatarSpeedPowerUp(new Entity(name, color), x, y, attribute);
					break;
				case "improvebombrange":
					abstractEntity = new ImproveRangeBombPowerUp(new Entity(name, color), x, y, (int)attribute);
					break;	
				default:
					throw new IllegalArgumentException("Unknown type for powerUp abstractEntity");
			}
			
		}
		
		// Catch lines which don't have the good format.
		else {
			throw new IllegalArgumentException("Too much or not enough String in the line.");
		}
		
		return abstractEntity;
	}

}
