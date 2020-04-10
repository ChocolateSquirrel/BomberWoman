package game.map;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import engine.EngineApplication;
import engine.renderitems.Ball;
import engine.renderitems.Sound;
import game.BomberWomanMain;

public abstract class PowerUp extends PlacedEntity {
	private Ball ball;

	public PowerUp(Entity entity, float x, float y) {
		super(entity, x, y);
		ball = new Ball(new Vector3f(x+0.5f, y+0.5f, BomberWomanMain.Z_POWER_UP), entity.getColor());
	}
	
	public Ball getBall() {
		return ball;
	}
	
	public void removePowerUpNode() {
		Node nodePowerUp = this.getBall().getNode();
		EngineApplication.getInstance().getRootNode().detachChild(nodePowerUp);
	}
	
	
	/**
	 * When avatar catch the powerUp, changes are applied to the avatar using this method.
	 * @param avatar: target that will receive the effects.
	 */
	public abstract void applyPowerUp(Avatar avatar);

}
