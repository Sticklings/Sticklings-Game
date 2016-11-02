package sticklings.ui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import sticklings.Game;
import sticklings.levels.Level;
import sticklings.levels.LevelLoader;

/**
 * A level select screen
 */
public class LevelSelectScreen extends Screen {
	private final LevelLoader loader;
	private final Game game;

	Image background = new Image(LevelSelectScreen.class.getResourceAsStream("/ui/background.png"));
	BackgroundSize bg_size = new BackgroundSize(500, 500, true, true, true, false);
	BackgroundImage background_i = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, bg_size);
	Background background_bg = new Background(background_i);

	public LevelSelectScreen(Game game) {
		this.game = game;
		this.loader = game.getLevelLoader();
	}

	@Override
	public Parent initialize() {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(10));
		root.setBackground(background_bg);
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
		backButton.setOnAction(e -> game.getScreenManager().gotoScreen(new MainScreen(game)));
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

	/**
	 * Launches the level
	 * 
	 * @param level The level
	 */
	private void startLevel(Level level) {
		game.getScreenManager().gotoScreen(new LevelTitleScreen(game, level));
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
