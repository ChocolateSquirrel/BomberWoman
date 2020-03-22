package game;

import com.jme3.math.Vector3f;

import engine.renderitems.Text;
import game.map.Avatar;
import game.map.ChangeAvatarSpeedPowerUp;
import game.map.Color;
import game.map.DestructibleWall;
import game.map.Entity;
import game.map.ImproveRangeBombPowerUp;
import game.map.Map;
import game.map.PowerUp;
import game.map.Wall;

/**
 * The class represents a game round, which has the following life cycle:<br>
 * <ul>
 * 	<li>init the map and fill it with entities (at GameRound instance creation).
 * 	<li>update the map according to game rules (each time update is called).
 * </ul>
 *
 */
public class GameRound {
	private Map map;
	private Avatar playerAvatar;
	
	public GameRound() {
		// Create map
		map = new Map(20, 10);
		
		Entity entity1 = new Entity("player", new Color(0, 0, 255, 0));
		playerAvatar = new Avatar(entity1, 0, 0);
		map.addNonGridEntity(playerAvatar);
		
		Entity entity6 = new Entity("versus", new Color(0, 0, 255, 0));
		Avatar versus = new Avatar(entity6, 19, 9);
		map.addNonGridEntity(versus);
		
		Entity entity8 = new Entity("Speed boots", new Color(150, 150, 0, 0));
		ChangeAvatarSpeedPowerUp powerUpSpeed = new ChangeAvatarSpeedPowerUp(entity8, 15, 2, 2f);
		map.addNonGridEntity(powerUpSpeed);
		
		Entity entity10 = new Entity("Slow boots", new Color(150, 150, 0, 0));
		ChangeAvatarSpeedPowerUp powerUpSpeed2 = new ChangeAvatarSpeedPowerUp(entity10, 15, 7, 0.5f);
		map.addNonGridEntity(powerUpSpeed2);
	
		Entity entity7 = new Entity("Bomb Range 3", new Color(125, 0, 150, 0));
		ImproveRangeBombPowerUp powerUpBomb = new ImproveRangeBombPowerUp(entity7, 18, 8, 3);
		map.addNonGridEntity(powerUpBomb);
		
		Entity entity9 = new Entity("Bomb Range 2", new Color(125, 0, 150, 0));
		ImproveRangeBombPowerUp powerUpBomb2 = new ImproveRangeBombPowerUp(entity9, 13, 2, 2);
		map.addNonGridEntity(powerUpBomb2);
		
		Wall entity3 = new Wall("wall", new Color(0, 255, 0, 0), 10, 5);
		map.addGridEntity(entity3, 10, 5);
		
		DestructibleWall entity4 = new DestructibleWall("wall", new Color(0, 255, 0, 0), 2, 8);
		map.addGridEntity(entity4, 2, 8);
		
		Wall entity5 = new Wall("wall", new Color(0, 255, 0, 0), 10, 4);
		map.addGridEntity(entity5, 10, 4);
		
		System.out.println(map.describe());
		System.out.println("13, 2" + map.getGroundAt(13, 2).getIsPowerUp());
		System.out.println("15, 2" + map.getGroundAt(15, 2).getIsPowerUp());
		System.out.println("1, 2" + map.getGroundAt(1, 2).getIsPowerUp());
		
		// Place text under the map
		Clock.getInstance();
	}
	
	public Map getMap() {
		return map;
	}
	
	public Avatar getPlayerAvatar() {
		return playerAvatar;
	}
	
}
