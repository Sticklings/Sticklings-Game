package sticklings;

import javafx.application.Application;
import javafx.stage.Stage;
import sticklings.levels.LevelLoader;
import sticklings.ui.LevelSelectScreen;
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
		
		// Launch the game
		Game game = new Game(screenManager);
		
		// Load all levels
		LevelLoader loader = new LevelLoader(SticklingsMain.class.getResource("/levels/"));
		loader.loadAll();
		
		LevelSelectScreen screen = new LevelSelectScreen(loader, game);
		screenManager.gotoScreen(screen);
//		
//		// Debug:
//		Level level = Iterables.getFirst(loader.getLevels(), null); 
//		
//		Scene scene = game.loadLevel(level);
//		
//		GameRenderer renderer = new GameRenderer(game.getTextureManager(), scene, WIDTH, 385);
//		
//		WorldView uitest = new WorldView(scene, renderer);
//		screenManager.gotoScreen(uitest);
		
//		GameTimer timer = new GameTimer(game, renderer);
//		timer.start();
		
		// Make it visible
		primaryStage.show();
	}
}
