package sticklings;

import java.io.IOException;
import java.util.Optional;

import com.google.common.base.Preconditions;

import sticklings.levels.Level;
import sticklings.render.TextureManager;
import sticklings.scene.EndGate;
import sticklings.scene.Scene;
import sticklings.scene.StartGate;
import sticklings.terrain.TerrainTexture;
import sticklings.terrain.TerrainType;
import sticklings.ui.ScreenManager;
import sticklings.util.ClasspathTextureSource;

public class Game {
	public static final double SPEED_NORMAL = 1;
	public static final double SPEED_FAST = 3;
	public static final double SPEED_FASTEST = 6;
	
	private final ScreenManager screenManager;
	private final TextureManager textureManager;
	
	private Level currentLevel;
	private Scene scene;
	
	private double gameSpeed;
	
	/**
	 * Constructs a new game with the needed managers
	 * @param screenManager The screen manager for updating the UI
	 * @param renderer The game renderer for updating the scene
	 */
	public Game(ScreenManager screenManager) {
		this.screenManager = screenManager;
		this.textureManager = new TextureManager();
		
		textureManager.addTextureSource(new ClasspathTextureSource(Game.class, textureManager));
		
		instance = this;
	}
	
	/**
	 * Sets the games speed as a percent of real-time.
	 * 1 = realtime
	 * @param speed The new speed
	 */
	public void setGameSpeed(double speed) {
		Preconditions.checkArgument(speed >= 0);
		gameSpeed = speed;
	}
	
	/**
	 * Gets the games speed as a percent of real-time.
	 * 1 = realtime
	 * @return The games speed
	 */
	public double getGameSpeed() {
		return gameSpeed;
	}
	
	/**
	 * Updates the game, progressing the game by {@code deltaTime} seconds
	 * @param deltaTime The time between start of last frame and this frame in seconds
	 */
	public void update(double deltaTime) {
		screenManager.update(deltaTime*gameSpeed);
		textureManager.update(deltaTime*gameSpeed);
		
		if (scene != null) {
			scene.update(deltaTime*gameSpeed);
		}
		// TODO: Game update method
	}
	
	/**
	 * Loads a level
	 * @param level The level to load
	 * @return The scene holding the loaded state
	 */
	public Scene loadLevel(Level level) throws IOException {
		Preconditions.checkNotNull(level);
		
		// TODO: Cleanup existing scene
		scene = Scene.fromLevel(level);
		currentLevel = level;
		
		// Add the start and end gates
		StartGate gate = new StartGate();
		gate.setLocation(level.getStartLocation());
		scene.addEntity(gate);
		
		EndGate end = new EndGate();
		end.setLocation(level.getEndLocation());
		scene.addEntity(end);
		
		return scene;
	}
	
	/**
	 * Gets the current scene if any
	 * @return An optional that may or may not have a scene 
	 */
	public Optional<Scene> getScene() {
		return Optional.ofNullable(scene);
	}
	
	/**
	 * Gets the current level if any
	 * @return The current level
	 */
	public Optional<Level> getLevel() {
		return Optional.ofNullable(currentLevel);
	}
	
	/**
	 * Gets the active screen manager.
	 * Use this to transition between screens and control the game window
	 * @return The screen manager
	 */
	public ScreenManager getScreenManager() {
		return screenManager;
	}
	
	/**
	 * Gets the texture manager
	 * @return The texture manager
	 */
	public TextureManager getTextureManager() {
		return textureManager;
	}
	
	private static Game instance;
	/**
	 * Gets the games current instance
	 * @return
	 */
	public static Game getInstance() {
		return instance;
	}
}
