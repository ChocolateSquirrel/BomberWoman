package engine;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import engine.renderitems.Message;
import game.BomberWomanMain;
import game.GameRound;
import game.GameRules;
import game.roundState.GameRoundState;
import game.roundState.State;

/**
 * Its single instance represents the life of an application built on the engine.
 */
public class EngineApplication extends SimpleApplication {
	private GameRules gameRules; 
	private GameRound round;
	private Message message;
	private AudioNode audio_win;
	private AudioNode audio_loose;
	private AudioNode audio_hurryUp;

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
		gameRules = new GameRules(round.getMap(), round.getPlayerAvatar());
		message = new Message(round.getMap().getWidth(),
				round.getMap().getHeight() /2,
				new Vector3f((round.getMap().getWidth())/2, (round.getMap().getHeight())/2, BomberWomanMain.Z_MESSAGE),
				new ColorRGBA(0.1f, 0.2f, 0.3f, 0.5f),
				" ");
		rootNode.detachChild(message.getNode());
		initKeys();
		initAudio();
		
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
//		cam.setParallelProjection(true);
//		renderManager.setCamera(cam, true);
//        float aspect = (float) cam.getWidth() / cam.getHeight();
//        float size   = 10f;
//        cam.setFrustum(-1000, 1000, -aspect * size, aspect * size, size, -size);
        
        // Display number of lives of avatar player.
        round.getPlayerAvatar().getText().changeStringInText("Nb of lives: " + round.getPlayerAvatar().getLivesAvatar());
	}
	
	@Override
	public void simpleUpdate(float tpf) {
		GameRoundState previousState = round.getGameRoundState();
		GameRoundState newState = gameRules.changeRoundState(round);
		
		switch (newState.getState()) {
		case WIN_NO_MORE_ENNEMY:
			message.changeMessage(newState.getState().getName());
			rootNode.attachChild(message.getNode());
			audio_win.play();
			break;
		case LOOSE_NO_MORE_LIFE:
			message.changeMessage(newState.getState().getName());
			rootNode.attachChild(message.getNode());
			audio_loose.play();
			break;
		case LOOSE_TIME_OFF:
			message.changeMessage(newState.getState().getName());
			rootNode.attachChild(message.getNode());
			audio_loose.play();
			break;
		case NOT_FINISHED_HURRY_UP:
		case NOT_FINISHED :
			gameRules.manageTimeUpdate(round, tpf);
			if (newState.getState() == State.NOT_FINISHED_HURRY_UP && previousState.getState() != State.NOT_FINISHED_HURRY_UP)
				audio_hurryUp.play();
			break;
			
		default: 
			throw new UnsupportedOperationException("Unknown gameRoundState");
		}
		
		round.setGameRoundState(newState);
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
		AudioNode audio_nature = new AudioNode(assetManager, "Sounds/ambient/foret.ogg", DataType.Stream);
		audio_nature.setLooping(true);
		audio_nature.setPositional(true);
		audio_nature.setVolume(3);
		rootNode.attachChild(audio_nature);
		audio_nature.play();
		
		audio_win = new AudioNode(assetManager, "Sounds/ambient/applaudissements.ogg", DataType.Stream);
		audio_win.setLooping(false);
		audio_win.setPositional(true);
		audio_win.setVolume(3);
		rootNode.attachChild(audio_win);
		
		audio_loose = new AudioNode(assetManager, "Sounds/ambient/vache.ogg", DataType.Stream);
		audio_loose.setLooping(false);
		audio_loose.setPositional(true);
		audio_loose.setVolume(3);
		rootNode.attachChild(audio_win);
		
		audio_hurryUp = new AudioNode(assetManager, "Sounds/ambient/tictac.ogg", DataType.Stream);
		audio_hurryUp.setLooping(true);
		audio_hurryUp.setPositional(true);
		audio_hurryUp.setVolume(3);
		rootNode.attachChild(audio_hurryUp);
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
