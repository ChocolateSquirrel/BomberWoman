package states.playstate.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import engine.EngineApplication;
import main.BomberWomanMain;
import states.menustate.MenuState;
import states.playstate.PlayState;
import states.playstate.game.actions.Action;
import states.playstate.game.actions.MoveAction;
import states.playstate.game.actions.PoseBombAction;
import states.playstate.game.map.Avatar;
import states.playstate.game.map.Bomb;
import states.playstate.game.map.DestructibleWall;
import states.playstate.game.map.Map;
import states.playstate.game.map.MonsterAvatar;
import states.playstate.game.map.PlayerAvatar;
import states.playstate.game.roundstate.GameRoundState;
import states.playstate.game.roundstate.State;

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
			case BomberWomanMain.CONTROL_RIGHT : playerAvatar.addActionToDo(new MoveAction(Direction.RIGHT)); break;
			case BomberWomanMain.CONTROL_LEFT : playerAvatar.addActionToDo(new MoveAction(Direction.LEFT)); break;
			case BomberWomanMain.CONTROL_UP : playerAvatar.addActionToDo(new MoveAction(Direction.UP)); break;
			case BomberWomanMain.CONTROL_DOWN : playerAvatar.addActionToDo(new MoveAction(Direction.DOWN)); break;
			default : 
				// This is reached when a discrete input is provided: do nothing
				break;
			}
		}
	}
	
	public void manageDiscreteInputs(String name, boolean isPressed, float tpf, GameRound gameRound) {
		// Restart
		if ( (gameRound.getGameRoundState().getState() == State.LOOSE_TIME_OFF || 
				gameRound.getGameRoundState().getState() == State.LOOSE_NO_MORE_LIFE) &&
				name.equals(BomberWomanMain.CONTROL_RESTART) &&
				!isPressed ) {
			Clock.getInstance().reset();
			EngineApplication.getInstance().changeState(new MenuState());
			return;
		}
		
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
		Clock.getInstance().addFrame();
		
		// Generate Actions for AI and apply all actions (players and monsters avatars)
		haveAIGenerateActions();
		applyAvatarActions(tpf);
		
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
				
				for (Avatar avatar : map.getAvatars()) {
					// Determine the owner of the bomb
					if (avatar.getAvatarBombList().contains(bomb)) {
						avatar.getAvatarBombList().remove(bomb);
						// Apply damages on avatars
						for (Avatar mapAvatar : map.getAvatars()) {
							if (mapAvatar.isInDanger(map.getImpactedGroundZone(bomb))) {
								mapAvatar.applyDamageOntheAvatar(bomb);
								if (avatar instanceof PlayerAvatar && mapAvatar instanceof MonsterAvatar)
									avatar.getHappySound().play();
							}
						}
					}
					else {
						// Don't change avatar's lives
					}
				}
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
		
		// Clean the map
		for (Avatar avatar : map.getAvatars()) {
			if (avatar.getLivesAvatar() <= 0) {
				EngineApplication.getInstance().getRootNode().detachChild(avatar.getCube().getNode());
				map.removeNonGridAlignedEntities(avatar);
				System.out.println("Avatar " + avatar.getEntity().getName() + " is dead !");
			}
		}
	}
	
	public GameRoundState changeRoundState(GameRound gameRound) {
		GameRoundState gameRoundState = new GameRoundState(State.NOT_FINISHED, Clock.getInstance().getNbrFrames());
		if (playerAvatar.getLivesAvatar() <= 0) 
			gameRoundState.setState(State.LOOSE_NO_MORE_LIFE);

		if (map.getAvatars().size() == 1)
			gameRoundState.setState(State.WIN_NO_MORE_ENNEMY);
		
		if (Clock.getInstance().getTimeInSeconds() >= gameRound.getTimeOfAGame()*0.7f)
			gameRoundState.setState(State.NOT_FINISHED_HURRY_UP);
		
		if (Clock.getInstance().getTimeInSeconds() >= gameRound.getTimeOfAGame())
			gameRoundState.setState(State.LOOSE_TIME_OFF);
		
		return gameRoundState;
	}
	
	private void haveAIGenerateActions() {
		for (Avatar avatar : map.getAvatars()) {
			// Add a random action to MonsterAvatar
			if (avatar instanceof MonsterAvatar) {
				if (Clock.getInstance().getTimeInSeconds() >= ((MonsterAvatar) avatar).getTimeOfBegginingCurrentAction() + 1) {
					avatar.clearActionToDo();
					int choice = (int) Math.floor(Math.random()*5);
					switch (choice) {
					case 0:
						((MonsterAvatar) avatar).setCurrentAction(new MoveAction(Direction.RIGHT), Clock.getInstance().getTimeInSeconds());
						break;
					case 1:
						((MonsterAvatar) avatar).setCurrentAction(new MoveAction(Direction.LEFT), Clock.getInstance().getTimeInSeconds());
						break;
					case 2:
						((MonsterAvatar) avatar).setCurrentAction(new MoveAction(Direction.UP), Clock.getInstance().getTimeInSeconds());
						break;
					case 3:
						((MonsterAvatar) avatar).setCurrentAction(new MoveAction(Direction.DOWN), Clock.getInstance().getTimeInSeconds());
						break;
					case 4:
						((MonsterAvatar) avatar).setCurrentAction(new PoseBombAction(), Clock.getInstance().getTimeInSeconds());
						break;
					default : 
						throw new UnsupportedOperationException("Unknown action");
					}
				}
			}
		}
	}
	
	private void applyAvatarActions(float tpf) {
		for (Avatar avatar : map.getAvatars()) {
			for (Action action : avatar.getActionsToDo()) {
				action.applyAction(avatar, map, tpf);
			}
			if (avatar instanceof PlayerAvatar)
				avatar.clearActionToDo();
		}
	}
	
}
