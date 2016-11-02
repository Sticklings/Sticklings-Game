package sticklings.ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import sticklings.Game;

/**
 * The main menu
 * @author swann
 */
public class MainScreen extends Screen {

	public int btn_height = 75;
	public int btn_width = 150;

	private final Game game;

	public MainScreen(Game game) {
		this.game = game;
	}

	@Override
	public Parent initialize() {
		// throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		Image main_background_image = null;
		Image btn_play_image = null;
		Image btn_help_image = null;
		Image btn_settings_image = null;
		try { // Load images for the main screen
			main_background_image = new Image(MainScreen.class.getClass().getResourceAsStream("/ui/title screen.png"));
			btn_play_image = new Image(MainScreen.class.getClass().getResourceAsStream("/ui/btn_play.png"));
			btn_help_image = new Image(MainScreen.class.getClass().getResourceAsStream("/ui/btn_help.png"));
			btn_settings_image = new Image(MainScreen.class.getClass().getResourceAsStream("/ui/btn_settings.png"));
		} catch (Exception e) {
			System.out.print("Can't load an image");
			e.printStackTrace();
		}

		BackgroundSize main_bg_size = new BackgroundSize(500, 500, true, true, true, false);
		BackgroundSize btn_bg_size = new BackgroundSize(btn_width, btn_height, true, true, true, false);

		BackgroundImage main_bg_image = new BackgroundImage(main_background_image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, main_bg_size);
		BackgroundImage btn_play_bg_image = new BackgroundImage(btn_play_image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_bg_size);
		BackgroundImage btn_help_bg_image = new BackgroundImage(btn_help_image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_bg_size);
		BackgroundImage btn_settings_bg_image = new BackgroundImage(btn_settings_image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_bg_size);

		Background main_bg = new Background(main_bg_image);
		Background btn_play_bg = new Background(btn_play_bg_image);
		Background btn_help_bg = new Background(btn_help_bg_image);
		Background btn_settings_bg = new Background(btn_settings_bg_image);

		Button btn_play = new Button();
		btn_play.setText("Play");
		btn_play.setBackground(btn_play_bg);
		btn_play.setCursor(Cursor.HAND);
		btn_play.setOnAction(e -> game.getScreenManager().gotoScreen(new LevelSelectScreen(game)));

		Button btn_settings = new Button();
		btn_settings.setText("Exit");
		btn_settings.setBackground(btn_settings_bg);
		btn_settings.setCursor(Cursor.HAND);
		btn_settings.setOnAction(e -> Platform.exit());

		Button btn_help = new Button();
		btn_help.setText("Help");
		btn_help.setBackground(btn_help_bg);
		btn_help.setCursor(Cursor.HAND);
		btn_help.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.INFORMATION);

				alert.setTitle("Help");
				alert.setHeaderText("How to play Sticklings");
				String info = "Welcome to Sticklings!\n" + "The aim of the game is to get as many sticklings to the end of the level.\n" + "\n" + "There may be some obstacles in your way though...\n" + "\n" + "You can select different types of Sticklings by clicking on the bar on the bottom. " + "There are only a certain number of characteristics so use them carefully!\n" + "The number above the Stickling tells you how many are left\n" + "Once you run out, a curtain will close and you won't be able to select it anymore.\n" + "\n" + "Miner:\n" + "Started looking for precious gems, now trying to escape, problem is it only knows how to dig " + "on a steep decline. Will start mining through the ground and continue until it breaks through.\n " + "\n" + "Floater:\n" + "Once a daredevil, always a daredevil. Equipped with a jetpack with enough thrust " + "to fly to mars and back, this little guy can survive any fall distance, assuming you turn it on.\n" + "\n" + "Blocker:\n" + "Good luck getting into this night club. Not even Kim Kardashian herself can get past this guy. " + "Whether it be a large hole or you want to speed up things, no Stickling shall pass!\n" + "\n" + "Swimmer:\n" + "This Stickling may run near the pool or dive into the shallow end but at least it is wearing " + "it's floaties to keep it from drowning.\n" + "\n" + "Exploder:\n" + "This Stickling is trying to find it's purpose in life and " + "still can't figure out why all its friends run away when it shows up." + " At least it can help take away a left over blocker\n" + "\n" + "The Sticklings were enjoying their lazy days running around in grass and sleeping on trees " + "until a conga line party led them to fall down a hidden trap door that landed them in a cave of despair. \n" + "Luckily enough, each Stickling has a hidden characteristic that can be activated. With a magic portal " + "to lead them back, the Sticklings are desperate to get home and won't stop walking to get there";

				TextArea textArea = new TextArea(info);
				textArea.setEditable(false);
				textArea.setWrapText(true);
				textArea.setMaxWidth(Double.MAX_VALUE);
				textArea.setMaxHeight(Double.MAX_VALUE);
				GridPane.setVgrow(textArea, Priority.ALWAYS);
				GridPane.setHgrow(textArea, Priority.ALWAYS);

				GridPane expContent = new GridPane();
				expContent.setMaxWidth(Double.MAX_VALUE);
				expContent.add(textArea, 0, 1);
				/// alert.getDialogPane().setExpandableContent(expContent);
				alert.getDialogPane().setContent(expContent);
				alert.showAndWait();
			}
		});

		Pane root = new Pane();

		btn_play.setMinSize(btn_width, btn_height);
		btn_play.layoutXProperty().bind(btn_play.widthProperty().negate().add(root.widthProperty()).divide(2));
		btn_play.layoutYProperty().bind(btn_play.heightProperty().negate().add(root.heightProperty()).divide(2));

		btn_help.setMinSize(btn_width, btn_height);
		btn_help.layoutXProperty().bind(btn_play.layoutXProperty().add(btn_play.widthProperty()));
		btn_help.layoutYProperty().bind(btn_play.layoutYProperty().add(btn_play.heightProperty()));

		btn_settings.setMinSize(btn_width, btn_height);
		btn_settings.layoutXProperty().bind(btn_play.layoutXProperty().subtract(btn_settings.widthProperty()));
		btn_settings.layoutYProperty().bind(btn_play.layoutYProperty().add(btn_play.heightProperty()));

		root.setBackground(main_bg);
		root.getChildren().add(btn_play);
		root.getChildren().add(btn_help);
		root.getChildren().add(btn_settings);

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
