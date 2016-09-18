package sticklings.ui;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class ScreenTest extends Screen {
	private ScreenManager screenManager;
	
	public ScreenTest(ScreenManager screenManager) {
		this.screenManager = screenManager;
	}
	
	@Override
	public Parent initialize() {
		BorderPane root = new BorderPane();
		root.setCenter(new Label("This is a test screen"));
		root.setBottom(new Label("This is checking layout works"));
		
		Button button = new Button("Change Screen");
		button.setOnAction(e -> screenManager.gotoScreen(new ScreenTest2()));
		root.setTop(button);
		
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
