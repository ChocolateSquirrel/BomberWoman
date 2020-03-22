package engine;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

import engine.renderitems.Text;
import game.BomberWomanMain;
import game.GameRound;
import game.GameRules;
import game.map.Avatar;
import game.map.Bomb;
import game.map.Color;
import game.map.Entity;
import game.map.Ground;
import game.map.Map;
import game.map.PlacedEntity;
import game.map.PowerUp;

/**
 * Its single instance represents the life of an application built on the engine.
 */
public class EngineApplication extends SimpleApplication {
	private GameRules gameRules; 
	private GameRound round;

	
	// Holds the unique EngineApplication instance
	private static EngineApplication instance;
	
	/**
	 * Create the unique instance of EngineApplication.
	 * @return unique instance.
	 */
	public static EngineApplication getInstance() {
		return instance==null ? instance=new EngineApplication() : instance;
	}

	@Override
	public void simpleInitApp() {
		round = new GameRound();
		gameRules = new GameRules(round.getMap(), round.getPlayerAvatar(), round.getPlayerAvatar().getCube().getNode());
		initKeys();
		//initAudio();
		
		// Camera is fixed
		flyCam.setEnabled(false);
		
		// Place the camera
		float fov = 45f;
		float h = cam.getHeight();
		float W = 2*round.getMap().getWidth();
		float H = 2*round.getMap().getHeight();
		float dH = (float) (H / (2*Math.atan(fov/2)));
		float dW = (float) (W / (2*Math.atan(fov/2)));
		float d = (float) (1.75*Math.max(dH, dW));
		System.out.println("d="+d);
		cam.setFrame(
			new Vector3f(round.getMap().getWidth()/2.f, round.getMap().getHeight()/2.f, d), // location
			new Vector3f(-1, 0, 0), // left
			new Vector3f(0, 1, 0), // up
			new Vector3f(0, 0, -1) ); // direction
		
		// This switches camera to ortho mode
		cam.setParallelProjection(true);
		renderManager.setCamera(cam, true);
        float aspect = (float) cam.getWidth() / cam.getHeight();
        float size   = 10f;
        cam.setFrustum(-1000, 1000, -aspect * size, aspect * size, size, -size);
        
        // Display number of lives of avatar player.
        
	}
	
	@Override
	public void simpleUpdate(float tpf) {
		gameRules.manageTimeUpdate(round, tpf);
	}
	
	private void initKeys() {
		inputManager.addMapping(BomberWomanMain.CONTROL_PAUSE, new KeyTrigger(KeyInput.KEY_P));
		inputManager.addMapping(BomberWomanMain.CONTROL_RIGHT, new KeyTrigger(KeyInput.KEY_RIGHT));
		inputManager.addMapping(BomberWomanMain.CONTROL_LEFT, new KeyTrigger(KeyInput.KEY_LEFT));
		inputManager.addMapping(BomberWomanMain.CONTROL_UP, new KeyTrigger(KeyInput.KEY_UP));
		inputManager.addMapping(BomberWomanMain.CONTROL_DOWN, new KeyTrigger(KeyInput.KEY_DOWN));
		inputManager.addMapping(BomberWomanMain.CONTROL_BOMB, new KeyTrigger(KeyInput.KEY_SPACE));
		
		EngineListener listener = new EngineListener(gameRules);		
		inputManager.addListener((AnalogListener)listener, BomberWomanMain.CONTROL_RIGHT,
				BomberWomanMain.CONTROL_LEFT,
				BomberWomanMain.CONTROL_UP,
				BomberWomanMain.CONTROL_DOWN);
		inputManager.addListener((ActionListener)listener, BomberWomanMain.CONTROL_BOMB,
				BomberWomanMain.CONTROL_PAUSE);
	}
	
	private void initAudio() {
		AudioNode audio_nature = new AudioNode(assetManager, "../assets/sounds/foret.ogg", DataType.Stream);
		audio_nature.setLooping(true);
		audio_nature.setPositional(true);
		audio_nature.setVolume(3);
		rootNode.attachChild(audio_nature);
		audio_nature.play();
	}
	
	/**
	 * Private constructor required for singleton pattern.
	 */
	private EngineApplication() {
		
	}
	
	public Node getRootNode() {
		return rootNode;
	}

}
