package engine;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.scene.Node;

import game.GameRound;
import game.GameRules;
import states.PlayState;

/**
 * Its single instance represents the life of an application built on the engine.
 */
public class EngineApplication extends SimpleApplication {

	// Holds the unique EngineApplication instance
	private static EngineApplication instance;
	private AppState currentState;
	
	/**
	 * Create the unique instance of EngineApplication.
	 * @return unique instance.
	 */
	public static EngineApplication getInstance() {
		return instance==null ? instance=new EngineApplication() : instance;
	}

	@Override
	public void simpleInitApp() {
	}
	
	@Override
	public void simpleUpdate(float tpf) {
	}
	
	
	/**
	 * Private constructor required for singleton pattern.
	 */
	private EngineApplication() {
		currentState = new PlayState();
		getStateManager().attach(currentState);
	}
	
	public Node getRootNode() {
		return rootNode;
	}
	
	public void changeState(AppState state) {
		getStateManager().detach(currentState);
		currentState = state;
		getStateManager().attach(currentState);
	}
}
