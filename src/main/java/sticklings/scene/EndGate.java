package sticklings.scene;

import sticklings.render.DebugTexture;
import sticklings.scene.sticklings.Stickling;
import sticklings.util.Location;

public class EndGate extends Entity implements Collideable {
	public EndGate() {
		setTexture(new DebugTexture(60, 60), new Location(-30, -60));
		setBounds(60, 60);
	}
	
	@Override
	public Action onCollide(Entity entity) {
		if (entity instanceof Stickling) {
			entity.remove();
		}
		return Action.NONE;
	}

	@Override
	public void update(double deltaTime) {
		// No operation
	}
	
}
