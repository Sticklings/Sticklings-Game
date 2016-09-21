package sticklings;

import javafx.scene.image.Image;
import sticklings.render.AbstractTexture;
import sticklings.render.FrameDrawer;
import sticklings.scene.Entity;
import sticklings.scene.Scene;
import sticklings.util.Location;

public class GameRenderer {
	private final FrameDrawer frameDrawer;
	private final Game game;
	
	public GameRenderer(Game game, int width, int height) {
		this.game = game;
		frameDrawer = new FrameDrawer(width, height);
	}
	
	public Image getFrameImage() {
		return frameDrawer.getFrameImage();
	}
	
	public void draw() {
		frameDrawer.beginFrame();
		if (game.getScene().isPresent()) {
			Scene scene = game.getScene().get();
			
			for (Entity entity : scene.getAllEntities()) {
				AbstractTexture texture = entity.getTexture();
				
				Location position = entity.getLocation();
				Location offset = entity.getTextureOffset();
				double x = position.x + offset.x;
				double y = position.y + offset.y;
				
				frameDrawer.draw(texture, (int)x, (int)y);
			}
		}
		frameDrawer.endFrame();
	}
}
