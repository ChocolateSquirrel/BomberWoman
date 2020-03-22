package engine.renderitems;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

import engine.EngineApplication;

public class Text extends RenderItem {
	private String text;
	private BitmapText bitMapText;
	
	public Text(Vector3f v, String test) {
		this.text = test;
		BitmapFont font = new BitmapFont();
		font = EngineApplication.getInstance().getAssetManager().loadFont("Interface/Fonts/Default.fnt");
		bitMapText = new BitmapText(font, false);
		bitMapText.setSize(1f);
		bitMapText.setLocalScale(1);
		bitMapText.setColor(ColorRGBA.Pink);
		bitMapText.setText(this.text);
		bitMapText.move(v);
		node.attachChild(bitMapText);
	}
	
	public void changeStringInText(String newText) {
		bitMapText.setText(newText);
	}


}
