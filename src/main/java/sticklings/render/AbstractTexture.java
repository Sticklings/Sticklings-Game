package sticklings.render;

import java.nio.IntBuffer;

/**
 * Represents an image that can be drawn with the frame drawer
 */
public abstract class AbstractTexture {
	/**
	 * Gets the width in pixels of the image
	 * 
	 * @return The width in pixels
	 */
	public abstract int getWidth();

	/**
	 * Gets the height in pixels of the image
	 * 
	 * @return The height in pixels
	 */
	public abstract int getHeight();

	/**
	 * Gets the texture image data in ARGB format. This is used for rendering the texture
	 * 
	 * @return The pixel buffer in width first order
	 */
	public abstract IntBuffer getRawData();
}
