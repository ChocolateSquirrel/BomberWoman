package engine.hud;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import engine.EngineApplication;

public class TimerHUD {
	private Node node = new Node();
	private EngineApplication engineApp;
	private BitmapText text;
	
	public TimerHUD(EngineApplication engineApp) {
		this.engineApp = engineApp;
		BitmapFont font = new BitmapFont();
		font = engineApp.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
		text = new BitmapText(font);
		text.setColor(ColorRGBA.Green);
		text.setSize(font.getCharSet().getRenderedSize()*4);
		text.setText(changeTimeInString(0));
		node.attachChild(text);
	}
	
	public Node getNode() {
		return node;
	}
	
	public BitmapText getText() {
		return text;
	}
	
	public void displayTime(double time) {
		text.setText(changeTimeInString(time));
	}
	
	public void translate(float x, float y, float z) {
		text.setLocalTranslation(x, y + text.getHeight()*0.7f, z);
	}
	
	/**
	 * Transform a number of seconds in a String (HH:mm:ss).
	 * @param timeInSeconds time given in seconds.
	 * @return a String (HH:mm:ss).
	 */
	private String changeTimeInString(double timeInSeconds) {
		StringBuilder str = new StringBuilder();
		
		int i = (int) timeInSeconds;
		int minuts = i/60;
		int seconds = i%60;
		int hours = minuts/60;
		minuts = minuts%60;
		
		str.append(" ");
		if (hours<10) {
			str.append("0");
			str.append(hours);
		}
		else {
			str.append(hours);
		}
		
		str.append(" : ");
		if (minuts<10) {
			str.append("0");
			str.append(minuts);
		}
		else {
			str.append(minuts);
		}
		
		str.append(" : ");
		if (seconds<10) {
			str.append("0");
			str.append(seconds);
		}
		else {
			str.append(seconds);
		}
		
		return str.toString();	
	}
}
