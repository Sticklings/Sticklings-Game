package sticklings.ui;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class ScreenTest2 extends Screen {
	@Override
	public Parent initialize() {
		BorderPane root = new BorderPane();
		root.setCenter(new Label("This is the second test screen"));
		
		return root;
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
