package engine.hud;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

import engine.EngineApplication;

public class Message {
	protected Node node = new Node();
	protected BitmapText message;
	private EngineApplication engineApp;
	
	public Message(EngineApplication engineApp) {
		this.engineApp = engineApp;
		BitmapFont font = new BitmapFont();
		font = engineApp.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
		message = new BitmapText(font);
		message.setSize(font.getCharSet().getRenderedSize()*4);
		message.setColor(ColorRGBA.Orange);
		node.attachChild(message);
	}
	
	public Node getNode() {
		return node;
	}
	
	public BitmapText getMessage() {
		return message;
	}
	
	public EngineApplication getEngineApp() {
		return engineApp;
	}
	
	public void changeMessage(String newText) {
		message.setText(newText);
	}
	
	public void translate(float x, float y, float z) {
		message.setLocalTranslation(x, y, z);
	}
	
	public void changeColor(ColorRGBA color) {
		message.setColor(color);
	}
	
}

