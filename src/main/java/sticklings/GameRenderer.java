package sticklings;

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
	
	private TerrainTexture terrainTexture;
	
	public GameRenderer(TextureManager textureManager, Scene scene, TerrainTexture terrainTexture, int screenWidth, int screenHeight) {
		this.textureManager = textureManager;
		this.scene = scene;
		this.terrainTexture = terrainTexture;
		
		frameDrawer = new FrameDrawer(screenWidth, screenHeight);
	}
	
	public Image getFrameImage() {
		return frameDrawer.getFrameImage();
	}
	
	public void draw() {
		frameDrawer.beginFrame();
		
		// TODO: View port
		
		// Render terrain
		frameDrawer.draw(terrainTexture, 0, 0);
		
		// Render entities
		for (Entity entity : scene.getAllEntities()) {
			AbstractTexture texture = entity.getTexture();
			
			Location position = entity.getLocation();
			Location offset = entity.getTextureOffset();
			double x = position.x + offset.x;
			double y = position.y + offset.y;
			
			frameDrawer.draw(texture, (int)x, (int)y);
		}
		
		frameDrawer.endFrame();
	}
}
