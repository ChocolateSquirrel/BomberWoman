package states.playstate.game;

import com.jme3.math.Vector3f;

import engine.renderitems.Color;
import engine.renderitems.Text;
import states.playstate.game.map.ChangeAvatarSpeedPowerUp;
import states.playstate.game.map.DestructibleWall;
import states.playstate.game.map.Entity;
import states.playstate.game.map.ImproveRangeBombPowerUp;
import states.playstate.game.map.Map;
import states.playstate.game.map.MonsterAvatar;
import states.playstate.game.map.PlayerAvatar;
import states.playstate.game.map.Wall;
import states.playstate.game.roundstate.GameRoundState;
import states.playstate.game.roundstate.State;

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
	private float timeOfAGame = 10; // In seconds
	private GameRoundState gameRoundState;
	
	public GameRound() {
		// Create map
		map = new Map(20, 10);
		
		gameRoundState = new GameRoundState(State.NOT_FINISHED, 0);
		
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

	}
	
	public Map getMap() {
		return map;
	}
	
	public PlayerAvatar getPlayerAvatar() {
		return playerAvatar;
	}
	
	public float getTimeOfAGame() {
		return timeOfAGame;
	}
	
	public GameRoundState getGameRoundState() {
		return gameRoundState;
	}
	
	public void setGameRoundState(GameRoundState gameRoundState) {
		this.gameRoundState = gameRoundState;
	}
	
	public void setGameRoundState(State state, long numberOfFrame) {
		this.getGameRoundState().setState(state);
		this.getGameRoundState().setNumberOfFrame(numberOfFrame);
	}
}
