package sticklings.render;

import java.nio.IntBuffer;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;

/**
 * Represents a static image based texture
 */
public class BasicTexture extends AbstractTexture {
	private final Image image;
	private final IntBuffer texData;

	/**
	 * Creates a basic texture from an image
	 * 
	 * @param image the image to build from
	 */
	public BasicTexture(Image image) {
		this.image = image;

		texData = IntBuffer.allocate(getWidth() * getHeight());
		image.getPixelReader().getPixels(0, 0, getWidth(), getHeight(), PixelFormat.getIntArgbInstance(), texData, getWidth());
	}

	@Override
	public int getWidth() {
		return (int) image.getWidth();
	}

	@Override
	public int getHeight() {
		return (int) image.getHeight();
	}

	@Override
	public IntBuffer getRawData() {
		return texData;
	}
}
