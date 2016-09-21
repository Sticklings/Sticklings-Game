package sticklings;

import javafx.application.Application;
import javafx.stage.Stage;
import sticklings.levels.Level;
import sticklings.render.TextureManager;
import sticklings.scene.EntityTest;
import sticklings.scene.Scene;
import sticklings.terrain.TerrainTexture;
import sticklings.terrain.TerrainType;
import sticklings.ui.MainScreen;
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
		
		// DEBUG
		Level debugLevel = new Level("DEBUG", SticklingsMain.class.getResource("/debug/test-mask.png"));
		Scene scene = game.loadLevel(debugLevel);
		scene.addEntity(new EntityTest());
		
		TerrainTexture terrainTex = new TerrainTexture(scene.getTerrain());
		terrainTex.setTexture(TerrainType.AIR, game.getTextureManager().createBasic(SticklingsMain.class.getResourceAsStream("/debug/test-background.png")));
		terrainTex.setTexture(TerrainType.GROUND, game.getTextureManager().createBasic(SticklingsMain.class.getResourceAsStream("/debug/test-foreground.png")));
		terrainTex.setTexture(TerrainType.WATER, game.getTextureManager().createBasic(SticklingsMain.class.getResourceAsStream("/debug/test-water.png")));
		game.getTextureManager().addDynanic(terrainTex);
		
		GameRenderer renderer = new GameRenderer(game.getTextureManager(), scene, terrainTex, WIDTH, HEIGHT);
		
        MainScreen test = new MainScreen();
		screenManager.gotoScreen(test);
		
		GameTimer timer = new GameTimer(game, renderer);
		timer.start();
		
		// Make it visible
		primaryStage.show();
	}
}
