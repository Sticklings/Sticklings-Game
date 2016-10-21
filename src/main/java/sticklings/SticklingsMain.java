package sticklings;

import javafx.application.Application;
import javafx.stage.Stage;
import sticklings.levels.LevelLoader;
import sticklings.ui.LevelSelectScreen;
import sticklings.ui.MainScreen;
import sticklings.ui.ScreenManager;

/**
 * Main class for Sticklings
 */
public class SticklingsMain extends Application {
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	
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
		ScreenManager screenManager = new ScreenManager(primaryStage, WIDTH, HEIGHT);
		
		primaryStage.setTitle("Sticklings");
		primaryStage.setResizable(false);
		
		// Load all levels
				LevelLoader loader = new LevelLoader(SticklingsMain.class.getResource("/levels/"));
				loader.loadAll();
		
		// Launch the game
		Game game = new Game(screenManager, loader);
		
		MainScreen mainMenu = new MainScreen(game);
		screenManager.gotoScreen(mainMenu);
		
		// Make it visible
		primaryStage.show();
	}
}
