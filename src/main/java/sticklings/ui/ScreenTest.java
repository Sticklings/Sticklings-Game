package sticklings.ui;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import sticklings.GameRenderer;
import sticklings.util.Location;

public class ScreenTest extends Screen {
	private static final int VIEWMOVE = 10;
	
	private GameRenderer renderer;
	private Image frameImage;
	
	public ScreenTest(GameRenderer renderer) {
		this.frameImage = renderer.getFrameImage();
		this.renderer = renderer;
	}
	
	@Override
	public Parent initialize() {
		BorderPane root = new BorderPane();
		ImageView view = new ImageView(frameImage);
		
		root.setCenter(view);
		root.setBottom(new Label("This is checking layout works"));
		
		root.setFocusTraversable(true);
		root.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case RIGHT:
				moveViewport(VIEWMOVE, 0);
				break;
			case LEFT:
				moveViewport(-VIEWMOVE, 0);
				break;
			case UP:
				moveViewport(0, -VIEWMOVE);
				break;
			case DOWN:
				moveViewport(0, VIEWMOVE);
				break;
			}
		});
		
		return root;
	}
	
	private void moveViewport(int dX, int dY) {
		Location viewLocation = renderer.getViewOffset();
		viewLocation.x += dX;
		viewLocation.y += dY;
		
		if (viewLocation.x < 0) {
			viewLocation.x = 0;
		}
		
		if (viewLocation.y < 0) {
			viewLocation.y = 0;
		}
	}

	@Override
	public void onShow() {
	}

	@Override
	public void onHide() {
	}

	@Override
	public void update(double deltaTime) {
	}
}
