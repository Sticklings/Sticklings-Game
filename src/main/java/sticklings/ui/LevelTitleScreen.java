package sticklings.ui;

import java.io.IOException;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import sticklings.Game;
import sticklings.GameRenderer;
import sticklings.levels.Level;
import sticklings.scene.Scene;
import sticklings.scene.sticklings.SticklingType;

public class LevelTitleScreen extends Screen {
	private static final double DISPLAY_TIME = 6;
	
	private final Game game;
	private final Level level;
	
	private double time;
	
	public LevelTitleScreen(Game game, Level level) {
		this.game = game;
		this.level = level;
		
		time = DISPLAY_TIME;
	}
	
	@Override
	public Parent initialize() {
		StackPane root = new StackPane();
		
		VBox content = new VBox();
		content.setSpacing(10);
		
		Label title = new Label(level.getName());
		title.setFont(Font.font(90));
		content.getChildren().add(title);
		
		Label available = new Label("Available: " + level.getTotalSticklings());
		content.getChildren().add(available);
		Label required = new Label("Required: " + level.getRequiredSticklings());
		content.getChildren().add(required);
		
		Parent types = generateSticklingAvailability();
		content.getChildren().add(types);
		
		content.setAlignment(Pos.CENTER);
		root.getChildren().add(content);
		
		return root;
	}
	
	private Parent generateSticklingAvailability() {
		HBox layout = new HBox();
		layout.setSpacing(10);
		layout.setAlignment(Pos.CENTER);
		
		// Add each type
		for (SticklingType type : SticklingType.values()) {
			if (type == SticklingType.Basic) {
				continue;
			}
			
			int available = level.getTypeAvailability().getTotal(type);
			
			layout.getChildren().add(createSticklingIcon(type, available));
		}
		
		return layout;
	}
	
	private Parent createSticklingIcon(SticklingType type, int count) {
		Image icon = new Image(Game.class.getResourceAsStream("/sprites/stickling/" + type.name().toLowerCase() + ".png"));
		ImageView view = new ImageView(icon);
		
		Label usedLabel = new Label(String.format("X %d", count));
		
		HBox layout = new HBox(view, usedLabel);
		layout.setSpacing(8);
		
		return layout;
	}

	@Override
	public void onShow() {
	}

	@Override
	public void onHide() {
	}

	@Override
	public void update(double deltaTime) {
		time -= deltaTime;
		if (time <= 0) {
			// Move to game screen
			try {
				Scene scene = game.loadLevel(level);
				
				GameRenderer renderer = new GameRenderer(game.getTextureManager(), scene, 500, 385);
				game.setRenderer(renderer);
				WorldView gameHud = new WorldView(scene, renderer);
				game.getScreenManager().gotoScreen(gameHud);
			} catch (IOException e) {
				game.getScreenManager().gotoScreen(new MainScreen(game));
				Alert errorBox = new Alert(AlertType.ERROR);
				errorBox.setTitle("Could not load level");
				errorBox.setHeaderText("Could not load level");
				errorBox.setContentText("The file data is corrupted");
				errorBox.show();
			}
		}
	}

}
