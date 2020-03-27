package engine.renderitems;

import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

import engine.EngineApplication;


public class Message extends RenderItem {
	private Text message;
	private Vector3f position;
	
	public Message(float width, float height, Vector3f position, ColorRGBA color, String message) {
		this.position = position;
		
		this.message = new Text(position, message, new ColorRGBA(0, 1, 0, 1));
		node.attachChild(this.message.getNode());
		
		Box b1 = new Box(width*0.5f, height*0.5f, 0.5f);
		Geometry cube = new Geometry("Box", b1);
		Material mat1 = new Material(EngineApplication.getInstance().getAssetManager(),
		          "Common/MatDefs/Misc/Unshaded.j3md");
		mat1.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		mat1.setColor("Color", color);
		cube.setMaterial(mat1);
		cube.setQueueBucket(Bucket.Transparent);
		cube.move(position);
		node.attachChild(cube);
	}

	public void changeMessage(String s) {
		message.changeStringInText(s);
		int sizeOfMessage = s.length();
		System.out.println(sizeOfMessage);
		Vector3f translation = new Vector3f(position.x-sizeOfMessage/10,position.y,position.z);
		message.translate(translation);
	}
}
