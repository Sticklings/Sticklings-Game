package sticklings.scene.sticklings;

import sticklings.Game;
import sticklings.scene.MovementController.MovementType;
import sticklings.util.Location;

/**
 * The standard stickling. Simply walks in a single direction, reversing when hitting an obstacle.
 * Has no special abilities
 */
public class BasicStickling extends Stickling {
	public BasicStickling() {
		setTexture(Game.getInstance().getTextureManager().getTexture("/sprites/stickling/basic.png"));
		setTextureOffset(new Location(-10, -10));
		// setTexture(new DebugTexture(20, 20), new Location(-10, -10));
		locomotor.allowMovement(MovementType.Walk);
	}

	@Override
	protected void operate() {
		// No operation
	}
}
