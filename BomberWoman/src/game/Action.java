package game;

import java.util.ArrayList;
import java.util.List;

import com.jme3.math.Vector3f;

import game.map.Avatar;
import game.map.Map;
import game.map.Wall;

public interface Action {
	
	void applyAction(Avatar avatar, Map map, float tpf);

}
