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
	private final GameRenderer renderer;
	private long lastTime;
	
	/**
	 * Constructs the game timer
	 * @param game The game instance to update
	 * @param renderer The game renderer part 
	 */
	public GameTimer(Game game, GameRenderer renderer) {
		this.game = game;
		this.renderer = renderer;
		lastTime = System.nanoTime();
	}
	
	@Override
	public void handle(long now) {
		double deltaTime = (now - lastTime) / NANO_PER_SEC;
		game.update(deltaTime);
		renderer.draw();
		lastTime = now;
	}
}
