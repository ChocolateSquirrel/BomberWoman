package engine;

import com.jme3.app.SimpleApplication;
import com.jme3.scene.Node;

import states.StateBase;
import states.menustate.MenuState;
import states.playstate.PlayState;

/**
 * Its single instance represents the life of an application built on the engine.
 */
public class EngineApplication extends SimpleApplication {

	// Holds the unique EngineApplication instance
	private static EngineApplication instance;
	private StateBase currentState;
	
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
	
	public Node getGuiNode() {
		return guiNode;
	}
	
	public StateBase getCurrentState() {
		return currentState;
	}
	
	public void changeState(StateBase state) {
		getStateManager().detach(currentState);
		currentState.cleanup();
		currentState = state;
		getStateManager().attach(currentState);
	}
}
