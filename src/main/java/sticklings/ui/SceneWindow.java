package sticklings.ui;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import sticklings.GameRenderer;
import sticklings.scene.Scene;
import sticklings.scene.sticklings.BasicStickling;
import sticklings.scene.sticklings.Stickling;
import sticklings.scene.sticklings.SticklingType;
import sticklings.util.Location;

public class SceneWindow extends BorderPane {
	private int VIEWMOVE = 10;
	
	private final Scene scene;
	private final GameRenderer renderer;
	private SticklingType selectedType;
	
	public SceneWindow(Scene scene, GameRenderer renderer) {
		this.scene = scene;
		this.renderer = renderer;
		
		ImageView view = new ImageView(renderer.getFrameImage());
		
		setCenter(view);
		
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
		
		setOnMouseClicked(this::mousePressed);
		
		setOnMouseMoved(e -> {
			Location offset = renderer.getViewOffset();
			double x = e.getX() - offset.x;
			double y = e.getY() - offset.y;
			
			
		});
	}
	
	private void mousePressed(MouseEvent event) {
		if (selectedType == null) {
			return;
		}
		
		Location offset = renderer.getViewOffset();
		double x = event.getX() + offset.x;
		double y = event.getY() + offset.y;
		
		// Find the stickling that is under the cursor. The stickling must be a basic stickling or
		// the selected type must be exploder
		Stickling selected = null;
		for (Stickling stickling : scene.findEntities(Stickling.class)) {
			if (selectedType != SticklingType.Exploder && !(stickling instanceof BasicStickling)) {
				continue;
			}
			
			if (stickling.getBounds().contains(x, y)) {
				selected = stickling;
				break;
			}
		}
		
		if (selected == null) {
			return;
		}
		
		// Change its type
		if (selectedType == SticklingType.Exploder) {
			// TODO: Special case
		} else {
			Stickling newStickling = selectedType.create();
			newStickling.copyFrom(selected);
			
			scene.addEntity(newStickling);
			selected.remove();
			
			// TODO: Reduce available count
		}
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
	
	public void selectType(SticklingType type) {
		selectedType = type;
	}
}
