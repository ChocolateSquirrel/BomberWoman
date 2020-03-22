package game.map;

public class ImproveRangeBombPowerUp extends PowerUp {
	private int range;

	public ImproveRangeBombPowerUp(Entity entity, float x, float y, int range) {
		super(entity, x, y);
		this.range = range;
	}

	@Override
	public void applyPowerUp(Avatar avatar) {
		avatar.setRangeDamage(range);
	}

}
