package sticklings.ui;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import sticklings.GameRenderer;
import sticklings.util.Location;

public class SceneWindow extends BorderPane {
	private int VIEWMOVE = 10;
	
	private final GameRenderer renderer;
	public SceneWindow(GameRenderer renderer) {
		this.renderer = renderer;
		
		ImageView view = new ImageView(renderer.getFrameImage());
		
		setCenter(view);
		setBottom(new Label("This is checking layout works"));
		
		setFocusTraversable(true);
		setOnKeyPressed(e -> {
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
		
		setOnMouseClicked(e -> {
			Location offset = renderer.getViewOffset();
			double x = e.getX() - offset.x;
			double y = e.getY() - offset.y;
			
			
		});
		
		setOnMouseMoved(e -> {
			Location offset = renderer.getViewOffset();
			double x = e.getX() - offset.x;
			double y = e.getY() - offset.y;
			
			
		});
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
}
