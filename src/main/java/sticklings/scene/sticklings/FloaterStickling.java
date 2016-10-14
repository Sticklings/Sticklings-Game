package sticklings.scene.sticklings;

import sticklings.Game;
import sticklings.scene.MovementController.MovementType;
import sticklings.util.Location;

public class FloaterStickling extends Stickling {
	public FloaterStickling() {
		locomotor.allowMovement(MovementType.Walk);
		locomotor.allowMovement(MovementType.Float);
		
		setTexture(Game.getInstance().getTextureManager().getTexture("/sprites/stickling/floater.png"));
		setTextureOffset(new Location(-10, -10));
		setBounds(20, 20);
	}
	
	@Override
	protected void operate() {
	}
}
