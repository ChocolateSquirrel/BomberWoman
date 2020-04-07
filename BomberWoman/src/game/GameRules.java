package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import engine.EngineApplication;
import game.map.Avatar;
import game.map.Bomb;
import game.map.ChangeAvatarSpeedPowerUp;
import game.map.Color;
import game.map.DestructibleWall;
import game.map.Entity;
import game.map.Ground;
import game.map.ImproveRangeBombPowerUp;
import game.map.Map;
import game.map.MonsterAvatar;
import game.map.PlayerAvatar;
import game.map.PowerUp;
import game.map.Wall;

/**
 * This class is where events from the engine come (key presses and time updates).<br>
 * Depending on those events, and according to the game Rules, the map will be updated.
 *
 */
public class GameRules {
	private Map map;
	private boolean isRunning  = true;
	private PlayerAvatar playerAvatar;
	
	public GameRules(Map map, PlayerAvatar playerAvatar) {
		this.map = map;
		this.playerAvatar = playerAvatar;
	}
	
	public void manageContinuousInputs(String name, float value, float tpf) {
		if (!isRunning) {
			System.out.println("Press P to unpause.");
			return;
		}
		
		else {	
			switch (name) {
			case BomberWomanMain.CONTROL_RIGHT : playerAvatar.addActionToDo(new MoveAction("Right")); break;
			case BomberWomanMain.CONTROL_LEFT : playerAvatar.addActionToDo(new MoveAction("Left")); break;
			case BomberWomanMain.CONTROL_UP : playerAvatar.addActionToDo(new MoveAction("Up")); break;
			case BomberWomanMain.CONTROL_DOWN : playerAvatar.addActionToDo(new MoveAction("Down")); break;
			default : break; // Unsupported key
			}
		}
	}
	
	public void manageDiscreteInputs(String name, boolean isPressed, float tpf) {
		// Change Pause status
		if (name.equals(BomberWomanMain.CONTROL_PAUSE) && !isPressed) {
			isRunning = !isRunning;
		}
		
		// Don't update if paused
		if (!isRunning) {
			System.out.println("Press P to unpause.");
			return;
		}
		
		// Pose a bomb
		if (name.equals(BomberWomanMain.CONTROL_BOMB) && isPressed) {
			playerAvatar.addActionToDo(new PoseBombAction());
		}
	}

	
	public void manageTimeUpdate(GameRound gameRound, float tpf){
		// Don't update if paused
		if (!isRunning) {
			System.out.println("Press P to unpause.");
			return;
		}
		
		// Not paused: update
		Clock.getInstance().addTime(tpf);
		//System.out.println(playerAvatar.getActionToDo());
		
		// Actions of Avatar
		for (Action action : playerAvatar.getActionToDo()) {
			action.applyAction(playerAvatar, map, tpf);
		}
		
		// Make bomb explode if it is time
		for (Bomb bomb : map.getBombList()) {
			
			// Damages caused by bomb : on the scene (visual) and on the map (walls and avatars)
			if ( Clock.getInstance().getTimeInSeconds() >= bomb.getExplosionTime() && !bomb.getHasExploded() ) {
				bomb.playExplosionSound();
				bomb.transformBombGeometryIntoImpactedZoneGeometry();
				bomb.setHasExploded(true);
				
				for (DestructibleWall wall : map.getImpactedDestructibleWall(bomb)) {
					wall.removeDestructibleWallNode();
					map.removeDestructibleWall(wall);
				}
				
				for (Avatar avatar : map.getAvatar()) {
					if (avatar.isInDanger(map.getImpactedGroundZone(bomb))) {
						avatar.setLivesAvatar(avatar.getLivesAvatar() - 1);
						System.out.println("nombre de vies de l'avatar " + avatar.getEntity().getName() + " : " + avatar.getLivesAvatar());
					}
					else {
						// Don't change avatar's lives
					}
				}
				playerAvatar.getText().changeStringInText("Nb of lives: " + playerAvatar.getLivesAvatar());
			}
			else {
				// Don't explode yet
			}
			
			// Remove visual damages some seconds after a bomb explosion 
			// Ground under bomb can now receive a new bomb
			if (Clock.getInstance().getTimeInSeconds() >= bomb.getExplosionTime() + BomberWomanMain.EXPLOSION_VISUAL_LIFE_TIME_IN_SECONDS) {
				EngineApplication.getInstance().getRootNode().detachChild(bomb.getImpactedZoneGeometry().getNode());
				map.getGroundUnderBomb(bomb).setIsBomb(false);
				map.removeNonGridAlignedEntities(bomb);
			}
			else {
				// Nothing to remove
			}
		}
		
		// Clean the map and Action to do 
		for (Avatar avatar : map.getAvatar()) {
			if (avatar.getLivesAvatar() <= 0) {
				EngineApplication.getInstance().getRootNode().detachChild(avatar.getCube().getNode());
				map.removeNonGridAlignedEntities(avatar);
				System.out.println("Avatar " + avatar.getEntity().getName() + " is dead !");
			}
			avatar.clearActionToDo();
		}
	}
	
	
	
	
	public String endOfTheRound() {
		String str = " ";
		if (playerAvatar.getLivesAvatar() <= 0)
			str = BomberWomanMain.LOOSE_NO_MORE_LIFE;
		if (map.getAvatar().size() == 1)
			str = BomberWomanMain.WIN_NO_MORE_ENNEMY;
		if (Clock.getInstance().getTimeInSeconds() > 240)
			str = BomberWomanMain.LOOSE_TIME_OFF;
		return str;
	}
	
}
