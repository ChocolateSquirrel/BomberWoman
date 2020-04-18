package game;

import com.jme3.math.Vector3f;

import engine.EngineApplication;
import game.map.Color;
import game.map.Entity;
import game.map.Map;

public class BomberWomanMain {
	
	// Position of entities along the z-axis (the higher, the closer to the camera).
	public static final float Z_GROUND = 0;
	public static final float Z_WALL = 1;
	public static final float Z_POWER_UP = 1.05f;
	public static final float Z_BOMB = 1.1f;
	public static final float Z_AVATAR = 1.15f;
	public static final float Z_MESSAGE = 1.5f;

	// Name of controls for Engine Key mapping
	public static final String CONTROL_PAUSE = "Pause";
	public static final String CONTROL_BOMB = "Bomb";
	public static final String CONTROL_RIGHT = "Right";
	public static final String CONTROL_LEFT = "Left";
	public static final String CONTROL_UP = "Up";
	public static final String CONTROL_DOWN = "Down";
	
	// Explosion visual life time
	public static final double EXPLOSION_VISUAL_LIFE_TIME_IN_SECONDS = 1;
	
	// Speed of the avatar expressed in map coordinates per second
	public static final float AVATAR_SPEED = 10f; 
	
	public static void main(String[] args) {			
		// Use the engine to create an app
		EngineApplication.getInstance().start();
	}

}
