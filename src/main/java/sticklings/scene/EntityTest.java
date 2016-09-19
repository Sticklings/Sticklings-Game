package sticklings.scene;

import sticklings.render.DebugTexture;
import sticklings.util.Location;

public class EntityTest extends Entity {
	private int dirX;
	private int dirY;
	
	public EntityTest() {
		setTexture(new DebugTexture(32, 32));
		setTextureOffset(new Location(-16, -16));
		dirX = 1;
		dirY = 1;
	}
	
	@Override
	public void update(double deltaTime) {
		Location previous = getLocation();
		
		if (previous.x < 0) {
			dirX = 1;
		}
		
		if (previous.y < 0) {
			dirY = 1;
		}
		
		if (previous.x >= getScene().getWidth()) {
			dirX = -1;
		}
		
		if (previous.y > getScene().getHeight()) {
			dirY = -1;
		}
		
		previous.x += dirX;
		previous.y += dirY;
	}
}
