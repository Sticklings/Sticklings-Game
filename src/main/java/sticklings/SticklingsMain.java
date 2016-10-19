package sticklings;

import com.google.common.collect.Iterables;

import javafx.application.Application;
import javafx.stage.Stage;
import sticklings.levels.Level;
import sticklings.levels.LevelLoader;
import sticklings.levels.SticklingAvailability;
import sticklings.scene.EndGate;
import sticklings.scene.Scene;
import sticklings.scene.StartGate;
import sticklings.scene.sticklings.SticklingType;
import sticklings.terrain.TerrainTexture;
import sticklings.terrain.TerrainType;
import sticklings.ui.ScreenManager;
import sticklings.ui.WorldView;
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
		
		// Load all levels
		LevelLoader loader = new LevelLoader(SticklingsMain.class.getResource("/levels/"));
		loader.loadAll();
		
		// Debug:
		Level level = Iterables.getFirst(loader.getLevels(), null); 
		
		Scene scene = game.loadLevel(level);
		
		GameRenderer renderer = new GameRenderer(game.getTextureManager(), scene, WIDTH, 385);
		
		WorldView uitest = new WorldView(scene, renderer);
		screenManager.gotoScreen(uitest);
		
		GameTimer timer = new GameTimer(game, renderer);
		timer.start();
		
		// Make it visible
		primaryStage.show();
	}
}
