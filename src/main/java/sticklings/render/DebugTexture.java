package sticklings.render;

import java.nio.IntBuffer;

public class DebugTexture extends AbstractTexture {
	private IntBuffer buffer;
	private int width;
	private int height;

	public DebugTexture(int width, int height) {
		this.width = width;
		this.height = height;

		buffer = IntBuffer.allocate(width * height);

		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				buffer.put(x + y * width, ((x / 8) % 2) - ((y / 8) % 2) != 0 ? 0xFF000000 : 0xFFFF00FF);
			}
		}
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public IntBuffer getRawData() {
		return buffer;
	}
}
