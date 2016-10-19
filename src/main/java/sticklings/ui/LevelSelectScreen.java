package sticklings.ui;

import java.io.IOException;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import sticklings.Game;
import sticklings.GameRenderer;
import sticklings.levels.Level;
import sticklings.levels.LevelLoader;
import sticklings.scene.Scene;

public class LevelSelectScreen extends Screen {
	private final LevelLoader loader;
	private final Game game;
	
	public LevelSelectScreen(LevelLoader loader, Game game) {
		this.loader = loader;
		this.game = game;
	}
	
	@Override
	public Parent initialize() {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(10));
		
		// Title
		Label title = new Label("Level Select");
		title.setFont(Font.font(46));
		title.setPadding(new Insets(0, 0, 0, 20));
		title.setMaxWidth(Double.MAX_VALUE);
		title.setMaxHeight(67);
		title.setPrefHeight(67);
		
		root.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER_LEFT);
		
		// Level pane
		FlowPane levelPane = new FlowPane();
		levelPane.setColumnHalignment(HPos.CENTER);
		levelPane.setHgap(33);
		levelPane.setVgap(20);
		
		populateLevelPane(levelPane);
		
		root.setCenter(levelPane);
		BorderPane.setMargin(levelPane, new Insets(0, 23, 0, 23));
		
		// Button pane
		FlowPane buttonPane = new FlowPane();
		buttonPane.setAlignment(Pos.CENTER_LEFT);
		buttonPane.setPrefHeight(48);
		
		// Back button
		Button backButton = new Button("Back");
		backButton.setPrefHeight(43);
		backButton.setPrefWidth(122);
		backButton.setOnAction(e -> game.getScreenManager().gotoScreen(new MainScreen()));
		buttonPane.getChildren().add(backButton);
		
		root.setBottom(buttonPane);
		
		return root;
	}
	
	private void populateLevelPane(FlowPane levelPane) {
		int index = 0;
		
		for (Level level : loader.getLevels()) {
			++index;
			
			Button levelButton = new Button();
			levelButton.setPrefWidth(60);
			levelButton.setMaxWidth(60);
			levelButton.setPrefHeight(60);
			levelButton.setMaxHeight(60);
			
			levelButton.setText(String.valueOf(index));
			
			Tooltip levelTooltip = new Tooltip(level.getName());
			levelButton.setTooltip(levelTooltip);
			
			levelButton.setOnAction(e -> startLevel(level));
			
			levelPane.getChildren().add(levelButton);
		}
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
