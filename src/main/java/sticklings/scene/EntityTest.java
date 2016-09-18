package sticklings.scene;

import sticklings.render.DebugTexture;

public class EntityTest extends Entity {
	private int dirX;
	private int dirY;
	
	public EntityTest() {
		setTexture(new DebugTexture(32, 32));
		
		dirX = 1;
		dirY = 1;
	}
	
	@Override
	public void update(double deltaTime) {
		if (getX() < 0) {
			dirX = 1;
		}
		
		if (getY() < 0) {
			dirY = 1;
		}
		
		if (getX() > 500) {
			dirX = -1;
		}
		
		if (getY() > 400) {
			dirY = -1;
		}
		
		setLocation(getX() + dirX, getY() + dirY);
	}
}
