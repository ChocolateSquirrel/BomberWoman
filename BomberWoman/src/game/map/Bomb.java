
package game.map;

import java.util.Optional;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

import engine.EngineApplication;
import engine.renderitems.Cube;
import engine.renderitems.ImpactedZoneGeometry;
import engine.renderitems.Sound;
import game.BomberWomanMain;
import game.Clock;

public class Bomb extends PlacedEntity {
	//Determine the number of cell the Bomb can destroy
	private boolean hasExploded = false;
	private Cube cube;
	private ImpactedZoneGeometry impactedZoneGeometry;
	private int range;
	private double fuseTimeInSeconds;
	private double explosionTime;
	private Sound bombExplosion;
	
	
	public Bomb(Entity entity, float x, float y, int range, double fuseTimeInSeconds) {
		super(entity, x, y);
		cube = new Cube(0.5f, 0.5f, new Vector3f(x, y, BomberWomanMain.Z_BOMB), new Color(16, 16, 16, 0), Optional.empty());
		this.range = range;
		this.fuseTimeInSeconds = fuseTimeInSeconds;
		explosionTime = Clock.getInstance().getTimeInSeconds() + fuseTimeInSeconds;
		impactedZoneGeometry = new ImpactedZoneGeometry(new Vector3f(x, y, BomberWomanMain.Z_BOMB), this.range);
		EngineApplication.getInstance().getRootNode().detachChild(impactedZoneGeometry.getNode());
		int i = (int) (Math.round(Math.random()*3) + 1);
		bombExplosion = new Sound("Sounds/bomb/explosion" + i + ".ogg");
	}
	
	public boolean getHasExploded() {
		return hasExploded;
	}
	
	public void setHasExploded(boolean answer) {
		hasExploded = answer;
	}
	
	public Cube getCube() {
		return cube;
	}
	
	public ImpactedZoneGeometry getImpactedZoneGeometry() {
		return impactedZoneGeometry;
	}
	
	public void transformBombGeometryIntoImpactedZoneGeometry() {
		EngineApplication.getInstance().getRootNode().attachChild(getImpactedZoneGeometry().getNode());
		Node nodeBomb = getCube().getNode();
		EngineApplication.getInstance().getRootNode().detachChild(nodeBomb);
	}
	
	public int getRange(){
		return range;
	}
	
	public void setRange(int newRange) {
		range = newRange;
	}
	
	public double getFuseTime() {
		return fuseTimeInSeconds;
	}
	
	public double getExplosionTime() {
		return explosionTime;
	}
	
	public void playExplosionSound() {
		bombExplosion.play();
	}
	
}
