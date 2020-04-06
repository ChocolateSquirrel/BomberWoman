package game;

import game.map.Avatar;
import game.map.Map;

public interface Action {
	
	public abstract void applyAction(Avatar avatar, Map map);
	
	public abstract void move(Avatar avatar, Map map);
	
	public abstract void poseBomb(Avatar avatar, Map map);

}
