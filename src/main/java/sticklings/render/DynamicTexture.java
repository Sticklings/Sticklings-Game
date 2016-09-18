package sticklings.render;

/**
 * Represents a texture that is dynamically updated.
 * How the texture is updated is up to the implementation.
 */
public abstract class DynamicTexture extends AbstractTexture {
	/**
	 * Called to update the texture if needed
	 * @param deltaTime The time in seconds between the last update and this one
	 */
	public abstract void update(double deltaTime);
}
