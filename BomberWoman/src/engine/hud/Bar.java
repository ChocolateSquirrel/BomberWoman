package engine.hud;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;

import engine.EngineApplication;
import states.playstate.game.Clock;
import states.playstate.game.map.PlayerAvatar;

public class Bar {
	private Node node = new Node();
	private EngineApplication engineApp;
	private Picture pic;
	private TimerHUD timer;
	private Message nbLives;
	
	public Bar(EngineApplication engineApp) {
		this.engineApp = engineApp;
		
		pic = new Picture("bar");
		pic.setImage(engineApp.getAssetManager(), "Textures/hud.png", true);
		pic.setWidth(engineApp.getContext().getSettings().getWidth());
		pic.setHeight(engineApp.getContext().getSettings().getHeight()/5);
		pic.setPosition(0, 0);
		node.attachChild(pic);
		
		timer = new TimerHUD(engineApp);
		timer.translate(
				engineApp.getContext().getSettings().getWidth()/6 - timer.getText().getLineWidth()/2, 
				(engineApp.getContext().getSettings().getHeight()/5 - timer.getText().getLineHeight()) /2,
				0);
		node.attachChild(timer.getNode());
		
		nbLives = new Message(engineApp);
		nbLives.translate(
				engineApp.getContext().getSettings().getWidth()*2/3 + nbLives.getMessage().getLineWidth()/2, 
				(engineApp.getContext().getSettings().getHeight()/5 - nbLives.getMessage().getLineHeight()) /2 + nbLives.getMessage().getLineHeight()*0.7f,
				0);
		nbLives.changeMessage("Number of lives : " + 0);
		node.attachChild(nbLives.getNode());
	}
	
	public Node getNode() {
		return node;
	}
	
	public void updateBar(PlayerAvatar playerAvatar) {
		timer.displayTime(Clock.getInstance().getTimeInSeconds());
		nbLives.changeMessage("Number of lives : " + playerAvatar.getLivesAvatar());
		
	}

}
