package sticklings;

import javafx.application.Application;
import javafx.stage.Stage;
import sticklings.levels.Level;
import sticklings.scene.EntityTest;
import sticklings.scene.Scene;
import sticklings.ui.ScreenManager;
import sticklings.ui.ScreenTest;
import sticklings.util.GameTimer;

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
		
		// Launch the game
		Game game = new Game(screenManager);
		GameRenderer renderer = new GameRenderer(game, WIDTH, HEIGHT);
		
		// DEBUG
		Level debugLevel = new Level("DEBUG", SticklingsMain.class.getResource("/debug/test-mask.png"));
		Scene scene = game.loadLevel(debugLevel);
		scene.addEntity(new EntityTest());
		
		ScreenTest test = new ScreenTest(renderer.getFrameImage());
		screenManager.gotoScreen(test);
		
		GameTimer timer = new GameTimer(game, renderer);
		timer.start();
		
		// Make it visible
		primaryStage.show();
	}
}
