package sticklings.ui;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import sticklings.GameRenderer;
import sticklings.scene.Scene;
import sticklings.scene.sticklings.BasicStickling;
import sticklings.scene.sticklings.Stickling;
import sticklings.scene.sticklings.SticklingType;
import sticklings.util.Location;

/**
 * Displays the game world and handles game input
 */
public class SceneWindow extends BorderPane {
	private int VIEWMOVE = 10;
	private static final double PAN_SCALE = 1;

	private final Scene scene;
	private final GameRenderer renderer;
	private SticklingType selectedType;

	// Panning
	private boolean isPanning;
	private double panMouseX;
	private double panMouseY;
	private Location panInitial;

	public SceneWindow(Scene scene, GameRenderer renderer) {
		this.scene = scene;
		this.renderer = renderer;

		ImageView view = new ImageView(renderer.getFrameImage());

		setCenter(view);

		setFocusTraversable(true);

		Location loc = new Location();
		loc = scene.getLevel().getStartLocation().copy();
		loc.y -= renderer.getScreenHeight() / 2;
		loc.x -= renderer.getScreenWidth() / 2;
		if (loc.x < 0) {
			loc.x = 0;
		}
		if (loc.y < 0) {
			loc.y = 0;
		}
		if (loc.y > (scene.getHeight() - renderer.getScreenHeight())) {
			loc.y = (scene.getHeight() - renderer.getScreenHeight());
		}
		if (loc.x > (scene.getWidth() - renderer.getScreenWidth())) {
			loc.x = scene.getWidth() - renderer.getScreenWidth();
		}

		renderer.setViewOffset(loc);

		// Pan
		setOnMousePressed(e -> {
			if (e.getButton() == MouseButton.SECONDARY) {
				isPanning = true;

				panMouseX = e.getX();
				panMouseY = e.getY();
				panInitial = renderer.getViewOffset();
				// setCursor(Cursor.MOVE);
			}
		});

		setOnMouseReleased(e -> {
			if (e.getButton() == MouseButton.SECONDARY) {
				isPanning = false;
				// setCursor(Cursor.DEFAULT);
			}
		});

		setOnMouseDragged(e -> {
			if (isPanning) {
				double deltaX = e.getX() - panMouseX;
				double deltaY = e.getY() - panMouseY;

				double targetX = panInitial.x - deltaX * PAN_SCALE;
				double targetY = panInitial.y - deltaY * PAN_SCALE;

				double maxX = Math.max(0, scene.getWidth() - renderer.getScreenWidth());
				double maxY = Math.max(0, scene.getHeight() - renderer.getScreenHeight());

				targetX = Math.min(maxX, Math.max(0, targetX));
				targetY = Math.min(maxY, Math.max(0, targetY));

				renderer.setViewOffset(new Location(targetX, targetY));
			}
		});
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
			double x = e.getX() + offset.x;
			double y = e.getY() + offset.y;

		});
	}

	private void mousePressed(MouseEvent event) {
		if (selectedType == null) {
			return;
		}

		// Dont permit changing type when none available
		if (scene.getSticklingAvailability().getRemaining(selectedType) == 0) {
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
			Stickling newStickling = selectedType.create();
			newStickling.copyFrom(selected);

			scene.addEntity(newStickling);
			selected.remove();
		} else {
			Stickling newStickling = selectedType.create();
			newStickling.copyFrom(selected);

			scene.addEntity(newStickling);
			selected.remove();

			// TODO: Reduce available count
		}

		scene.getSticklingAvailability().adjustUsed(selectedType, 1);
	}

	/**
	 * Moves the viewport by the given amount
	 * 
	 * @param dX the change in X value
	 * @param dY the change in Y value
	 */
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

	/**
	 * Selects the type used on click
	 * 
	 * @param type The stickling type, can be null
	 */
	public void selectType(SticklingType type) {
		selectedType = type;
	}
}
