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
import game.map.PowerUp;
import game.map.Wall;

/**
 * This class is where events from the engine come (key presses and time updates).<br>
 * Depending on those events, and according to the game Rules, the map will be updated.
 *
 */
public class GameRules {
	private Map map;
	private Avatar avatar;
	private Node playerNode;
	private boolean isRunning  = true;
	
	public GameRules(Map map, Avatar avatar, Node playerNode) {
		this.map = map;
		this.avatar = avatar;
		this.playerNode = playerNode;
	}
	
	public void manageContinuousInputs(String name, float value, float tpf) {
		if (!isRunning) {
			System.out.println("Press P to unpause.");
			return;
		}
		
		// This vector will hold the displacement
		Vector3f v = new Vector3f( 0, 0, 0);
		
		// Convert avatar speed to coordinates per second
		float distance = tpf * avatar.getAvatarSpeed();			
		switch(name) {
			case BomberWomanMain.CONTROL_RIGHT : v = new Vector3f(distance, 0, BomberWomanMain.Z_AVATAR); break;
			case BomberWomanMain.CONTROL_LEFT : v = new Vector3f(-distance, 0, BomberWomanMain.Z_AVATAR); break;
			case BomberWomanMain.CONTROL_UP : v = new Vector3f(0, distance, BomberWomanMain.Z_AVATAR); break;
			case BomberWomanMain.CONTROL_DOWN : v = new Vector3f(0, -distance, BomberWomanMain.Z_AVATAR); break;
			
			default : break; // Unsupported key
		}
		
		moveEntityNode(playerNode, v, avatar, map);	
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
				map.addNonGridEntity(bombinette);
				map.getGroundUnderBomb(bombinette).setIsBomb(true);
			}
			else {
				System.out.println("Already a bomb!!");
			}
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
		
		// Make bomb explode if it is time
		for (Bomb bomb : map.getBombList()) {
			
			// Damages caused by bomb : on the scene (visual) and on the map (entities)
			if (Clock.getInstance().getTimeInSeconds() >= bomb.getExplosionTime()) {
				EngineApplication.getInstance().getRootNode().attachChild(bomb.getImpactedZoneGeometry().getNode());
				bomb.removeBombNode();
				for (DestructibleWall wall : map.getImpactedDestructibleWall(bomb)) {
					wall.removeDestructibleWallNode();
					map.removeDestructibleWall(wall);
				}
				avatar.isInDanger(map.getImpactedGroundZone(bomb));
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
	}
	
	/**
	 * Move the node if the arrival point is in the Map and set new coordinates to the Avatar.
	 * @param node node attached to the avatar.
	 * @param candidateTranslation candidate vector for the transformation.
	 * @param avatar avatar to move.
	 * @param map the map, used to detect avatars outside of the map.
	 */
	private static void moveEntityNode(Node node, Vector3f candidateTranslation, Avatar avatar, Map map) {
		System.out.println( "Node: " + node.getWorldTranslation().toString() );
		System.out.println( "Avatar: " + avatar.toString() );
		
		Vector3f candidatePosition = new Vector3f( 
			avatar.getX() + candidateTranslation.x, 
			avatar.getY() + candidateTranslation.y,
			0.f );
		
		Vector3f position = new Vector3f();
		if ( !(intersectionWithWalls(candidatePosition, map.getWall())) ) {
			position = new Vector3f(
					Math.min(map.getWidth()-1.f, Math.max(0.f, candidatePosition.x) ),
					Math.min(map.getHeight()-1.f, Math.max(0.f, candidatePosition.y) ),
					0 );
		}
		else {
			position = new Vector3f(avatar.getX(), avatar.getY(), 0f);
		}
		
		Vector3f translation = new Vector3f(
			position.x - avatar.getX(),
			position.y - avatar.getY(),
			0 );
		
		avatar.setX( position.x );
		avatar.setY( position.y );
		node.move( translation );
		
		// Catch powerUp "on the road" and remove catching powerUp from map and scene.
		for (PowerUp powerUp : map.getPowerUpOnMap()) {
			if (avatar.canCatchPowerUp(powerUp)) {
				avatar.addPowerUp(powerUp);
				map.removeNonGridAlignedEntities(powerUp);
				powerUp.removePowerUpNode();
				powerUp.applyPowerUp(avatar);
			}
		}
		
		System.out.println(avatar.getPowerUpList().toString());
	}
	
	/**
	 * Test if an object intersects Walls.
	 * @param position position of the bottom-left corner of the Object which could intersect walls. 
	 * @param listWall list of Wall to test.
	 * @return answer: true (intersection with one or many walls) or false (no intersection with a wall among listWall)
	 */
	public static boolean intersectionWithWalls(Vector3f position, List<Wall> listWall) {
		boolean inter = false;
		boolean answer = false;
		// Construction of an Arraylist composed of boolean (each boolean correspond to the intersection between the Object and a Wall)
		ArrayList<Boolean> interAnswer = new ArrayList<Boolean>();
		for (Wall wall : listWall) {
			float leftMaxPositionX = Math.max(position.x, wall.getX());
			float rightMaxPositionX = Math.min(position.x + 1, wall.getX() + 1);
			float lowMaxPositionY = Math.max(position.y, wall.getY());
			float highMinPositionY = Math.min(position.y + 1, wall.getY() + 1);
			
			if ( (leftMaxPositionX<rightMaxPositionX) && (lowMaxPositionY<highMinPositionY) ) {
				inter = true;
			}
			else {
				inter = false;
			}
			interAnswer.add(inter);
		}
		
		// Test if there is a (just one) true in the ArrayList
		if (interAnswer.contains(true)){
			answer = true;
		}
		System.out.println("Intersection with walls : " + answer);		
		return answer;
	}
	
	
	
	
}
