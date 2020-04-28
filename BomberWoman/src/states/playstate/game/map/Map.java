package states.playstate.game.map;

import java.util.ArrayList;
import java.util.List;

import com.jme3.math.Vector3f;

import engine.EngineApplication;
import engine.renderitems.Color;

public class Map {
	private Ground[][] groundEntities;
	private Entity[][] gridAlignedEntities;
	private final List<PlacedEntity> nonGridAlignedEntities = new ArrayList<PlacedEntity>();
	
	
	/**
	 * Creates an instance of Map.
	 * @param rowCount number of rows
	 * @param columnCount number of columns
	 * @throws IllegalArgumentException when rowCount or columnCount are negatives or zero.
	 */
	public Map(int rowCount, int columnCount) throws IllegalArgumentException {
		if (rowCount<=0 || columnCount<=0) {
			throw new IllegalArgumentException("Received invalid grid dimensions: "+ rowCount + ", " + columnCount);
		}
		
		// Build map ground
		groundEntities = new Ground[rowCount][];
		for (int i=0; i<rowCount; i++) {
			groundEntities[i] = new Ground[columnCount];
			for (int j=0; j<groundEntities[i].length; j++) {
				int c = (int) (Math.random()*256);
				groundEntities[i][j] = new Ground("ground", new Color(c, c, c, 0), false, i, j);
			}
		}
		
		// Prepare grid array
		gridAlignedEntities = new Entity[rowCount][];
		for (int i=0; i<rowCount; i++) {
			gridAlignedEntities[i] = new Entity[columnCount];
		}
	}
	
	/**
	 * Get the map width in cell coordinates.
	 * @return map width (column count)
	 */
	public int getWidth() {
		return gridAlignedEntities.length;
	}
	
	/**
	 * Get the map height in cell coordinates.
	 * @return map height (row count)
	 */
	public int getHeight() {
		return gridAlignedEntities[ 0 ].length;
	}
	
	public String describe() {
		StringBuilder str = new StringBuilder();
		str.append("Out of the grid: ") ;
		str.append(nonGridAlignedEntities.size());
		str.append(" entities \n");
		for (PlacedEntity ent : nonGridAlignedEntities) {
			str.append( "\t - " );
			str.append( ent.getEntity().getName() );
			str.append( " @ " );
			str.append( "(" );
			str.append( ent.getX() );
			str.append( "," );
			str.append( ent.getY() );
			str.append( ") \n" );
		}
		
		str.append("\nIn the grid: ") ;
		str.append(countNonNullCells());
		str.append(" entities \n");
		for (int i=0; i<gridAlignedEntities.length; i++) {
			for (int j=0; j<gridAlignedEntities[i].length; j++) {
				if (gridAlignedEntities[i][j] != null) {
					if (gridAlignedEntities[i][j] instanceof Wall) {
						str.append( "\t - " );
						str.append( gridAlignedEntities[i][j].getName() );
						str.append( " @ " );
						str.append( "(" );
						str.append( i );
						str.append( "," );
						str.append( j );
						str.append( ") : " );
					}
					else {
						str.append( "\t - " );
						str.append( gridAlignedEntities[i][j].getName() );
						str.append( " @ " );
						str.append( "(" );
						str.append( i );
						str.append( "," );
						str.append( j );
						str.append( ")" );
					}
				}
			}
		}
		
		return str.toString();
	}
	
	/**
	 * Add an Entity if there is no one at (x, y).
	 * @param ent Entity to add.
	 * @param x row index.
	 * @param y column index.
	 * @throws IllegalArgumentException when x or y are not within range.
	 * @throws UnsupportedOperationException when there is already an Entity at (x, y).
	 */
	public void addGridEntity(Entity ent, int x, int y) throws IllegalArgumentException, UnsupportedOperationException {
		if (x<0 || x>=gridAlignedEntities.length) {
			throw new IllegalArgumentException("x must be between zero and rowCount.");
		}
		if (y<0 || y>=gridAlignedEntities[0].length) {
			throw new IllegalArgumentException("y must be between zero and columnCount.");
		}
		if (gridAlignedEntities[x][y] != null) {
			throw new UnsupportedOperationException("There is already an entity (" + gridAlignedEntities[x][y].getName() + ") at " + "(" + x + "," + y + ")");
		}
		gridAlignedEntities[x][y] = ent;
	}
	
	/**
	 * Add an entity not necessarily aligned with the grid.<br>
	 * Note: entities at the same place are allowed.
	 * 
	 * @param plEnt valid instance of {@link PlacedEntity}.
	 */
	public void addNonGridEntity(PlacedEntity plEnt) {
		if (plEnt instanceof PowerUp) {
			int xNearerPowerUp = (int) Math.floor(plEnt.getX());
			int yNearerPowerUp = (int) Math.floor(plEnt.getY());
			Ground groundUnderPowerUp = getGroundAt(xNearerPowerUp, yNearerPowerUp);
			groundUnderPowerUp.setIsPowerUp(true);
		}
		nonGridAlignedEntities.add(plEnt);
	}
	
	/**
	 * Give coordinates of the map center.
	 * @return a Vector3f corresponding to map center coordinates.
	 */
	public Vector3f centerOfMap() {
		// Center of map is in the middle of the middle cell
		float cellSize = 1f;
		float y = (float) (0.5*gridAlignedEntities.length) + cellSize*.5f;
		float x = (float) (0.5*gridAlignedEntities[0].length) + cellSize*.5f;
		Vector3f center = new Vector3f(x, y, 20);
		//System.out.println("centerOfMap() is at "+x+", "+y);
		return center;
	}
	
	private int countNonNullCells() {
		int nbCells =0;
		for (int i=0; i<gridAlignedEntities.length; i++) {
			for (int j=0; j<gridAlignedEntities[i].length; j++) {
				if (gridAlignedEntities[i][j] != null) {
					nbCells++;
				}
			}
		}
		return nbCells;
	}
	
	public ArrayList<Avatar> getAvatars(){
		ArrayList<Avatar> listAvatarOnMap = new ArrayList<Avatar>();
		for (PlacedEntity plEnt : nonGridAlignedEntities) {
			if (plEnt instanceof Avatar) {
				listAvatarOnMap.add((Avatar) plEnt);
			}
		}
		return listAvatarOnMap;
	}
	
	public List<Wall> getWall(){
		List<Wall> wallList = new ArrayList<Wall>();
		for (Entity[] subEnt : gridAlignedEntities) {
			for (Entity ent : subEnt) {
				if (ent instanceof Wall) {
					wallList.add((Wall) ent);
				}
			}
		}
		return wallList;
	}
	
	public List<PowerUp> getPowerUpOnMap(){
		List<PowerUp> listPowerUpOnMap = new ArrayList<PowerUp>();
		for (PlacedEntity plEnt : nonGridAlignedEntities) {
			if (plEnt instanceof PowerUp) {
				listPowerUpOnMap.add((PowerUp) plEnt);
			}
		}
		return listPowerUpOnMap;
	}
	
	public Entity getGridAlignedEntitiesAt(int i, int j) {
		return gridAlignedEntities[i][j];
	}
	
	public PowerUp getPowerUpAt(float x, float y) {
		PowerUp thePowerUp = null;
			for (PowerUp powerUp : this.getPowerUpOnMap()) {
				try {
					if ((powerUp.getX()==x) && (powerUp.getY()==y)) {
						thePowerUp = powerUp;
					}
				} catch (IllegalArgumentException e) {
					System.out.println("No PowerUp at (" + x + ";" + y + ")"); 
				}
				
			} 
		return thePowerUp;
	}
	
	public Ground getGroundAt(int x, int y) {
		if (x<0 || x>=groundEntities.length) {
			throw new IllegalArgumentException("x must be between zero and rowCount.");
		}
		if (y<0 || y>=groundEntities[0].length) {
			throw new IllegalArgumentException("y must be between zero and columnCount.");
		}
		return groundEntities[x][y];
	}
	
	public List<DestructibleWall> getImpactedDestructibleWall(Bomb bomb){
		List<DestructibleWall> impactedDestructibleWall = new ArrayList<DestructibleWall>();
		// Whole coordinates of the bomb
		int xWholeBombCoordinate = (int) (bomb.getX() - 0.5f);
		int yWholeBombCoordinate = (int) (bomb.getY() - 0.5f);
		// Entities along abscesses axe
		for (int k= -bomb.getRange(); k<bomb.getRange()+1; k++) {
			// Check abscesses concerned are between 0 and the width of the map
			if ( (xWholeBombCoordinate+k>=0 && xWholeBombCoordinate+k<getWidth()) ) {
				if (getGridAlignedEntitiesAt(xWholeBombCoordinate+k, yWholeBombCoordinate) instanceof DestructibleWall) {
					impactedDestructibleWall.add((DestructibleWall) getGridAlignedEntitiesAt(xWholeBombCoordinate+k, yWholeBombCoordinate));
				}
			}
		}
		//Entities along ordinates axe
		for (int k= -bomb.getRange(); k<bomb.getRange()+1; k++) {
			// Check ordinates concerned are between 0 and the height of the map.
			if ( (yWholeBombCoordinate+k>=0 && yWholeBombCoordinate+k<getHeight()) ) {
				if (getGridAlignedEntitiesAt(xWholeBombCoordinate, yWholeBombCoordinate+k) instanceof DestructibleWall) {
					impactedDestructibleWall.add((DestructibleWall) getGridAlignedEntitiesAt(xWholeBombCoordinate, yWholeBombCoordinate+k));
				}
			}
		}
		return impactedDestructibleWall;
	}
	
	public List<Ground>getImpactedGroundZone(Bomb bomb){
		List<Ground> impactedGround = new ArrayList<Ground>();
		// Whole coordinates of the bomb
		int xWholeBombCoordinate = (int) (bomb.getX() - 0.5f);
		int yWholeBombCoordinate = (int) (bomb.getY() - 0.5f);
		// Grounds along abscesses axe
		for (int k= -bomb.getRange(); k<bomb.getRange()+1; k++) {
			// Check abscesses concerned are between 0 and the width of the map
			if ( (xWholeBombCoordinate+k>=0 && xWholeBombCoordinate+k<getWidth()) ) {
				impactedGround.add(getGroundAt(xWholeBombCoordinate+k, yWholeBombCoordinate));
			}
		}
		// Grounds along ordinates axe
		for (int k= -bomb.getRange(); k<bomb.getRange()+1; k++) {
			// Check ordinates concerned are between 0 and the height of the map.
			if ( (yWholeBombCoordinate+k>=0 && yWholeBombCoordinate+k<getHeight()) ) {
				impactedGround.add(getGroundAt(xWholeBombCoordinate, yWholeBombCoordinate+k));
			}
		}
		return impactedGround;
	}
	
	public void removeDestructibleWall(DestructibleWall destructibleWall) {
		int i = destructibleWall.getX();
		int j = destructibleWall.getY();
		gridAlignedEntities[i][j] = null;
	}
	
	public void removeNonGridAlignedEntities(PlacedEntity plEnt) {
		if (plEnt instanceof PowerUp) {
			int xNearerPowerUp = (int) Math.floor(plEnt.getX());
			int yNearerPowerUp = (int) Math.floor(plEnt.getY());
			Ground groundUnderPowerUp = getGroundAt(xNearerPowerUp, yNearerPowerUp);
			groundUnderPowerUp.setIsPowerUp(false);
		}
		nonGridAlignedEntities.remove(plEnt);
	}
	
	public List<Bomb> getBombList(){
		List<Bomb> listOfBomb = new ArrayList<Bomb>();
		for (PlacedEntity plEnt : nonGridAlignedEntities) {
			if (plEnt instanceof Bomb) {
				listOfBomb.add((Bomb) plEnt);
			}
		}
		return listOfBomb;
	}
	
	public Ground getGroundUnderBomb(Bomb bomb) {
		Ground ground = getGroundAt((int)(bomb.getX()-0.5f), (int)(bomb.getY()-0.5f));
		return ground;
	}
}
