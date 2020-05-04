package states.menustate;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;

import engine.EngineApplication;

public class PseudoButton {
	private Node node;
	private BitmapText text;
	private Picture pic;
	private MainMenuProposals menu;
	
	public PseudoButton(EngineApplication engineApp, MainMenuProposals menu, int index) {
		this.menu = menu;
		node = new Node();
	
		BitmapFont font = new BitmapFont();
		font = engineApp.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
		text = new BitmapText(font);
		text.setSize(font.getCharSet().getRenderedSize()*4);
		text.setColor(ColorRGBA.Blue);
		text.setText(menu.getName());
		text.setLocalTranslation(
				engineApp.getContext().getSettings().getWidth()/2 - text.getLineWidth()/2,
				index*engineApp.getContext().getSettings().getHeight()/3 + ( engineApp.getContext().getSettings().getHeight()/3 - text.getLineHeight() ) /2 + text.getLineHeight(),
				0.1f);
		node.attachChild(text);
		
		pic = new Picture("background pseudoButton");
		pic.setImage(engineApp.getAssetManager(), "Textures/mainMenuHorizontalBar.png", true);
		pic.setWidth(engineApp.getContext().getSettings().getWidth()/3);
		float heightPic = text.getLineHeight()*2;
		pic.setHeight(heightPic);
		pic.setLocalTranslation(
				engineApp.getContext().getSettings().getWidth()/3,
				index*engineApp.getContext().getSettings().getHeight()/3 + ( engineApp.getContext().getSettings().getHeight()/3 - heightPic ) /2 ,
				0);
		node.attachChild(pic);
	}
	
	public Node getNode() {
		return node;
	}
	
	public void highlightButton() {
		text.setColor(ColorRGBA.Orange);	
	}
	
	public void delightButton() {
		text.setColor(ColorRGBA.Blue);
	}
	
	public MainMenuProposals getMenuOfButton() {
		return menu;
	}

}
