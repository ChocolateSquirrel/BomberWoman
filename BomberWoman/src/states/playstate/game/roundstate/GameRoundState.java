package states.playstate.game.roundstate;

public class GameRoundState {
	private State state;
	private long numberOfFrame;
	
	public GameRoundState(State state, long numFrame) {
		this.state = state;
		this.numberOfFrame = numFrame;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public double getNumberOfFrame() {
		return numberOfFrame;
	}
	
	public void setNumberOfFrame(long numberOfFrame) {
		this.numberOfFrame = numberOfFrame;
	}

}
