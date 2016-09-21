package sticklings;

import java.io.IOException;
import java.util.Optional;

import com.google.common.base.Preconditions;

import sticklings.levels.Level;
import sticklings.render.TextureManager;
import sticklings.scene.Scene;
import sticklings.ui.ScreenManager;

public class Game {
	private final ScreenManager screenManager;
	private final TextureManager textureManager;
	
	private Level currentLevel;
	private Scene scene;
	
	/**
	 * Constructs a new game with the needed managers
	 * @param screenManager The screen manager for updating the UI
	 */
	public Game(ScreenManager screenManager) {
		this.screenManager = screenManager;
		this.textureManager = new TextureManager();
	}
	
	/**
	 * Updates the game, progressing the game by {@code deltaTime} seconds
	 * @param deltaTime The time between start of last frame and this frame in seconds
	 */
	public void update(double deltaTime) {
		screenManager.update(deltaTime);
		textureManager.update(deltaTime);
		
		if (scene != null) {
			scene.update(deltaTime);
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
}
