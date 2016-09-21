package sticklings;

import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;
import sticklings.render.AbstractTexture;
import sticklings.render.FrameDrawer;
import sticklings.render.TextureManager;
import sticklings.scene.Entity;
import sticklings.scene.Scene;
import sticklings.terrain.TerrainTexture;
import sticklings.util.Location;

public class GameRenderer {
	private final FrameDrawer frameDrawer;
	private final TextureManager textureManager;
	private final Scene scene;
	
	private final int screenWidth;
	private final int screenHeight;
	private Location viewOffset;
	
	private TerrainTexture terrainTexture;
	
	public GameRenderer(TextureManager textureManager, Scene scene, TerrainTexture terrainTexture, int screenWidth, int screenHeight) {
		this.textureManager = textureManager;
		this.scene = scene;
		this.terrainTexture = terrainTexture;
		
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		frameDrawer = new FrameDrawer(screenWidth, screenHeight);
		viewOffset = new Location();
	}
	
	/**
	 * Sets the top left coord of the view, relative to the scene.
	 * The size of the view is the same as the screen size
	 * @param position The new offset
	 */
	public void setViewOffset(Location position) {
		this.viewOffset = position;
	}
	
	/**
	 * Gets the top left most coord of the view start relative to the scene.
	 * The size of the view is the same as the screen size
	 * @return The view offset location
	 */
	public Location getViewOffset() {
		return viewOffset;
	}
	
	public Image getFrameImage() {
		return frameDrawer.getFrameImage();
	}
	
	public void draw() {
		frameDrawer.beginFrame();
		
		BoundingBox viewport = new BoundingBox(viewOffset.x, viewOffset.y, screenWidth, screenHeight);
		
		// Render terrain
		frameDrawer.draw(terrainTexture, 0, 0, (int)viewport.getMinX(), (int)viewport.getMinY(), screenWidth, screenHeight);
		
		// Render entities
		for (Entity entity : scene.getAllEntities()) {
			AbstractTexture texture = entity.getTexture();
			
			Location position = entity.getLocation();
			Location offset = entity.getTextureOffset();
			double x = position.x + offset.x;
			double y = position.y + offset.y;
			
			// Viewport check
			if (!viewport.intersects(x, y, texture.getWidth(), texture.getHeight())) {
				continue;
			}
			
			frameDrawer.draw(texture, (int)(x - viewport.getMinX()), (int)(y - viewport.getMinY()));
		}
		
		frameDrawer.endFrame();
	}
}
