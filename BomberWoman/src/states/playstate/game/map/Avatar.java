package states.playstate.game.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.jme3.math.Vector3f;

import engine.renderitems.Color;
import engine.renderitems.Cube;
import engine.renderitems.Sound;
import main.BomberWomanMain;
import states.playstate.game.actions.Action;

public class Avatar extends PlacedEntity {
	private Cube cube;
	private final List<PowerUp> powerUpListAvatar = new ArrayList<PowerUp>();
	private final List<Bomb> bombList = new ArrayList<Bomb>();
	private int rangeDamage = 1;
	private float avatarSpeed;
	protected int livesAvatar = 2;
	private List<Action> actionToDo;
	protected Sound sadSound;
	protected Sound happySound;
	

	public Avatar(Entity entity, float x, float y) {
		super(entity, x, y);
		cube = new Cube(1, 1, new Vector3f(x+0.5f, y+0.5f, BomberWomanMain.Z_AVATAR), new Color(123, 10, 255, 0), Optional.empty());
		avatarSpeed = BomberWomanMain.AVATAR_SPEED * 1;	
		actionToDo = new ArrayList<>();
		sadSound = new Sound("Sounds/voice/scream.ogg");
		happySound = new Sound("Sounds/voice/laugh1.ogg");
	}
	
	public String toString() {
		return super.toString();
	}
	
	public Cube getCube() {
		return cube;
	}
	
	public int getLivesAvatar(){
		return livesAvatar;
	}
	
	public void setLivesAvatar(int nbLives) {
		livesAvatar = nbLives;
	}
	
	public List<PowerUp> getPowerUpList(){
		return powerUpListAvatar;
	}
	
	public int getRangeDamage() {
		return rangeDamage;
	}
	
	public void setRangeDamage(int range) {
		this.rangeDamage = range;
	}
	
	public void setAvatarSpeedCoeeficient(float coef) {
		this.avatarSpeed = BomberWomanMain.AVATAR_SPEED * coef;
	}
	
	public float getAvatarSpeed() {
		return avatarSpeed;
	}
	
	public Sound getHappySound() {
		return happySound;
	}
	
	public Sound getSadSound() {
		return sadSound;
	}
	
	public List<Bomb> getAvatarBombList(){
		return bombList;
	}
	
	public void addPowerUp(PowerUp powerUp) {
		powerUpListAvatar.add(powerUp);
	}
	
	public List<Action> getActionsToDo(){
		return actionToDo;
	}
	
	public void clearActionToDo() {
		actionToDo.clear();
	}
	
	public void addActionToDo(Action action) {
		actionToDo.add(action);
	}
	
	/**
	 * Test if avatar is spatially able to catch a powerUp.
	 * @param powerUpToCatch : a {@link PowerUp} instance. 
	 * @return true if avatar is close enough to powerUpToCatch.
	 */
	public boolean canCatchPowerUp(PowerUp powerUpToCatch) {
		// Center of Avatar
		float avatarX = getX()+0.5f;
		float avatarY = getY()+0.5f;
		
		// CatchingZone : a square of 0.5 centered in avatar center.
		float zoneXMin = avatarX-0.25f;
		float zoneXMax = avatarX+0.25f;
		float zoneYMin = avatarY-0.25f;
		float zoneYMax = avatarY+0.25f;
		
		// entityZone : a square of 0.5 centered in powerUp coordinates.
		float entityXMin = powerUpToCatch.getX()+0.25f;
		float entityXMax = powerUpToCatch.getX()+0.75f;
		float entityYMin = powerUpToCatch.getY()+0.25f;
		float entityYMax = powerUpToCatch.getY()+0.75f;
		
		float leftMaxPositionX = Math.max(zoneXMin, entityXMin);
		float rightMinPositionX = Math.min(zoneXMin+0.5f, entityXMin+0.5f);
		float lowMaxPositionY = Math.max(zoneYMin, entityYMin);
		float highMinPositionY = Math.min(zoneYMin+0.5f, entityYMin+0.5f);
		
		// Intersection test between catchingZone of avatar and entityZone of powerUp.
		return leftMaxPositionX<rightMinPositionX && lowMaxPositionY<highMinPositionY;
	}
	
	/**
	 * Test if avatar intersects Walls.
	 * @param listWall list of Wall to test.
	 * @return true (intersection with one or many walls) or false (no intersection with a wall among listWall)
	 */
	public static boolean intersectionWithWalls(List<Wall> listWall, Vector3f candidatePosition) {
		for (Wall wall : listWall) {
			float leftMaxPositionX = Math.max(candidatePosition.x, wall.getX());
			float rightMaxPositionX = Math.min(candidatePosition.x + 1, wall.getX() + 1);
			float lowMaxPositionY = Math.max(candidatePosition.y, wall.getY());
			float highMinPositionY = Math.min(candidatePosition.y + 1, wall.getY() + 1);
			
			if ( (leftMaxPositionX<rightMaxPositionX) && (lowMaxPositionY<highMinPositionY) ) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Test if avatar is in danger: on a ground impacted by a bomb.
	 * @param listGround: list of grounds which are in an impacted zone.
	 * @return true if avatar is on a ground impacted by a bomb.
	 */
	public boolean isInDanger(List<Ground> listGround) {
		boolean isDanger = false;
		Vector3f positionNearerAvatar = new Vector3f(
				(float)Math.floor(getX() + 0.5f),
				(float)Math.floor(getY() + 0.5f),
				BomberWomanMain.Z_AVATAR);
		for (Ground ground : listGround) {
			if ( (positionNearerAvatar.x == ground.getX()) && (positionNearerAvatar.y == ground.getY()) ) {
				isDanger = true;
				break;
			}
		}
		return isDanger;
	}
	
	
	public void applyDamageOntheAvatar(Bomb bomb) {
		livesAvatar = livesAvatar - 1;
		System.out.println("number of lives of " + this.getEntity().getName() + " : " + livesAvatar);
	}
	
}
