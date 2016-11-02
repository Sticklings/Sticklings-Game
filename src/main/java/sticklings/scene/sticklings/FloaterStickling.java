package sticklings.scene.sticklings;

import sticklings.Game;
import sticklings.scene.MovementController.MovementType;
import sticklings.util.Location;

/**
 * Stickling that can fall from any height
 */
public class FloaterStickling extends Stickling {
	private static final double MIN_FALL_DIST = 20;

	private double lastFallDistance;

	public FloaterStickling() {
		locomotor.allowMovement(MovementType.Walk);
		locomotor.allowMovement(MovementType.Float);

		setTexture(Game.getInstance().getTextureManager().getTexture("/sprites/stickling/floater.png"));
		setTextureOffset(new Location(-10, -10));
		setBounds(20, 20);
	}

	@Override
	protected void operate() {
		// Reset the stickling to a basic stickling when it lands
		if (getDistanceFallen() <= 0 && lastFallDistance > MIN_FALL_DIST) {
			BasicStickling basic = new BasicStickling();
			basic.copyFrom(this);
			remove();
			getScene().addEntity(basic);
		}

		lastFallDistance = getDistanceFallen();
	}
}
