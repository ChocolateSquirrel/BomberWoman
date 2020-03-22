package game.map;

import com.jme3.math.Vector3f;

import engine.renderitems.Cube;
import game.BomberWomanMain;

public class Wall extends Entity {
	private Cube cube;
	private int x;
	private int y;

	public Wall(String name, Color color, int x, int y) {
		super(name, color);
		this.x = x;
		this.y = y;
		cube = new Cube(1, 1, new Vector3f(x+0.5f, y+0.5f, BomberWomanMain.Z_WALL), getColor());
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Cube getCube() {
		return cube;
	}
	
	
	
}
