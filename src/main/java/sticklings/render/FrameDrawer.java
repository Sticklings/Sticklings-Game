package sticklings.render;

import java.nio.IntBuffer;
import java.util.Arrays;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * A class that enables you to draw to an image
 */
public class FrameDrawer {
	private final int width;
	private final int height;
	
	private IntBuffer frameBuffer;
	
	private WritableImage frameDisplay;
	
	/**
	 * Creates a new frame drawer with the given size
	 * @param width The width in pixels
	 * @param height The height in pixels
	 */
	public FrameDrawer(int width, int height) {
		this.width = width;
		this.height = height;
		
		frameBuffer = IntBuffer.allocate(width*height);
		frameDisplay = new WritableImage(width, height);
	}
	
	/**
	 * Gets the image that can be used to display the frame buffer
	 * @return The image
	 */
	public Image getFrameImage() {
		return frameDisplay;
	}
	
	/**
	 * Begins the frame
	 */
	public void beginFrame() {
		Arrays.fill(frameBuffer.array(), 0xFF000000);
	}
	
	/**
	 * Ends the frame and pushes the contents to the image
	 */
	public void endFrame() {
		PixelWriter writer = frameDisplay.getPixelWriter();
		writer.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), frameBuffer, width);
	}
	
	/**
	 * Draws a texture at the given location
	 * @param texture The texture to draw
	 * @param x The x coord
	 * @param y The y coord
	 */
	public void draw(AbstractTexture texture, int x, int y) {
		IntBuffer raw = texture.getRawData();
		int[] data = raw.array();
		int[] out = frameBuffer.array();
		
		int texWidth = texture.getWidth();
		int texHeight = texture.getHeight();
		
		// Copy the image in
		for (int xx = Math.max(x, 0); xx < x+texWidth && xx < width; ++xx) {
			for (int yy = Math.max(y, 0); yy < y+texHeight && yy < height; ++yy) {
				// Tile the texture if needed
				int colour = data[(xx-x) + (yy-y) * texWidth];
				
				// Blend if alpha is not full
				if ((colour & 0xFF000000) != 0xFF000000) {
					colour = blend(colour, out[xx + yy * this.width]);
				}
				
				out[xx + yy * width] = colour;
			}
		}
	}
	
	/**
	 * Draws a texture at the given location
	 * @param texture The texture to draw
	 * @param x The x coord of the destination
	 * @param y The y coord of the destination
	 * @param srcX The x coord in the texture to start at
	 * @param srcY The y coord in the texture to start at
	 * @param width The width of the drawing
	 * @param height The height of the drawing
	 */
	public void draw(AbstractTexture texture, int x, int y, int srcX, int srcY, int width, int height) {
		IntBuffer raw = texture.getRawData();
		int[] data = raw.array();
		int[] out = frameBuffer.array();
		
		int texWidth = texture.getWidth();
		int texHeight = texture.getHeight();
		
		// Copy the image in
		for (int xx = Math.max(x, 0); xx < x+width && xx < this.width; ++xx) {
			for (int yy = Math.max(y, 0); yy < y+height && yy < this.height; ++yy) {
				// Tile the texture if needed
				int colour = data[((xx-x+srcX) % texWidth) + ((yy-y+srcY) % texHeight) * texWidth];
				
				// Blend if alpha is not full
				if ((colour & 0xFF000000) != 0xFF000000) {
					colour = blend(colour, out[xx + yy * this.width]);
				}
				
				out[xx + yy * this.width] = colour;
			}
		}
	}
	
	/**
	 * Linear Interpolation between the src and dest colour based on the alpha of the src.
	 * Output colour will have no alpha
	 * @param src The colour being applied
	 * @param dest The colour that was already there
	 * @return The blended colour
	 */
	private int blend(int src, int dest) {
		float alpha = (src >> 24) / 255f;
		int r = (int)(((dest >> 16) & 0xFF) * (1-alpha) + ((src >> 16) & 0xFF) * alpha);
		int g = (int)(((dest >> 8) & 0xFF) * (1-alpha) + ((src >> 8) & 0xFF) * alpha);
		int b = (int)(((dest) & 0xFF) * (1-alpha) + ((src) & 0xFF) * alpha);
		
		return 0xFF000000 | (r << 16) | (g << 8) | b;
	}
}
