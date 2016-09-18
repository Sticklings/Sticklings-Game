package sticklings.render;

import java.nio.IntBuffer;

/**
 * Represents a texture that is empty
 */
public class NullTexture extends AbstractTexture {
	private static NullTexture instance;
	
	/**
	 * Gets the NullTexture instance
	 * @return The instance
	 */
	public static NullTexture get() {
		if (instance == null) {
			instance = new NullTexture();
		}
		
		return instance;
	}
	
	private final IntBuffer buffer;
	
	private NullTexture() {
		buffer = IntBuffer.allocate(1);
		buffer.put(0, 0x00000000);
	}
	
	@Override
	public int getWidth() {
		return 1;
	}

	@Override
	public int getHeight() {
		return 1;
	}

	@Override
	public IntBuffer getRawData() {
		return buffer;
	}
	
}
