package engine.renderitems;

import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import engine.EngineApplication;

/**
 * RenderItem allows the game code to display and move objects (in the JMonkey scene).
 */
public class RenderItem {
	protected Node node;
	
	protected RenderItem() {
		node = new Node();
		EngineApplication.getInstance().getRootNode().attachChild(node);
	}

	
	public void translate(Vector3f v) {
		node.move(v);
	}
	
	public Node getNode() {
		return node;
	}
}
