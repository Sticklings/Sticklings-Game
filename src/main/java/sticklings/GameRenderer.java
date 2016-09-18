package sticklings;

import javafx.scene.image.Image;
import sticklings.render.AbstractTexture;
import sticklings.render.FrameDrawer;
import sticklings.scene.Entity;
import sticklings.scene.Scene;

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
				// TODO: How to decide where to render the texture
				frameDrawer.draw(texture, entity.getX() - texture.getWidth()/2, entity.getY() - texture.getHeight()/2);
			}
		}
		frameDrawer.endFrame();
	}
}
