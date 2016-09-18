package sticklings.render;

import java.nio.IntBuffer;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritablePixelFormat;

/**
 * Represents an animated texture based off of a sprite sheet.
 */
class AnimatedTexture extends DynamicTexture {
	private static final double MILLIS_PER_SECOND = 1000.0;
	
	private IntBuffer[] frames;
	private double frameTime;
	
	private int frameWidth;
	private int frameHeight;
	
	private double currentFrameTime;
	private int currentFrame;
	
	/**
	 * Creates a new animated texture
	 * @param image The image to use
	 * @param settings The animation settings
	 */
	public AnimatedTexture(Image image, AnimationSettings settings) {
		// Slice the image up into frames
		int imageWidth = (int)image.getWidth();
		int imageHeight = (int)image.getHeight();
		
		// Frame settings
		int frameCount = settings.tilesX * settings.tilesY;
		frames = new IntBuffer[frameCount];
		
		frameWidth = imageWidth / settings.tilesX;
		frameHeight = imageHeight / settings.tilesY;
		frameTime = settings.frameTime / MILLIS_PER_SECOND;
		currentFrameTime = 0;
		
		// Load up each frame
		WritablePixelFormat<IntBuffer> format = PixelFormat.getIntArgbInstance();
		int frameX = 0;
		int frameY = 0;
		
		for (int i = 0; i < frameCount; ++i) {
			// Extract the frame data
			IntBuffer frameBuffer = IntBuffer.allocate(frameWidth * frameHeight);
			image.getPixelReader().getPixels(frameX, frameY, frameWidth, frameHeight, format, frameBuffer, frameWidth);
			frames[i] = frameBuffer;
			
			// Move to next frame
			if (settings.isVertical) {
				++frameY;
				
				if (frameY + frameHeight > imageHeight) {
					frameY = 0;
					++frameX;
				}
			} else {
				++frameX;
				
				if (frameX + frameWidth > imageWidth) {
					frameX = 0;
					++frameX;
				}
			}
		}
	}
	
	@Override
	public int getWidth() {
		return frameWidth;
	}
	
	@Override
	public int getHeight() {
		return frameHeight;
	}
	
	@Override
	public IntBuffer getRawData() {
		return frames[currentFrame];
	}
	
	@Override
	public void update(double deltaTime) {
		currentFrameTime += deltaTime;
		
		// Switch to the next frame if the current frame
		// has been displayed long enough
		while (currentFrameTime >= frameTime) {
			currentFrameTime -= frameTime;
			++currentFrame;
			
			if (currentFrame >= frames.length) {
				currentFrame = 0;
			}
		}
	}
}
