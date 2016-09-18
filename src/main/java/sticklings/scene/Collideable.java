package sticklings.scene;

import sticklings.util.Bounds;

public interface Collideable {
	public Bounds getBounds();
	
	public Action onCollide(Entity entity);
	
	public enum Action {
		NONE,
		BLOCK
	}
}
