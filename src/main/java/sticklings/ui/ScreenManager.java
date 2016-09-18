package sticklings.ui;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Manages the UI allowing various screens to be switched out as needed 
 */
public class ScreenManager {
	private final Stage primaryStage;
	
	/**
	 * Creates the screen manager with the main window of the application
	 * @param primaryStage The main window that will be used
	 */
	public ScreenManager(Stage primaryStage, double width, double height) {
		this.primaryStage = primaryStage;
		
		// TODO: Replace with something useful
		Group root = new Group();
		
		// Setup the scene
		Scene stageScene = new Scene(root, width, height);
		primaryStage.setScene(stageScene);
	}
	
	public void gotoScreen(Screen screen) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
