package states.playstate.game.actions;

import com.jme3.math.Vector3f;

import main.BomberWomanMain;
import states.playstate.game.Direction;
import states.playstate.game.map.Avatar;
import states.playstate.game.map.Map;
import states.playstate.game.map.PowerUp;

public class MoveAction implements Action{
	private Direction directionEnum;
	
	public MoveAction(Direction directionEnum) {
		this.directionEnum = directionEnum;
	}

	@Override
	public void applyAction(Avatar avatar, Map map, float tpf) {
		// Creation of a Vector which represent the candidate translation
		Vector3f candidateTranslation = new Vector3f(0, 0, BomberWomanMain.Z_AVATAR);
		System.out.println("Avatar : " + avatar.getEntity().getName() +  ", speed : " + avatar.getAvatarSpeed());
		switch (directionEnum) {
		case RIGHT: candidateTranslation.x = avatar.getAvatarSpeed() * tpf; break;
		case LEFT : candidateTranslation.x = -avatar.getAvatarSpeed() * tpf; break;
		case UP : candidateTranslation.y = avatar.getAvatarSpeed() * tpf; break;
		case DOWN : candidateTranslation.y = -avatar.getAvatarSpeed() * tpf; break;
		default : 
			throw new UnsupportedOperationException("Unknown direction");
		}
		
		// Vector represents the future hypothetical position of the avatar 
		Vector3f candidatePosition = new Vector3f( 
				avatar.getX() + candidateTranslation.x, 
				avatar.getY() + candidateTranslation.y,
				0.f );

		// Vector represents the position of the avatar at the end of the translation
		Vector3f position = new Vector3f(avatar.getX(), avatar.getY(), 0f);
		if ( !(Avatar.intersectionWithWalls(map.getWall(), candidatePosition)) ) {
			position = new Vector3f(
					Math.min(map.getWidth()-1.f, Math.max(0.f, candidatePosition.x) ),
					Math.min(map.getHeight()-1.f, Math.max(0.f, candidatePosition.y) ),
					0 );
		}
		
		// Vector represents the translation to apply to the node of the avatar
		Vector3f translation = new Vector3f(
				position.x - avatar.getX(),
				position.y - avatar.getY(),
				0 );
		avatar.getCube().getNode().move(translation);

		// Note that this is done after computing translation
		avatar.setX(position.x);
		avatar.setY(position.y);

		// Catch powerUp "on the road" and remove catching powerUp from map and scene.
		for (PowerUp powerUp : map.getPowerUpOnMap()) {
			if (avatar.canCatchPowerUp(powerUp)) {
				avatar.addPowerUp(powerUp);
				map.removeNonGridAlignedEntities(powerUp);
				powerUp.removePowerUpNode();
				powerUp.applyPowerUp(avatar);
			}
		}
		
	}
	

}
