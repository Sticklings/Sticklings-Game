package sticklings.ui;

import javafx.scene.Parent;
import sticklings.GameRenderer;

public class ScreenTest extends Screen {
	private GameRenderer renderer;
	
	public ScreenTest(GameRenderer renderer) {
		this.renderer = renderer;
	}
	
	@Override
	public Parent initialize() {
		SceneWindow window = new SceneWindow(renderer);
		return window;
	}

	@Override
	public void onShow() {
	}

	@Override
	public void onHide() {
	}

	@Override
	public void update(double deltaTime) {
	}
}
