package sticklings.scene;

import com.google.common.base.Preconditions;

import sticklings.render.AbstractTexture;
import sticklings.render.NullTexture;

/**
 * Represents an entity in a scene
 */
public abstract class Entity {
	private int posX;
	private int posY;
	private AbstractTexture texture;
	
	private int entityId;
	private Scene scene;
	
	/**
	 * Creates a new entity.
	 */
	public Entity() {
		this.texture = NullTexture.get();
	}
	
	/**
	 * Sets the scene this entity belongs to
	 * @param scene The scene object
	 * @param entityId The id of the entity within the scene
	 */
	void setScene(Scene scene, int entityId) {
		Preconditions.checkNotNull(scene);
		
		this.entityId = entityId;
		this.scene = scene;
	}
	
	/**
	 * Gets the ID of this entity.
	 * All entities have an ID, it may or may not be added to the scene
	 * @return The ID
	 */
	public int getEntityId() {
		return entityId;
	}
	
	/**
	 * Gets the X coordinate of the entity
	 * @return The X coord
	 */
	public final int getX() {
		return posX;
	}
	
	/**
	 * Gets the Y coordinate of the entity
	 * @return The Y coord
	 */
	public final int getY() {
		return posY;
	}
	
	/**
	 * Sets the X coord of the entity
	 * @param x The new X coord
	 */
	public void setX(int x) {
		posX = x;
	}
	
	/**
	 * Sets the Y coord of the entity
	 * @param y The new Y coord
	 */
	public void setY(int y) {
		posY = y;
	}
	
	/**
	 * Sets the X and Y position of the entity
	 * @param x The new X coord
	 * @param y The new Y coord
	 */
	public void setLocation(int x, int y) {
		posX = x;
		posY = y;
	}
	
	/**
	 * Gets the owning scene for this entity
	 * @return The scene
	 */
	public final Scene getScene() {
		return scene;
	}
	
	/**
	 * Gets the current texture for this entity
	 * @return The texture
	 */
	public AbstractTexture getTexture() {
		return texture;
	}
	
	/**
	 * Sets the current texture for this entity.
	 * @param texture The new texture, null will use NullTexture
	 */
	public void setTexture(AbstractTexture texture) {
		if (texture == null) {
			this.texture = NullTexture.get();
		} else {
			this.texture = texture;
		}
	}
	
	/**
	 * Marks the entity for removal in the scene.
	 */
	public final void remove() {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	/**
	 * Updates the entity
	 * @param deltaTime The time in seconds between the last update and this one
	 */
	public abstract void update(double deltaTime);
}
