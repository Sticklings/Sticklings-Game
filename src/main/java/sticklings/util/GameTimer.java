package sticklings.util;

import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;
import sticklings.Game;

/**
 * Game timer will update the game at approximately 60htz
 */
public class GameTimer extends AnimationTimer {
	private static final long NANO_PER_SEC = TimeUnit.SECONDS.toNanos(1);
	
	private final Game game;
	private long lastTime;
	
	/**
	 * Constructs the game timer
	 * @param game The game instance to update 
	 */
	public GameTimer(Game game) {
		this.game = game;
		lastTime = System.nanoTime();
	}
	
	@Override
	public void handle(long now) {
		double deltaTime = (now - lastTime) / NANO_PER_SEC;
		game.update(deltaTime);
		lastTime = now;
	}
}
