package states.playstate.game.map;

import engine.renderitems.TextureSet;

public class ChangeAvatarSpeedPowerUp extends PowerUp {
	private float coefficientSpeed;

	public ChangeAvatarSpeedPowerUp(Entity entity, float x, float y, float coefficientSpeed) {
		super(entity, x, y);
		getBall().changeTextureSet(new TextureSet("Textures/changeSpeed.png", "Textures/woodNormal.png"));
		this.coefficientSpeed = coefficientSpeed;
		
	}
	
	@Override
	public void applyPowerUp(Avatar avatar) {
		avatar.setAvatarSpeedCoeeficient(coefficientSpeed);
//		if (coefficientSpeed >= 1) {
//			Sound sound = new Sound("Sounds/voice/laugh2.ogg");
//			sound.play();
//		}
//		else {
//			Sound sound = new Sound("Sounds/voice/scream.ogg");
//			sound.play();
//		}
	}

}
