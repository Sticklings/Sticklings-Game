package sticklings;

import javafx.application.Application;
import javafx.stage.Stage;
import sticklings.levels.Level;
import sticklings.scene.EndGate;
import sticklings.scene.Scene;
import sticklings.scene.StartGate;
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
		
		// DEBUG
		Level debugLevel = new Level("DEBUG", SticklingsMain.class.getResource("/debug/test-mask.png"), 30, 20);
		
		Scene scene = game.loadLevel(debugLevel);
		StartGate gate = new StartGate();
		gate.setLocation(200, 180);
		scene.addEntity(gate);
		
		EndGate end = new EndGate();
		end.setLocation(1190, 438);
		scene.addEntity(end);
		
		TerrainTexture terrainTex = new TerrainTexture(scene.getTerrain());
		terrainTex.setTexture(TerrainType.AIR, game.getTextureManager().createBasic(SticklingsMain.class.getResourceAsStream("/debug/test-background.png")));
		terrainTex.setTexture(TerrainType.GROUND, game.getTextureManager().createBasic(SticklingsMain.class.getResourceAsStream("/debug/test-foreground.png")));
		terrainTex.setTexture(TerrainType.WATER, game.getTextureManager().createBasic(SticklingsMain.class.getResourceAsStream("/debug/test-water.png")));
		game.getTextureManager().addDynanic(terrainTex);
		
		GameRenderer renderer = new GameRenderer(game.getTextureManager(), scene, terrainTex, WIDTH, 385);
		
		WorldView uitest = new WorldView(scene, renderer);
		screenManager.gotoScreen(uitest);
		
		GameTimer timer = new GameTimer(game, renderer);
		timer.start();
		
		// Make it visible
		primaryStage.show();
	}
}
