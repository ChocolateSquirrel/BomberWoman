package game.roundState;

public enum State {
	WIN_NO_MORE_ENNEMY ("You win, there is no more ennemy !"),
	LOOSE_NO_MORE_LIFE ("You loose, you don't have anymore life !"),
	LOOSE_TIME_OFF ("You loose because of time !"),
	NOT_FINISHED_HURRY_UP("2 min left"),
	NOT_FINISHED("");
	
	private String name;
	
	private State(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
