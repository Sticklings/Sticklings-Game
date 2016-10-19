package sticklings.util;

import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;
import sticklings.Game;
import sticklings.GameRenderer;

/**
 * Game timer will update the game at approximately 60htz
 */
public class GameTimer extends AnimationTimer {
	private static final double NANO_PER_SEC = TimeUnit.SECONDS.toNanos(1);
	
	private final Game game;
	private long lastTime;
	
	private GameRenderer renderer;
	
	/**
	 * Constructs the game timer
	 * @param game The game instance to update
	 */
	public GameTimer(Game game) {
		this.game = game;
		lastTime = System.nanoTime();
	}
	
	/**
	 * Sets the game renderer instance
	 * @param renderer The renderer
	 */
	public void setRenderer(GameRenderer renderer) {
		this.renderer = renderer;
	}
	
	/**
	 * Gets the game renderer instance
	 * @return The renderer
	 */
	public GameRenderer getRenderer() {
		return renderer;
	}
	
	@Override
	public void handle(long now) {
		double deltaTime = (now - lastTime) / NANO_PER_SEC;
		game.update(deltaTime);
		if (renderer != null) {
			renderer.draw();
		}
		lastTime = now;
	}
}
