package sticklings;

import java.util.Optional;

import sticklings.scene.Scene;
import sticklings.ui.ScreenManager;

public class Game {
	private final ScreenManager screenManager;
	
	/**
	 * Constructs a new game with the needed managers
	 * @param screenManager The screen manager for updating the UI
	 */
	public Game(ScreenManager screenManager) {
		this.screenManager = screenManager;
	}
	
	/**
	 * Updates the game, progressing the game by {@code deltaTime} seconds
	 * @param deltaTime The time between start of last frame and this frame in seconds
	 */
	public void update(double deltaTime) {
		screenManager.update(deltaTime);
		// TODO: Game update method
	}
	
	public Optional<Scene> getScene() {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	/**
	 * Gets the active screen manager.
	 * Use this to transition between screens and control the game window
	 * @return The screen manager
	 */
	public ScreenManager getScreenManager() {
		return screenManager;
	}
}
