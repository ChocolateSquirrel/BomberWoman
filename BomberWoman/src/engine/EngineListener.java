package engine;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;

/**
 * Redirects keyboard Inputs and engine time update to the game Rules.
 *
 */
public class EngineListener implements ActionListener, AnalogListener {
	
	public EngineListener() {}
	
	@Override
	public void onAnalog(String name, float value, float tpf) {
		EngineApplication.getInstance().getCurrentState().manageContinuousInputs(name, value, tpf);
	}

	@Override
	public void onAction(String name, boolean isPressed, float tpf) {
		EngineApplication.getInstance().getCurrentState().manageDiscreteInputs(name, isPressed, tpf);
		
	}

}