/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sticklings.ui;

import com.google.common.collect.Iterables;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import sticklings.Game;
import sticklings.levels.Level;
import sticklings.GameRenderer;
import sticklings.scene.Scene;
import sticklings.scene.sticklings.Stickling;
import sticklings.scene.sticklings.SticklingType;

/**
 *
 * @author swann
 */
public class WorldView extends Screen {

    int game_height = 500;
    int game_width = 500;
    int btn_stickling_height = 75;
    int btn_stickling_width = 50;
    int label_height = 20;

    private Pane root;

    private ToggleButton[] typeButtons = new ToggleButton[SticklingType.values().length - 1];

    final Image button_background = new Image(WorldView.class.getResourceAsStream("/ui/btn_background.png"));

    final Image closed = new Image(WorldView.class.getResourceAsStream("/ui/closed.png"));

    final Image lbl_bg_i = new Image(WorldView.class.getResourceAsStream("/ui/lbl_bg.png"));

    private final Image img_miner_sel = new Image(WorldView.class.getResourceAsStream("/ui/btn_miner_selected.png"));
    private final Image img_floater_sel = new Image(WorldView.class.getResourceAsStream("/ui/btn_floater_selected.png"));
    private final Image img_blocker_sel = new Image(WorldView.class.getResourceAsStream("/ui/btn_blocker_selected.png"));
    private final Image img_swimmer_sel = new Image(WorldView.class.getResourceAsStream("/ui/btn_swimmer_selected.png"));
    private final Image img_exploder_sel = new Image(WorldView.class.getResourceAsStream("/ui/btn_exploder_selected.png"));

    private final Image img_miner_unsel = new Image(WorldView.class.getResourceAsStream("/ui/btn_miner_unselected.png"));
    private final Image img_floater_unsel = new Image(WorldView.class.getResourceAsStream("/ui/btn_floater_unselected.png"));
    private final Image img_blocker_unsel = new Image(WorldView.class.getResourceAsStream("/ui/btn_blocker_unselected.png"));
    private final Image img_swimmer_unsel = new Image(WorldView.class.getResourceAsStream("/ui/btn_swimmer_unselected.png"));
    private final Image img_exploder_unsel = new Image(WorldView.class.getResourceAsStream("/ui/btn_exploder_unselected.png"));

    private final Image cur_miner = new Image(WorldView.class.getResourceAsStream("/ui/miner_cursor.png"));
    private final Image cur_floater = new Image(WorldView.class.getResourceAsStream("/ui/floater_cursor.png"));
    private final Image cur_blocker = new Image(WorldView.class.getResourceAsStream("/ui/blocker_cursor.png"));
    private final Image cur_swimmer = new Image(WorldView.class.getResourceAsStream("/ui/swimmer_cursor.png"));
    private final Image cur_exploder = new Image(WorldView.class.getResourceAsStream("/ui/exploder_cursor.png"));

    //Stars to show how the user is progressing through the level
    Image star = new Image(WorldView.class.getResourceAsStream("/ui/star.png"));
    ImageView star1 = new ImageView(star);
    ImageView star2 = new ImageView(star);
    ImageView star3 = new ImageView(star);

    //Label Backgrounds
    BackgroundSize lbl_bg_size = new BackgroundSize(btn_stickling_width, label_height, true, true, true, false);
    BackgroundImage lbl_bg_image = new BackgroundImage(lbl_bg_i, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, lbl_bg_size);
    Background lbl_bg = new Background(lbl_bg_image);

    //Standard size for the stickling buttons to avoid repetition
    BackgroundSize btn_stickling_bg_size = new BackgroundSize(btn_stickling_width, btn_stickling_height, true, true, true, false);

    //Miner
    BackgroundImage btn_miner_image_bg_sel = new BackgroundImage(img_miner_sel, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_miner_bg_sel = new Background(btn_miner_image_bg_sel);
    BackgroundImage btn_miner_image_bg_unsel = new BackgroundImage(img_miner_unsel, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_miner_bg_unsel = new Background(btn_miner_image_bg_unsel);
    //Floater
    BackgroundImage btn_floater_image_bg_sel = new BackgroundImage(img_floater_sel, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_floater_bg_sel = new Background(btn_floater_image_bg_sel);
    BackgroundImage btn_floater_image_bg_unsel = new BackgroundImage(img_floater_unsel, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_floater_bg_unsel = new Background(btn_floater_image_bg_unsel);
    //Blocker
    BackgroundImage btn_blocker_image_bg_sel = new BackgroundImage(img_blocker_sel, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_blocker_bg_sel = new Background(btn_blocker_image_bg_sel);
    BackgroundImage btn_blocker_image_bg_unsel = new BackgroundImage(img_blocker_unsel, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_blocker_bg_unsel = new Background(btn_blocker_image_bg_unsel);
    //Swimmer
    BackgroundImage btn_swimmer_image_bg_sel = new BackgroundImage(img_swimmer_sel, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_swimmer_bg_sel = new Background(btn_swimmer_image_bg_sel);
    BackgroundImage btn_swimmer_image_bg_unsel = new BackgroundImage(img_swimmer_unsel, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_swimmer_bg_unsel = new Background(btn_swimmer_image_bg_unsel);
    //Exploder
    BackgroundImage btn_exploder_image_bg_sel = new BackgroundImage(img_exploder_sel, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_exploder_bg_sel = new Background(btn_exploder_image_bg_sel);
    BackgroundImage btn_exploder_image_bg_unsel = new BackgroundImage(img_exploder_unsel, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_exploder_bg_unsel = new Background(btn_exploder_image_bg_unsel);
    //Closed off stickling when the use can't put anymore onto the level
    BackgroundImage btn_close_image_bg = new BackgroundImage(closed, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_closed_bg = new Background(btn_close_image_bg);

    private Label lbl_progress = new Label();
    private Label lbl_goal = new Label();
    private Label lbl_avail = new Label();

    private final Scene scene;
    private final GameRenderer renderer;

    private SceneWindow window;

    public WorldView(Scene scene, GameRenderer renderer) {
        this.scene = scene;
        this.renderer = renderer;
    }

    private Image loadImage(String path) {
        return new Image(WorldView.class.getResourceAsStream(path));
    }

    private ToggleButton createTypeButton(SticklingType type, double x, Image selected, Image unselected, Image cursor) {
        ToggleButton button = new ToggleButton();

        // Layout settings
        button.setMinSize(btn_stickling_width, btn_stickling_height);
        button.setLayoutX(x);
        button.setLayoutY(game_height - btn_stickling_height - label_height);

        // Background settings
        BackgroundSize size = new BackgroundSize(btn_stickling_width, btn_stickling_height, true, true, true, false);

        Background selectedBackground = new Background(new BackgroundImage(selected, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, size));
        Background unselectedBackground = new Background(new BackgroundImage(unselected, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, size));

        // Allow the background to be changed automatically based on the state
        button.setBackground(unselectedBackground);
        button.selectedProperty().addListener((btn, old, current) -> {
            if (current) {

                button.setBackground(selectedBackground);
            } else if (!button.getBackground().equals(btn_closed_bg)) {
                button.setBackground(unselectedBackground);
            }
        });

        // Other options
        button.setCursor(Cursor.HAND);

        // Action
        button.setOnAction(e -> selectType(button, type));

        typeButtons[type.ordinal() - 1] = button;

        return button;
    }

    // Setup quantity label
    public Label set_qty_label(SticklingType type, ToggleButton button) {
        Label quantityLabel = new Label("-");
        quantityLabel.setMinSize(btn_stickling_width, label_height);
        quantityLabel.setAlignment(Pos.CENTER);
        quantityLabel.setLayoutX(button.getLayoutX());
        quantityLabel.setLayoutY(button.getLayoutY() - label_height);
        quantityLabel.setTextFill(Paint.valueOf("White"));
        quantityLabel.setBackground(lbl_bg);
        quantityLabel.textProperty().bind(scene.getSticklingAvailability().remainingProperty(type).asString());
        quantityLabel.setBackground(lbl_bg);

        if (quantityLabel.getText().equals("0")) {
            button.setBackground(btn_closed_bg);
        } else {
            quantityLabel.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                    if (t1.equals("0")) {
                        button.setBackground(btn_closed_bg);
                    }
                }
            });
        }
        return quantityLabel;
    }

    public Label set_name_label(String s, ToggleButton btn, Label lbl) {
        lbl.setText(s);
        lbl.setMinSize(btn_stickling_width, label_height);
        lbl.setAlignment(Pos.CENTER);
        lbl.setLayoutX(btn.getLayoutX());
        lbl.setLayoutY(btn.getLayoutY() + btn_stickling_height);
        lbl.setTextFill(Paint.valueOf("White"));
        lbl.setBackground(lbl_bg);
        return lbl;
    }

    @Override
    public Parent initialize() {
        root = new Pane();

        window = new SceneWindow(scene, renderer);

        Button btn_speed_normal = new Button();
        Button btn_speed_fast = new Button();
        Button btn_speed_faster = new Button();
        Button btn_kill_all = new Button();
        Button btn_reset = new Button();

        Label lbl_miner = new Label();
        Label lbl_floater = new Label();
        Label lbl_blocker = new Label();
        Label lbl_swimmer = new Label();
        Label lbl_exploder = new Label();

        Label qty_miner;
        Label qty_floater;
        Label qty_blocker;
        Label qty_swimmer;
        Label qty_exploder;

        //----------------------------------------------------------------------
        ToggleButton btn_miner = createTypeButton(
                SticklingType.Miner,
                0,
                loadImage("/ui/btn_miner_selected.png"),
                loadImage("/ui/btn_miner_unselected.png"),
                loadImage("/ui/miner_cursor.png")
        );
        qty_miner = set_qty_label(SticklingType.Miner, btn_miner);
        lbl_miner = set_name_label("Miner", btn_miner, lbl_miner);
        //----------------------------------------------------------------------
        ToggleButton btn_floater = createTypeButton(
                SticklingType.Floater,
                btn_miner.getLayoutX() + btn_stickling_width,
                loadImage("/ui/btn_floater_selected.png"),
                loadImage("/ui/btn_floater_unselected.png"),
                loadImage("/ui/floater_cursor.png")
        );
        qty_floater = set_qty_label(SticklingType.Floater, btn_floater);
        lbl_floater = set_name_label("Floater", btn_floater, lbl_floater);
        //----------------------------------------------------------------------
        ToggleButton btn_blocker = createTypeButton(
                SticklingType.Blocker,
                btn_floater.getLayoutX() + btn_stickling_width,
                loadImage("/ui/btn_blocker_selected.png"),
                loadImage("/ui/btn_blocker_unselected.png"),
                loadImage("/ui/blocker_cursor.png")
        );
        qty_blocker = set_qty_label(SticklingType.Blocker, btn_blocker);
        lbl_blocker = set_name_label("Blocker", btn_blocker, lbl_blocker);
        //----------------------------------------------------------------------
        ToggleButton btn_swimmer = createTypeButton(
                SticklingType.Swimmer,
                btn_blocker.getLayoutX() + btn_stickling_width,
                loadImage("/ui/btn_swimmer_selected.png"),
                loadImage("/ui/btn_swimmer_unselected.png"),
                loadImage("/ui/swimmer_cursor.png")
        );
        qty_swimmer = set_qty_label(SticklingType.Swimmer, btn_swimmer);
        lbl_swimmer = set_name_label("Swimmer", btn_swimmer, lbl_swimmer);
        //----------------------------------------------------------------------
        ToggleButton btn_exploder = createTypeButton(
                SticklingType.Exploder,
                btn_swimmer.getLayoutX() + btn_stickling_width,
                loadImage("/ui/btn_exploder_selected.png"),
                loadImage("/ui/btn_exploder_unselected.png"),
                loadImage("/ui/exploder_cursor.png")
        );
        qty_exploder = set_qty_label(SticklingType.Exploder, btn_exploder);
        lbl_exploder = set_name_label("Exploder", btn_exploder, lbl_exploder);

        //----------------------------------------------------------------------
        btn_speed_normal.setText(">");
        btn_speed_normal.setTextAlignment(TextAlignment.CENTER);
        btn_speed_normal.setMinSize(30, 25);
        btn_speed_normal.setLayoutX(btn_exploder.getLayoutX() + btn_stickling_width + 10);
        btn_speed_normal.setLayoutY(btn_exploder.getLayoutY());
        btn_speed_normal.setOnAction(e -> Game.getInstance().setGameSpeed(Game.SPEED_NORMAL));

        btn_speed_fast.setText(">>");
        btn_speed_fast.setTextAlignment(TextAlignment.CENTER);
        btn_speed_fast.setMinSize(30, 25);
        btn_speed_fast.setLayoutX(btn_speed_normal.getLayoutX() + 30 + 10);
        btn_speed_fast.setLayoutY(btn_exploder.getLayoutY());
        btn_speed_fast.setOnAction(e -> Game.getInstance().setGameSpeed(Game.SPEED_FAST));

        btn_speed_faster.setText(">>>");
        btn_speed_faster.setTextAlignment(TextAlignment.CENTER);
        btn_speed_faster.setMinSize(30, 25);
        btn_speed_faster.setLayoutX(btn_speed_fast.getLayoutX() + 30 + 10);
        btn_speed_faster.setLayoutY(btn_exploder.getLayoutY());
        btn_speed_faster.setOnAction(e -> Game.getInstance().setGameSpeed(Game.SPEED_FASTEST));

        btn_reset.setText("Reset");
        btn_reset.setTextAlignment(TextAlignment.CENTER);
        btn_reset.setMinSize(120, 25);
        btn_reset.setLayoutX(btn_exploder.getLayoutX() + btn_stickling_width + 10);
        btn_reset.setLayoutY(btn_speed_normal.getLayoutY() + 35);

        // DEBUG: Remove this
        btn_reset.setOnAction(e -> Game.getInstance().getScreenManager().gotoScreen(new LevelEndScreen(Game.getInstance(), scene)));

        btn_kill_all.setText("Kill All");
        btn_kill_all.setTextAlignment(TextAlignment.CENTER);
        btn_kill_all.setMinSize(120, 25);
        btn_kill_all.setLayoutX(btn_exploder.getLayoutX() + btn_stickling_width + 10);
        btn_kill_all.setLayoutY(btn_reset.getLayoutY() + 35);

        btn_kill_all.setOnAction(e -> {
            scene.findEntities(Stickling.class).forEach(stick -> {
                Stickling newStickling = SticklingType.Exploder.create();
                newStickling.copyFrom(stick);
                scene.addEntity(newStickling);
                stick.remove();
            });
            scene.setRemainingSticklings(0);
        });

        lbl_goal.setText(String.format("Goal: %d", scene.getLevel().getRequiredSticklings()));
        lbl_goal.setTextAlignment(TextAlignment.LEFT);
        lbl_goal.setFont(javafx.scene.text.Font.font(20));
        lbl_goal.setMinSize(60, 25);
        lbl_goal.setLayoutX(btn_reset.getLayoutX() + 120 + 10);
        lbl_goal.setLayoutY(btn_speed_faster.getLayoutY() - 25);
        lbl_goal.setTextFill(Paint.valueOf("White"));

        lbl_progress.setTextAlignment(TextAlignment.LEFT);
        lbl_progress.setFont(javafx.scene.text.Font.font(20));
        lbl_progress.setMinSize(60, 25);
        lbl_progress.setLayoutX(btn_reset.getLayoutX() + 120 + 10);
        lbl_progress.setLayoutY(lbl_goal.getLayoutY() + 25);
        lbl_progress.setTextFill(Paint.valueOf("White"));

        lbl_avail.setText(String.format("Avail: %d", scene.getTotalSticklings()));
        lbl_avail.setTextAlignment(TextAlignment.LEFT);
        lbl_avail.setFont(javafx.scene.text.Font.font(20));
        lbl_avail.setMinSize(60, 25);
        lbl_avail.setLayoutX(btn_reset.getLayoutX() + 120 + 10);
        lbl_avail.setLayoutY(lbl_progress.getLayoutY() + 25);
        lbl_avail.setTextFill(Paint.valueOf("White"));

        window.setLayoutX(0);
        window.setLayoutY(0);

        star1.setLayoutX(btn_reset.getLayoutX() + 120 + 10);
        star1.setLayoutY(btn_kill_all.getLayoutY());
        star1.setVisible(false);
        star2.setLayoutX(star1.getLayoutX() + 20 + 10);
        star2.setLayoutY(btn_kill_all.getLayoutY());
        star2.setVisible(false);
        star3.setLayoutX(star2.getLayoutX() + 20 + 10);
        star3.setLayoutY(btn_kill_all.getLayoutY());
        star3.setVisible(false);

        ImageView button_bg = new ImageView(button_background);
        button_bg.setLayoutX(0);
        button_bg.setLayoutY(385);
        root.getChildren().add(button_bg);

        root.getChildren().add(star1);
        root.getChildren().add(star2);
        root.getChildren().add(star3);

        root.getChildren().add(qty_miner);
        root.getChildren().add(btn_miner);
        root.getChildren().add(lbl_miner);

        root.getChildren().add(qty_floater);
        root.getChildren().add(btn_floater);
        root.getChildren().add(lbl_floater);

        root.getChildren().add(qty_blocker);
        root.getChildren().add(btn_blocker);
        root.getChildren().add(lbl_blocker);

        root.getChildren().add(qty_swimmer);
        root.getChildren().add(btn_swimmer);
        root.getChildren().add(lbl_swimmer);

        root.getChildren().add(qty_exploder);
        root.getChildren().add(btn_exploder);
        root.getChildren().add(lbl_exploder);

        root.getChildren().add(btn_speed_normal);
        root.getChildren().add(btn_speed_fast);
        root.getChildren().add(btn_speed_faster);
        root.getChildren().add(btn_reset);
        root.getChildren().add(btn_kill_all);

        root.getChildren().add(lbl_goal);
        root.getChildren().add(lbl_progress);
        root.getChildren().add(lbl_avail);

        root.getChildren().add(window);

        return root;
    }

    private void selectType(ToggleButton button, SticklingType type) {
        if (!button.isSelected()) {
            root.setCursor(Cursor.DEFAULT);
            window.selectType(null);
            return;
        } else {
            // Prevent selecting ones that are not available
            if (scene.getSticklingAvailability().getRemaining(type) == 0) {
                button.setSelected(false);
                return;
            }

            // Deselect previous buttons
            for (ToggleButton other : typeButtons) {
                if (other != button) {
                    other.setSelected(false);
                }
            }
        }

        Image cursorImage;
        switch (type) {
            case Blocker:
                cursorImage = cur_blocker;
                break;
            case Exploder:
                cursorImage = cur_exploder;
                break;
            case Floater:
                cursorImage = cur_floater;
                break;
            case Miner:
                cursorImage = cur_miner;
                break;
            case Swimmer:
                cursorImage = cur_swimmer;
                break;
            default:
                throw new AssertionError(type);
        }

        window.selectType(type);
        root.setCursor(new ImageCursor(cursorImage));
    }

    @Override
    public void onShow() {

    }

    @Override
    public void onHide() {

    }

    @Override
    public void update(double deltaTime) {
        int total = scene.getTotalSticklings();
        int saved = scene.getSuccessfulSticklings();
        int required = scene.getLevel().getRequiredSticklings();

        Level stats = Game.getInstance().getLevel().get();
        lbl_progress.setText(String.format("Saved: %d", saved));

        //do star stuff here
        if (saved >= required) {
            star1.setVisible(true);
        }

        if (saved >= (((total - required) / 2) + required)) {
            star2.setVisible(true);
        }
        if (saved >= total) {
            star3.setVisible(true);
        }

        if (scene.getRemainingSticklings() == 0 && Iterables.isEmpty(scene.findEntities(Stickling.class))) {
            Game.getInstance().getScreenManager().gotoScreen(new LevelEndScreen(Game.getInstance(), scene));
        }
    }
}
