package game;

public enum GameRoundState {
	WIN_NO_MORE_ENNEMY ("You win, there is no more ennemy !"),
	LOOSE_NO_MORE_LIFE ("You loose, you don't have anymore life !"),
	LOOSE_TIME_OFF ("You loose because of time !"),
	NOT_FINISHED("");
	
	private String name;
	
	private GameRoundState(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
