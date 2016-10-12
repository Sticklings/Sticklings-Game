package sticklings.scene;

import javafx.scene.image.Image;
import sticklings.render.BasicTexture;
import sticklings.render.DebugTexture;
import sticklings.scene.sticklings.BasicStickling;
import sticklings.util.Location;

/**
 * Spawns sticklings in the world
 */
public class StartGate extends Entity {
	private static final double SPAWN_DELAY = 5;
	
	private double lastSpawnTime = 0;
	
	public StartGate() {
            Image entry = new Image(StartGate.class.getResourceAsStream("/ui/entry.png"));
		//setTexture(new DebugTexture(60, 60), new Location(-30, -30));
                setTexture(new BasicTexture(entry), new Location(-30, -30));
	}
	
	@Override
	public void update(double deltaTime) {
		lastSpawnTime += deltaTime;
		if (lastSpawnTime >= SPAWN_DELAY) {
			lastSpawnTime -= SPAWN_DELAY;
			spawnStickling();
		}
	}
	
	private void spawnStickling() {
		if (getScene().getRemainingSticklings() <= 0)
			return;
		
		BasicStickling stickling = new BasicStickling();
		stickling.setLocation(getLocation().copy());
		getScene().addEntity(stickling);
		
		// Record that one was spawned
		getScene().setRemainingSticklings(getScene().getRemainingSticklings()-1);
	}
}

