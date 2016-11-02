package sticklings.ui;

import com.google.common.base.Preconditions;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Manages the UI allowing various screens to be switched out as needed
 */
public class ScreenManager {
	private final Stage primaryStage;
	private final AnchorPane root;

	private Screen activeScreen;

	/**
	 * Creates the screen manager with the main window of the application
	 * 
	 * @param primaryStage The main window that will be used
	 */
	public ScreenManager(Stage primaryStage, double width, double height) {
		this.primaryStage = primaryStage;

		root = new AnchorPane();

		// Setup the scene
		Scene stageScene = new Scene(root, width, height);
		primaryStage.setScene(stageScene);
	}

	/**
	 * Gets the actively displayed screen.
	 * 
	 * @return The screen instance. May be null but only before the first screen is shown
	 */
	public Screen getActiveScreen() {
		return activeScreen;
	}

	/**
	 * Changes the active screen
	 * 
	 * @param screen The next screen to display
	 */
	public void gotoScreen(Screen screen) {
		Preconditions.checkNotNull(screen, "Cannot change to NULL screen");

		// Deactivate the old screen
		if (activeScreen != null) {
			activeScreen.onHide();
			root.getChildren().clear();
		}

		// Create and activate new screen
		Parent component = screen.initialize();
		root.getChildren().add(component);
		AnchorPane.setTopAnchor(component, 0.0);
		AnchorPane.setBottomAnchor(component, 0.0);
		AnchorPane.setLeftAnchor(component, 0.0);
		AnchorPane.setRightAnchor(component, 0.0);

		activeScreen = screen;

		screen.onShow();
	}

	/**
	 * Shortcut to update the active screen
	 * 
	 * @param deltaTime The time in seconds between last update and current
	 */
	public void update(double deltaTime) {
		if (activeScreen != null) {
			activeScreen.update(deltaTime);
		}
	}
}
