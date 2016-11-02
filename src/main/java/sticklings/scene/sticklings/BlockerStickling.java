package sticklings.scene.sticklings;

import java.util.EnumSet;

import sticklings.Game;
import sticklings.scene.Collideable;
import sticklings.scene.Entity;
import sticklings.scene.MovementController.MovementType;
import sticklings.util.Location;

/**
 * Stickling that does not move and prevents other sticklings from moving through it
 */
public class BlockerStickling extends Stickling implements Collideable {
	public BlockerStickling() {
		setTexture(Game.getInstance().getTextureManager().getTexture("/sprites/stickling/blocker.png"));
		setTextureOffset(new Location(-10, -10));
		setBounds(20, 20);

		locomotor.setAllowedMovement(EnumSet.noneOf(MovementType.class));
	}

	@Override
	public Action onCollide(Entity entity) {
		if (entity instanceof Stickling) {
			return Action.BLOCK;
		}

		return Action.NONE;
	}

	@Override
	protected void operate() {
	}
}
