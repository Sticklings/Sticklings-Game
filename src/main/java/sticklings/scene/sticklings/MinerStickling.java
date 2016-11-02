package sticklings.scene.sticklings;

import sticklings.Game;
import sticklings.scene.MovementController.MovementType;
import sticklings.util.Location;

/**
 * Stickling that digs a path through ground
 */
public class MinerStickling extends Stickling {
	private static final int MINE_TIME = 6;
	private static final int WALK_TIME = 6;

	private static final double MAX_FALL_DIST = 20;

	private State state;
	private int stepCount;
	private int nextStateChange;

	public MinerStickling() {
		setTexture(Game.getInstance().getTextureManager().getTexture("/sprites/stickling/miner.png"));
		setTextureOffset(new Location(-10, -10));

		setState(State.Mining);
	}

	private void setState(State state) {
		this.state = state;
		stepCount = 0;
		if (state == State.Mining) {
			locomotor.blockMovement(MovementType.Walk);
			nextStateChange = MINE_TIME;

			if (getScene() != null) {
				Location pos = getLocation();
				getScene().getTerrain().clearCircleAt((int) pos.x, (int) pos.y, 15);
			}
		} else {
			locomotor.allowMovement(MovementType.Walk);
			nextStateChange = WALK_TIME;
		}
	}

	@Override
	protected void operate() {
		// Once they have fallen at least a certain amount, they will become a normal stickling again
		if (getDistanceFallen() >= MAX_FALL_DIST) {
			// Become basic stickling again
			BasicStickling newStickling = new BasicStickling();
			newStickling.copyFrom(this);
			remove();
			getScene().addEntity(newStickling);
			return;
		}

		if (stepCount > nextStateChange) {
			switch (state) {
			case Mining:
				setState(State.Walking);
				break;
			case Walking:
				setState(State.Mining);
				break;
			}
		} else {
			++stepCount;
		}
	}

	private enum State {
		Mining, Walking
	}

}
