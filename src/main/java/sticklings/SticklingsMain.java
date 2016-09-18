package sticklings;

import javafx.application.Application;
import javafx.stage.Stage;
import sticklings.ui.ScreenManager;
import sticklings.ui.ScreenTest;
import sticklings.util.GameTimer;

/**
 * Main class for Sticklings
 */
public class SticklingsMain extends Application {
	/**
	 * Entry point
	 * @param args CL args, not used
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Setup the UI
		ScreenManager screenManager = new ScreenManager(primaryStage, 500, 500);
		
		primaryStage.setTitle("Sticklings");
		primaryStage.setResizable(false);
		
		// Launch the game
		Game game = new Game(screenManager);
		
		// DEBUG, Test screens
		ScreenTest test = new ScreenTest(screenManager);
		screenManager.gotoScreen(test);
		
		GameTimer timer = new GameTimer(game);
		timer.start();
		
		// Make it visible
		primaryStage.show();
	}
}
