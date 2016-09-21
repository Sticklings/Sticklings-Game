package sticklings.terrain;

import java.nio.IntBuffer;
import java.util.Arrays;

import javafx.geometry.BoundingBox;
import sticklings.render.DynamicTexture;
import sticklings.render.NullTexture;
import sticklings.render.AbstractTexture;

/**
 * Allows the terrain data to be rendered to a texture for display within the scene.
 */
public class TerrainTexture extends DynamicTexture {
	private final TerrainData terrainData;
	private final AbstractTexture[] terrainTextures;
	private final IntBuffer output;
	
	private boolean isInitial;
	
	// During update only
	private IntBuffer[] textureBuffers;
	
	/**
	 * Creates the terrain texture.
	 * Must provide one texture for each TerrainType
	 * @param terrainData The terrain data
	 */
	public TerrainTexture(TerrainData terrainData) {
		this.terrainData = terrainData;
		
		terrainTextures = new AbstractTexture[TerrainType.values().length];
		textureBuffers = new IntBuffer[terrainTextures.length];
		
		Arrays.fill(terrainTextures, NullTexture.get());
		
		output = IntBuffer.allocate(terrainData.getWidth() * terrainData.getHeight());
		isInitial = true;
	}
	
	/**
	 * Sets the texture for the terrain type 
	 * @param type The type of terrain
	 * @param texture The texture to use
	 */
	public void setTexture(TerrainType type, AbstractTexture texture) {
		if (texture == null) {
			throw new NullPointerException();
		}
		
		terrainTextures[type.ordinal()] = texture;
	}
	
	@Override
	public int getHeight() {
		return terrainData.getHeight();
	}
	
	@Override
	public int getWidth() {
		return terrainData.getWidth();
	}
	
	/**
	 * Re-renders part of the terrain texture
	 * @param left The x coord to start at
	 * @param top The y coord to start at
	 * @param width The width of the rect to render
	 * @param height The height of the rect to render
	 */
	public void update(int left, int top, int width, int height) {
		if (left < 0) {
			width += left;
			left = 0;
		}
		
		if (top < 0) {
			height += top;
			top = 0;
		}
		
		if (left + width > terrainData.getWidth()) {
			width = terrainData.getWidth() - left;
		}
		
		if (top + height > terrainData.getHeight()) {
			height = terrainData.getHeight() - top;
		}
		
		// Splice them together based on the terrain data
		TerrainType[] mask = terrainData.lockRead();
		
		for (int x = left; x < left + width; ++x) {
			for (int y = top; y < top + height; ++y) {
				int value = get(mask[x + y * terrainData.getWidth()], x, y);
				output.put(x + y * getWidth(), value);
			}
		}
		
		terrainData.unlockRead();
	}
	
	private int get(TerrainType type, int x, int y) {
		AbstractTexture texture = terrainTextures[type.ordinal()];
		// Aquire direct access
		IntBuffer buffer = textureBuffers[type.ordinal()];
		if (buffer == null) {
			buffer = texture.getRawData();
			textureBuffers[type.ordinal()] = buffer;
		}
		
		// Get the texture value
		return buffer.get((x % texture.getWidth()) + (y % texture.getHeight()) * texture.getWidth());
	}
	
	@Override
	public void update(double deltaTime) {
		// Clear the texture buffers for dynamic and animated textures
		Arrays.fill(textureBuffers, null);
		
		BoundingBox region = terrainData.getAndClearDirtyRegion();
		if (isInitial) {
			isInitial = false;
			update(0, 0, terrainData.getWidth(), terrainData.getHeight());
		} else  if (region != null) {
			update((int)region.getMinX(), (int)region.getMinY(), (int)region.getWidth()+1, (int)region.getHeight()+1);
		}
	}
	
	@Override
	public IntBuffer getRawData() {
		return output;
	}
}
