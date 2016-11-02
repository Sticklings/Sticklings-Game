package sticklings.scene;

import com.google.common.base.Preconditions;

import javafx.geometry.BoundingBox;
import sticklings.render.AbstractTexture;
import sticklings.render.NullTexture;
import sticklings.util.Location;

/**
 * Represents an entity in a scene
 */
public abstract class Entity {
	private Location pos;
	private AbstractTexture texture;
	private Location textureOffset;
	private int width;
	private int height;

	private int entityId;
	private Scene scene;

	/**
	 * Creates a new entity.
	 */
	public Entity() {
		texture = NullTexture.get();
		textureOffset = new Location();
		pos = new Location();
		width = 1;
		height = 1;
	}

	/**
	 * Sets the scene this entity belongs to
	 * 
	 * @param scene The scene object
	 * @param entityId The id of the entity within the scene
	 */
	void setScene(Scene scene, int entityId) {
		Preconditions.checkNotNull(scene);

		this.entityId = entityId;
		this.scene = scene;
	}

	/**
	 * Gets the ID of this entity. All entities have an ID, it may or may not be added to the scene
	 * 
	 * @return The ID
	 */
	public int getEntityId() {
		return entityId;
	}

	/**
	 * Gets the location of this entity
	 * 
	 * @return The location
	 */
	public Location getLocation() {
		return pos;
	}

	/**
	 * Sets the position of this entity
	 * 
	 * @param location The new position
	 */
	public void setLocation(Location location) {
		Preconditions.checkNotNull(location);
		pos = location;
	}

	/**
	 * Sets the X and Y position of the entity
	 * 
	 * @param x The new X coord
	 * @param y The new Y coord
	 */
	public void setLocation(int x, int y) {
		pos = new Location(x, y);
	}

	/**
	 * Sets the bounds of the entity
	 * 
	 * @param width The width of it
	 * @param height The height of it
	 */
	protected void setBounds(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Gets the bounding box of the entity
	 * 
	 * @return The bounding box
	 */
	public BoundingBox getBounds() {
		return new BoundingBox(pos.x - width / 2, pos.y - height / 2, width, height);
	}

	/**
	 * Gets the owning scene for this entity
	 * 
	 * @return The scene
	 */
	public final Scene getScene() {
		return scene;
	}

	/**
	 * Gets the current texture for this entity
	 * 
	 * @return The texture
	 */
	public AbstractTexture getTexture() {
		return texture;
	}

	/**
	 * Sets the current texture for this entity.
	 * 
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
	 * Shortcut for setting the texture and texture offset
	 * 
	 * @param texture The texture to set
	 * @param offset The offset of the texture
	 */
	public void setTexture(AbstractTexture texture, Location offset) {
		setTexture(texture);
		setTextureOffset(offset);
	}

	/**
	 * Changes the relative position of the texture to the entities location
	 * 
	 * @param offset The offset. null to reset
	 */
	public void setTextureOffset(Location offset) {
		if (offset == null) {
			textureOffset = new Location();
		} else {
			textureOffset = offset;
		}
	}

	/**
	 * Gets the texture offset
	 * 
	 * @return The offset
	 */
	public Location getTextureOffset() {
		return textureOffset;
	}

	/**
	 * Marks the entity for removal in the scene.
	 */
	public final void remove() {
		scene.removeEntity(getEntityId());
	}

	/**
	 * Updates the entity
	 * 
	 * @param deltaTime The time in seconds between the last update and this one
	 */
	public abstract void update(double deltaTime);
}
