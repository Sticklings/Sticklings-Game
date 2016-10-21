package sticklings.ui;

import java.io.IOException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import sticklings.Game;
import sticklings.GameRenderer;
import sticklings.levels.Level;
import sticklings.scene.Scene;
import sticklings.scene.sticklings.SticklingType;

public class LevelEndScreen extends Screen {
	private final Game game;
	private final Scene scene;
	
	private final boolean wasSuccessful;
	
        final Image end_success = new Image(LevelEndScreen.class.getResourceAsStream("/ui/end_success.png"));
        
        BackgroundSize bg_size = new BackgroundSize(500, 500, true, true, true, false);
        BackgroundImage end_success_bg_i = new BackgroundImage(end_success, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, bg_size);
        Background end_success_bg = new Background(end_success_bg_i);
        
	public LevelEndScreen(Game game, Scene scene) {
		this.game = game;
		this.scene = scene;
		
		wasSuccessful = scene.getSuccessfulSticklings() >= scene.getLevel().getRequiredSticklings();
	}
	
	@Override
	public Parent initialize() {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(10));
		root.setBackground(end_success_bg);
		// Title
		Label title = new Label((wasSuccessful ? "Level Successful" : "Level Failed"));
		title.setFont(Font.font(46));
		title.setPadding(new Insets(0, 0, 0, 20));
		title.setMaxWidth(Double.MAX_VALUE);
		title.setMaxHeight(67);
		title.setPrefHeight(67);
		
		root.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER_LEFT);
		
		VBox contentPane = new VBox();
		contentPane.setSpacing(20);
		
		// Requirements output
		GridPane grid = new GridPane();
		grid.setVgap(8);
		grid.setHgap(8);
		
		grid.add(new Label("Required"), 0, 0);
		grid.add(new Label(String.format("%d/%d", scene.getLevel().getRequiredSticklings(), scene.getTotalSticklings())), 1, 0);
		
		grid.add(new Label("Achieved"), 0, 1);
		grid.add(new Label(String.format("%d/%d", scene.getSuccessfulSticklings(), scene.getTotalSticklings())), 1, 1);
		
		double percent = (scene.getSuccessfulSticklings() / (double)scene.getTotalSticklings()) * 100;
		Label percentageLabel = new Label(String.format("%.0f%%", percent));
		percentageLabel.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-size: 1;");
		percentageLabel.setPadding(new Insets(3));
		grid.add(percentageLabel, 1, 2);
		
		contentPane.getChildren().add(grid);
		
		contentPane.getChildren().add(generateSticklingUsage());
		
		root.setCenter(contentPane);
		BorderPane.setMargin(contentPane, new Insets(0, 23, 0, 23));
		
		// Button pane
		FlowPane buttonPane = new FlowPane();
		buttonPane.setAlignment(Pos.CENTER_LEFT);
		buttonPane.setPrefHeight(48);
		
		// Level Select button
		Button levelSelectButton = new Button("Levels");
		levelSelectButton.setPrefHeight(43);
		levelSelectButton.setPrefWidth(122);
		buttonPane.getChildren().add(levelSelectButton);
		
		// Retry button
		Button retryButton = new Button("Retry");
		retryButton.setPrefHeight(43);
		retryButton.setPrefWidth(122);
		buttonPane.getChildren().add(retryButton);
		
		if (wasSuccessful) {
			// Next Level button
			Button nextButton = new Button("Continue");
			nextButton.setPrefHeight(43);
			nextButton.setPrefWidth(122);
			buttonPane.getChildren().add(nextButton);
		}
		
		root.setBottom(buttonPane);
		
		return root;
	}
	
	private Parent generateSticklingUsage() {
		// Find out how many basic sticklings were used
		int usedBasic = scene.getSuccessfulSticklings();
		for (SticklingType type : SticklingType.values()) {
			if (type == SticklingType.Basic) {
				continue;
			}
			
			usedBasic = scene.getSticklingAvailability().getUsed(type);
		}
		
		HBox layout = new HBox();
		layout.setSpacing(10);
		
		// Add each type
		for (SticklingType type : SticklingType.values()) {
			int used;
			if (type == SticklingType.Basic) {
				used = usedBasic;
			} else {
				used = scene.getSticklingAvailability().getUsed(type);
			}
			
			if (used > 0) {
				layout.getChildren().add(createSticklingUsage(type, used));
			}
		}
		
		return layout;
	}
	
	private Parent createSticklingUsage(SticklingType type, int count) {
		Image icon = new Image(Game.class.getResourceAsStream("/sprites/stickling/" + type.name().toLowerCase() + ".png"));
		ImageView view = new ImageView(icon);
		
		Label usedLabel = new Label(String.format("X %d", count));
		
		HBox layout = new HBox(view, usedLabel);
		layout.setSpacing(8);
		
		return layout;
	}
	
	private void startLevel(Level level) {
		try {
			Scene scene = game.loadLevel(level);
			
			GameRenderer renderer = new GameRenderer(game.getTextureManager(), scene, 500, 385);
			game.setRenderer(renderer);
			WorldView uitest = new WorldView(scene, renderer);
			game.getScreenManager().gotoScreen(uitest);
		} catch (IOException e) {
			Alert errorBox = new Alert(AlertType.ERROR);
			errorBox.setTitle("Could not load level");
			errorBox.setHeaderText("Could not load level");
			errorBox.setContentText("The file data is corrupted");
			errorBox.show();
		}
		
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
