package sticklings.scene;

import javafx.geometry.BoundingBox;

/**
 * Represents something that can be collided with.
 * To be applied to entities for the movement controller to consider them.
 */
public interface Collideable {
	/**
	 * Gets the collision bounding box
	 * @return The bounding box
	 */
	public BoundingBox getBounds();

	/**
	 * Called upon collision start. The implementer may
	 * do any action at this time.
	 * @param entity The entity that collided with this object
	 * @return The action to be performed by the movement controller
	 */
	public Action onCollide(Entity entity);

	/**
	 * Represents an action to be taken by the movement controller
	 */
	public enum Action {
		/**
		 * Do nothing
		 */
		NONE,
		/**
		 * Prevent the entity from passing through
		 */
		BLOCK
	}
}
