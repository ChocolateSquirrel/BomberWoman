package states.menustate;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;

import engine.EngineApplication;

public class PseudoButton {
	private Node node;
	private int indexInMenu;
	private BitmapText button;
	private MainMenuProposals menu;
	
	public PseudoButton(EngineApplication engineApp, MainMenuProposals menu, int index) {
		this.menu = menu;
		this.indexInMenu = index;
		node = new Node();
	
		BitmapFont font = new BitmapFont();
		font = engineApp.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
		button = new BitmapText(font);
		button.setSize(font.getCharSet().getRenderedSize()*4);
		button.setColor(ColorRGBA.Blue);
		button.setText(menu.getName());
		button.setLocalTranslation(
				engineApp.getContext().getSettings().getWidth()/2 - button.getLineWidth()/2,
				index*engineApp.getContext().getSettings().getHeight()/3 + ( engineApp.getContext().getSettings().getHeight()/3 - button.getLineHeight() ) /2,
				0);
		node.attachChild(button);
	}
	
	public Node getNode() {
		return node;
	}
	
	public void changeColor(ColorRGBA color) {
		button.setColor(color);
	}
	
	public MainMenuProposals getMenuOfButton() {
		return menu;
	}

}
