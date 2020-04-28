package states;

import com.jme3.app.state.AppState;

public abstract class StateBase implements AppState {
	protected abstract void doManageContinousInputs(String name, float value, float tpf);
	protected abstract void doManageDiscreteInput(String name, boolean isPressed, float tpf);
	
	public void manageContinuousInputs(String name, float value, float tpf) {
		if (isInitialized())
			doManageContinousInputs(name, value, tpf);
	}
	
	public void manageDiscreteInputs(String name, boolean isPressed, float tpf) {
		if (isInitialized())
			doManageDiscreteInput(name, isPressed, tpf);
	}
}
