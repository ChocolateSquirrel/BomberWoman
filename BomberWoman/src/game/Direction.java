package game;

public enum Direction {
	RIGHT("Right"),
	LEFT("Left"), 
	UP("Up"), 
	DOWN("Down"),
	POSE_BOMB("Pose a bomb");

	private String name = "";
	
	Direction(String name) {
		this.name = name;
	}
}
