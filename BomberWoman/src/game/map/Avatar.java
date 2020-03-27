package game.map;

import java.util.ArrayList;
import java.util.List;

import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;

import engine.EngineApplication;
import engine.renderitems.Cube;
import engine.renderitems.Text;
import game.BomberWomanMain;

public class Avatar extends PlacedEntity {
	private Cube cube;
	private final List<PowerUp> powerUpListAvatar = new ArrayList<PowerUp>();
	private int rangeDamage = 1;
	private float avatarSpeed;
	private int livesAvatar = 2;
	

	public Avatar(Entity entity, float x, float y) {
		super(entity, x, y);
		cube = new Cube(1, 1, new Vector3f(x+0.5f, y+0.5f, BomberWomanMain.Z_AVATAR), new Color(0, 0, 255, 0));
		avatarSpeed = BomberWomanMain.AVATAR_SPEED * 1;
		
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
	
	public void addPowerUp(PowerUp powerUp) {
		powerUpListAvatar.add(powerUp);
	}
	
	
	/**
	 * Test if avatar is spatially able to catch a powerUp.
	 * @param powerUpToCatch : a PowerUp intance. 
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
	 * Test if avatar is in danger: on a ground impacted by a bomb.
	 * @param listGround: list of ground which are in an impacted zone.
	 * @return true if avatar is on a ground impacted by a bomb.
	 */
	public boolean isInDanger(List<Ground> listGround) {
		boolean isDanger = false;
		Vector3f positionNearerAvatar = new Vector3f(
				(float)Math.floor(this.getX() + 0.5f),
				(float)Math.floor(this.getY() + 0.5f),
				BomberWomanMain.Z_AVATAR);
		for (Ground ground : listGround) {
			if ( (positionNearerAvatar.x == ground.getX()) && (positionNearerAvatar.y == ground.getY()) ) {
				isDanger = true;
				break;
			}
		}
		return isDanger;
	}
	
	
}
