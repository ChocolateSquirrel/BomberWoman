package game.map;

import engine.renderitems.Sound;

public class ImproveRangeBombPowerUp extends PowerUp {
	private int range;

	public ImproveRangeBombPowerUp(Entity entity, float x, float y, int range) {
		super(entity, x, y);
		this.range = range; 
	}

	@Override
	public void applyPowerUp(Avatar avatar) {
		avatar.setRangeDamage(range);
		if (range >= avatar.getRangeDamage()) {
			Sound sound = new Sound ("Sounds/voice/laugh1.ogg");
			sound.play();
		}
		else {
			Sound sound = new Sound("Sounds/voice/scream.ogg");
			sound.play();
		}
	}

}
