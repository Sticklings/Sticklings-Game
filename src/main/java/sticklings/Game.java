package sticklings;

import java.util.Optional;

import sticklings.render.TextureManager;
import sticklings.scene.Scene;
import sticklings.ui.ScreenManager;

public class Game {
	private final ScreenManager screenManager;
	private final TextureManager textureManager;
	
	private Scene scene;
	
	/**
	 * Constructs a new game with the needed managers
	 * @param screenManager The screen manager for updating the UI
	 */
	public Game(ScreenManager screenManager) {
		this.screenManager = screenManager;
		this.textureManager = new TextureManager();
		
		// DEBUG, will be created on level load
		scene = new Scene();
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
	 * Gets the current scene if any
	 * @return An optional that may or may not have a scene 
	 */
	public Optional<Scene> getScene() {
		return Optional.ofNullable(scene);
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
