package states.playstate.game.map;

import com.jme3.scene.Node;

import engine.EngineApplication;
import engine.renderitems.Color;

public class DestructibleWall extends Wall {

	public DestructibleWall(String name, Color color, int x, int y) {
		super(name, color, x, y);
		// TODO Auto-generated constructor stub
	}
	
	public void removeDestructibleWallNode() {
		Node nodeDestructibleWall = this.getCube().getNode();
		EngineApplication.getInstance().getRootNode().detachChild(nodeDestructibleWall);
	}

}
