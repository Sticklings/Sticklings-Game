package sticklings.scene.sticklings;

import sticklings.render.DebugTexture;
import sticklings.scene.MovementController.MovementType;
import sticklings.util.Location;

public class BasicStickling extends Stickling {
	public BasicStickling() {
		setTexture(new DebugTexture(20, 20), new Location(-10, -10));
		locomotor.allowMovement(MovementType.Walk);
	}
	
	@Override
	protected void operate() {
		// No operation
	}
}
