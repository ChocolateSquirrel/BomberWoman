package states.playstate.game.map;

import java.util.Optional;

import com.jme3.math.Vector3f;

import engine.renderitems.Color;
import engine.renderitems.Cube;
import engine.renderitems.TextureSet;
import main.BomberWomanMain;

public class Wall extends Entity {
	private Cube cube;
	private int x;
	private int y;

	public Wall(String name, Color color, int x, int y) {
		super(name, color);
		this.x = x;
		this.y = y;
		cube = new Cube(1, 1, new Vector3f(x+0.5f, y+0.5f, BomberWomanMain.Z_WALL), getColor(), Optional.of(new TextureSet("Textures/wall_color.png", "Textures/wall_normal.png")));
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
