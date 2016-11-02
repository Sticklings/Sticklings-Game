package sticklings.scene;

import javafx.scene.image.Image;
import sticklings.render.BasicTexture;
import sticklings.scene.sticklings.Stickling;
import sticklings.util.Location;

public class EndGate extends Entity implements Collideable {
	public EndGate() {
		Image exit = new Image(EndGate.class.getResourceAsStream("/ui/exit.png"));
		setTexture(new BasicTexture(exit), new Location(-30, -60));
		// setTexture(new DebugTexture(60, 60), new Location(-30, -60));

		setBounds(30, 30);
	}

	@Override
	public Action onCollide(Entity entity) {
		if (entity instanceof Stickling) {
			getScene().setSuccessfulSticklings(getScene().getSuccessfulSticklings() + 1);
			entity.remove();
		}
		return Action.NONE;
	}

	@Override
	public void update(double deltaTime) {
		// No operation
	}

}
