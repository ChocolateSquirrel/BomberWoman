package states.playstate.game.map;

public class PlacedEntity {
	private float xInCellCoordinates;
	private float yInCellCoordinates;
	private Entity entity;
	
	public PlacedEntity(Entity entity, float x, float y) {
		this.entity = entity;
		this.xInCellCoordinates = x;
		this.yInCellCoordinates = y;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public float getX() {
		return xInCellCoordinates;
	}

	public float getY() {
		return yInCellCoordinates;
	}
	
	public void setX(float x) {
		xInCellCoordinates = x;
	}
	
	public void setY(float y) {
		yInCellCoordinates = y;
	}
	
	public String toString() {
		return xInCellCoordinates+", "+yInCellCoordinates;
	}

	
}
