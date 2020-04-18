package game.actions;

import game.map.Avatar;
import game.map.Map;

public interface Action {
	
	/**
	 * Apply an action on {@link Avatar} and change the map accordingly.
	 * @param avatar {@link Avatar} on which action is applied.
	 * @param map {@link Map} modified after the action.
	 * @param tpf time elapsed since last frame in milliseconds.
	 */
	void applyAction(Avatar avatar, Map map, float tpf);

}
