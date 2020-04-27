package game.roundState;

public abstract class StateBase {
	public abstract void onAnalog(String name, float value, float tpf);
	
	public abstract void onAction(String name, boolean isPressed, float tpf);
	
}
