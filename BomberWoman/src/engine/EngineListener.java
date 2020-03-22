package engine;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;

import game.GameRules;

/**
 * Redirects keyboard Inputs and engine time update to the game Rules.
 *
 */
public class EngineListener implements ActionListener, AnalogListener {
	private GameRules gameRules;
	
	public EngineListener(GameRules gameRules) {
		this.gameRules = gameRules;
	}
	
	@Override
	public void onAnalog(String name, float value, float tpf) {
		gameRules.manageContinuousInputs(name, value, tpf);
	}

	@Override
	public void onAction(String name, boolean isPressed, float tpf) {
		gameRules.manageDiscreteInputs(name, isPressed, tpf);
	}

}