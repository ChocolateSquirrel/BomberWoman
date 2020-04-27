package engine;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;

import game.GameRound;
import game.GameRules;

/**
 * Redirects keyboard Inputs and engine time update to the game Rules.
 *
 */
public class EngineListener implements ActionListener, AnalogListener {
	private GameRules gameRules;
	private GameRound gameRound;
	
	public EngineListener(GameRules gameRules, GameRound gameRound) {
		this.gameRules = gameRules;
		this.gameRound = gameRound;
	}
	
	public GameRound getGameRound() {
		return gameRound;
	}
	
	@Override
	public void onAnalog(String name, float value, float tpf) {
		gameRules.manageContinuousInputs(name, value, tpf);
	}

	@Override
	public void onAction(String name, boolean isPressed, float tpf) {
		gameRules.manageDiscreteInputs(name, isPressed, tpf, gameRound);
	}

}