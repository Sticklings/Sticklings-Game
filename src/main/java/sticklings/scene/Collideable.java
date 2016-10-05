package sticklings.scene;

import javafx.geometry.BoundingBox;

public interface Collideable {
	public BoundingBox getBounds();
	
	public Action onCollide(Entity entity);
	
	public enum Action {
		NONE,
		BLOCK
	}
}
