package states.playstate.game.map;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

import engine.renderitems.Text;
import engine.renderitems.TextureSet;
import main.BomberWomanMain;

public class PlayerAvatar extends Avatar {
	
	public PlayerAvatar(Entity entity, float x, float y) {
		super(entity, x, y);
		getCube().changeTextureSet(new TextureSet("Textures/playerAvatar.png", "Textures/woodNormal.png"));
	}
	
	@Override
	public void applyDamageOntheAvatar(Bomb bomb) {
		super.applyDamageOntheAvatar(bomb);
		sadSound.play();
	}

}
