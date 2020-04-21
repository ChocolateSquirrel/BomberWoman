package game.map;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

import engine.renderitems.Text;
import game.BomberWomanMain;
import game.actions.Action;

public class PlayerAvatar extends Avatar {
	private Text textNbLives;

	public PlayerAvatar(Entity entity, float x, float y) {
		super(entity, x, y);
		textNbLives = new Text(new Vector3f(2, 12, BomberWomanMain.Z_GROUND), " ", new ColorRGBA(1, 1, 0, 1));
	}
	
	public Text getText() {
		return textNbLives;
	}
	
	@Override
	public void applyDamageOntheAvatar(Bomb bomb) {
		livesAvatar = livesAvatar - 1;
		System.out.println("number of lives of " + this.getEntity().getName() + " : " + livesAvatar);
		sadSound.play();
	}

}
