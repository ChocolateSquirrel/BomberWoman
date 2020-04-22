package game.actions;

import com.jme3.math.Vector3f;

import game.BomberWomanMain;
import game.map.Avatar;
import game.map.Bomb;
import game.map.Color;
import game.map.Entity;
import game.map.Ground;
import game.map.Map;

public class PoseBombAction implements Action {
	

	@Override
	public void applyAction(Avatar avatar, Map map, float tpf) {
		Vector3f avatarCenter = new Vector3f(
				avatar.getX() + 0.5f,
				avatar.getY() + 0.5f,
				BomberWomanMain.Z_AVATAR);
		
		Vector3f positionNearerAvatar = new Vector3f(
				(float)Math.floor(avatarCenter.x),
				(float)Math.floor(avatarCenter.y),
				BomberWomanMain.Z_AVATAR);
		
		Entity entityUnderBomb = map.getGroundAt((int)positionNearerAvatar.x, (int)positionNearerAvatar.y);
		Ground ent = (Ground) entityUnderBomb ;
		
		// Creation of a bomb only if there is no bomb on the ground
		if (!ent.getIsBomb()) {				
			Bomb bombinette = new Bomb(new Entity("bomb", new Color(32, 32, 32, 32)),
					positionNearerAvatar.x + 0.5f,
					positionNearerAvatar.y + 0.5f,
					avatar.getRangeDamage(),
					2);
			avatar.getAvatarBombList().add(bombinette);
			map.addNonGridEntity(bombinette);
			map.getGroundUnderBomb(bombinette).setIsBomb(true);
		}
		else {
			//System.out.println("Already a bomb!!");
		}
		
	}

}
