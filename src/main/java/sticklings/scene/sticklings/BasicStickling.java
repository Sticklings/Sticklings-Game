package sticklings.scene.sticklings;

import javafx.scene.image.Image;
import sticklings.render.BasicTexture;
import sticklings.render.DebugTexture;
import sticklings.scene.MovementController.MovementType;
import sticklings.util.Location;

public class BasicStickling extends Stickling {
	public BasicStickling() {
		setTexture(new BasicTexture(new Image(BasicStickling.class.getResourceAsStream("/debug/test-stickling.png"))));
		setTextureOffset(new Location(-10, -10));
		//setTexture(new DebugTexture(20, 20), new Location(-10, -10));
		locomotor.allowMovement(MovementType.Walk);
	}
	
	@Override
	protected void operate() {
		// No operation
	}
}
