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
import game.map.MonsterAvatar;
import game.map.PlayerAvatar;
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
	private PlayerAvatar playerAvatar;
	
	public GameRound() {
		// Create map
		map = new Map(20, 10);
		
		Entity entity1 = new Entity("player", new Color(0, 0, 255, 0));
		playerAvatar = new PlayerAvatar(entity1, 0, 0);
		map.addNonGridEntity(playerAvatar);
		
		Entity entity2 = new Entity("versus1", new Color(0, 0, 255, 0));
		MonsterAvatar versus = new MonsterAvatar(entity2, 19, 9);
		map.addNonGridEntity(versus);
		
		Entity entity3 = new Entity("versus2", new Color(0, 0, 255, 0));
		MonsterAvatar versus2 = new MonsterAvatar(entity3, 15, 9);
		map.addNonGridEntity(versus2);
		
		Entity entity4 = new Entity("Speed boots", new Color(150, 150, 0, 0));
		ChangeAvatarSpeedPowerUp powerUpSpeed = new ChangeAvatarSpeedPowerUp(entity4, 15, 2, 2f);
		map.addNonGridEntity(powerUpSpeed);
		
		Entity entity5 = new Entity("Slow boots", new Color(150, 150, 0, 0));
		ChangeAvatarSpeedPowerUp powerUpSpeed2 = new ChangeAvatarSpeedPowerUp(entity5, 15, 7, 0.5f);
		map.addNonGridEntity(powerUpSpeed2);
	
		Entity entity6 = new Entity("Bomb Range 3", new Color(125, 0, 150, 0));
		ImproveRangeBombPowerUp powerUpBomb = new ImproveRangeBombPowerUp(entity6, 18, 8, 3);
		map.addNonGridEntity(powerUpBomb);
		
		Entity entity7 = new Entity("Bomb Range 2", new Color(125, 0, 150, 0));
		ImproveRangeBombPowerUp powerUpBomb2 = new ImproveRangeBombPowerUp(entity7, 13, 2, 2);
		map.addNonGridEntity(powerUpBomb2);
		
		Wall entity8 = new Wall("wall", new Color(0, 255, 0, 0), 10, 5);
		map.addGridEntity(entity8, 10, 5);
		
		DestructibleWall entity9 = new DestructibleWall("wall", new Color(0, 255, 0, 0), 2, 8);
		map.addGridEntity(entity9, 2, 8);
		
		Wall entity10 = new Wall("wall", new Color(0, 255, 0, 0), 10, 4);
		map.addGridEntity(entity10, 10, 4);
		
		System.out.println(map.describe());
		
		// Place text under the map
		Clock.getInstance();
	}
	
	public Map getMap() {
		return map;
	}
	
	public PlayerAvatar getPlayerAvatar() {
		return playerAvatar;
	}
	
}
