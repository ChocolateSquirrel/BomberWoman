package game.map;

public class ChangeAvatarSpeedPowerUp extends PowerUp {
	private float coefficientSpeed;

	public ChangeAvatarSpeedPowerUp(Entity entity, float x, float y, float coefficientSpeed) {
		super(entity, x, y);
		this.coefficientSpeed = coefficientSpeed;
	}
	
	@Override
	public void applyPowerUp(Avatar avatar) {
		avatar.setAvatarSpeedCoeeficient(coefficientSpeed);
	}

}
