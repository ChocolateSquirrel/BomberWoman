package game.map;

import com.jme3.math.Vector3f;

import engine.renderitems.Cube;
import game.BomberWomanMain;

public class Ground extends Entity {
	private Cube cube;
	private int x;
	private int y;
	private boolean isBomb = false;
	private boolean isPowerUp;

	public Ground(String name, Color color, Boolean isBomb, int x, int y) {
		super(name, color);
		this.isBomb = isBomb;
		this.x = x;
		this.y = y;
		cube = new Cube(1, 1, new Vector3f(x+0.5f, y+0.5f, BomberWomanMain.Z_GROUND), getColor());
		isPowerUp = false;
	}
	
	public void setIsBomb(boolean answer) {
		isBomb = answer;
	}
	
	public boolean getIsBomb() {
		return isBomb;
	}
	
	public boolean getIsPowerUp() {
		return isPowerUp;
	}
	
	public void setIsPowerUp(boolean answer) {
		isPowerUp = answer;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

}
