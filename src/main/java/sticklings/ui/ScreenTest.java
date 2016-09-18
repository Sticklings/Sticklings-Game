package sticklings.ui;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class ScreenTest extends Screen {
	private Image frameImage;
	
	public ScreenTest(Image frameImage) {
		this.frameImage = frameImage;
	}
	
	@Override
	public Parent initialize() {
		BorderPane root = new BorderPane();
		ImageView view = new ImageView(frameImage);
		
		root.setCenter(view);
		root.setBottom(new Label("This is checking layout works"));
		
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
