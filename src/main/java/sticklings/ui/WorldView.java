/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sticklings.ui;

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
public class WorldView extends Screen{
    int game_height = 500;
    int game_width = 500;
    int btn_stickling_height = 75;
    int btn_stickling_width = 50;
    int label_height = 20;
    
    private Pane root;
    
    ToggleButton btn_miner = new ToggleButton();
    ToggleButton btn_floater = new ToggleButton();
    ToggleButton btn_blocker = new ToggleButton();
    ToggleButton btn_swimmer = new ToggleButton();
    ToggleButton btn_exploder = new ToggleButton();
    
    final Image button_background = new Image(WorldView.class.getResourceAsStream("/ui/btn_background.png"));
    
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
    
      Image star = new Image(WorldView.class.getResourceAsStream("/ui/star.png"));
      ImageView star1 = new ImageView(star);
        
        
        ImageView star2 = new ImageView(star);
        
        
        
        ImageView star3 = new ImageView(star);
       
    
    BackgroundSize  btn_stickling_bg_size = new BackgroundSize(btn_stickling_width, btn_stickling_height, true, true, true, false);
    
    BackgroundImage btn_miner_image_bg_sel = new BackgroundImage(img_miner_sel, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_miner_bg_sel = new Background(btn_miner_image_bg_sel);
    BackgroundImage btn_miner_image_bg_unsel = new BackgroundImage(img_miner_unsel, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_miner_bg_unsel = new Background(btn_miner_image_bg_unsel);
    
    BackgroundImage btn_floater_image_bg_sel = new BackgroundImage(img_floater_sel,BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_floater_bg_sel = new Background(btn_floater_image_bg_sel);
    BackgroundImage btn_floater_image_bg_unsel = new BackgroundImage(img_floater_unsel,BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_floater_bg_unsel = new Background(btn_floater_image_bg_unsel);
    
    BackgroundImage btn_blocker_image_bg_sel = new BackgroundImage(img_blocker_sel, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_blocker_bg_sel = new Background(btn_blocker_image_bg_sel);
    BackgroundImage btn_blocker_image_bg_unsel = new BackgroundImage(img_blocker_unsel, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_blocker_bg_unsel = new Background(btn_blocker_image_bg_unsel);
    
    BackgroundImage btn_swimmer_image_bg_sel = new BackgroundImage(img_swimmer_sel, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_swimmer_bg_sel = new Background(btn_swimmer_image_bg_sel);
    BackgroundImage btn_swimmer_image_bg_unsel = new BackgroundImage(img_swimmer_unsel, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_swimmer_bg_unsel = new Background(btn_swimmer_image_bg_unsel);
    
    BackgroundImage btn_exploder_image_bg_sel = new BackgroundImage(img_exploder_sel, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_exploder_bg_sel = new Background(btn_exploder_image_bg_sel);
    BackgroundImage btn_exploder_image_bg_unsel = new BackgroundImage(img_exploder_unsel, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
    Background btn_exploder_bg_unsel = new Background(btn_exploder_image_bg_unsel);
        
    private Label lbl_progress; 
    
    private final Scene scene;
    private final GameRenderer renderer;
    
    private SceneWindow window;
    
    public WorldView(Scene scene, GameRenderer renderer) {
    	this.scene = scene;
    	this.renderer = renderer;
    }
    
    public Label set_qty_label(ToggleButton btn, Label lbl){
        lbl.setText("0");
        lbl.setMinSize(btn_stickling_width, label_height);
        lbl.setAlignment(Pos.CENTER);
        lbl.setLayoutX(btn.getLayoutX());
        lbl.setLayoutY(btn.getLayoutY() - label_height);
        lbl.setTextFill(Paint.valueOf("White"));
        return lbl;
    }
    
    public Label set_name_label(String s, ToggleButton btn, Label lbl){
        lbl.setText(s);
        lbl.setMinSize(btn_stickling_width, label_height);
        lbl.setAlignment(Pos.CENTER);
        lbl.setLayoutX(btn.getLayoutX());
        lbl.setLayoutY(btn.getLayoutY() + btn_stickling_height);
        lbl.setTextFill(Paint.valueOf("White"));
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
        
        Label  lbl_miner = new Label();
        Label  lbl_floater = new Label();
        Label  lbl_blocker = new Label();
        Label  lbl_swimmer = new Label();
        Label  lbl_exploder = new Label();
        
        Label  qty_miner = new Label();
        Label  qty_floater = new Label();
        Label  qty_blocker = new Label();
        Label  qty_swimmer = new Label();
        Label  qty_exploder = new Label();        
        
        lbl_progress = new Label();
        Label lbl_goal = new Label();        
        //---------------------------------------------------------------------- 
        btn_miner.setMinSize(btn_stickling_width, btn_stickling_height);
        btn_miner.setLayoutX(0);
        btn_miner.setLayoutY(game_height - btn_stickling_height - label_height);
        btn_miner.setBackground(btn_miner_bg_unsel);
        btn_miner.setCursor(Cursor.HAND);
        btn_miner.setOnAction(e -> selectType(btn_miner, SticklingType.Miner));
        
        qty_miner = set_qty_label(btn_miner, qty_miner);      
        
        lbl_miner = set_name_label("Miner", btn_miner, lbl_miner);
        
        //---------------------------------------------------------------------- 
        btn_floater.setMinSize(btn_stickling_width, btn_stickling_height);
        btn_floater.setLayoutX(btn_miner.getLayoutX() + btn_stickling_width);
        btn_floater.setLayoutY(game_height - btn_stickling_height - label_height);
        btn_floater.setBackground(btn_floater_bg_unsel);
        btn_floater.setCursor(Cursor.HAND);
        btn_floater.setOnAction(e -> selectType(btn_floater, SticklingType.Floater));
        
        qty_floater = set_qty_label(btn_floater, qty_floater);
        
        lbl_floater = set_name_label("Floater", btn_floater, lbl_floater);

        //---------------------------------------------------------------------- 
        btn_blocker.setMinSize(btn_stickling_width, btn_stickling_height);
        btn_blocker.setLayoutX(btn_floater.getLayoutX() + btn_stickling_width);
        btn_blocker.setLayoutY(game_height - btn_stickling_height - label_height);
        btn_blocker.setBackground(btn_blocker_bg_unsel);
        btn_blocker.setCursor(Cursor.HAND);
        btn_blocker.setOnAction(e -> selectType(btn_blocker, SticklingType.Blocker));
        
        qty_blocker = set_qty_label(btn_blocker, qty_blocker);
        
        lbl_blocker = set_name_label("Blocker", btn_blocker, lbl_blocker);
        
        //----------------------------------------------------------------------
        
        
        btn_swimmer.setMinSize(btn_stickling_width, btn_stickling_height);
        btn_swimmer.setLayoutX(btn_blocker.getLayoutX() + btn_stickling_width);
        btn_swimmer.setLayoutY(game_height - btn_stickling_height - label_height);
        btn_swimmer.setBackground(btn_swimmer_bg_unsel);
        btn_swimmer.setCursor(Cursor.HAND);
        btn_swimmer.setOnAction(e -> selectType(btn_swimmer, SticklingType.Swimmer));
        
        qty_swimmer = set_qty_label(btn_swimmer, qty_swimmer);
        
        lbl_swimmer = set_name_label("Swimmer", btn_swimmer, lbl_swimmer);
       
        //----------------------------------------------------------------------
        btn_exploder.setMinSize(btn_stickling_width, btn_stickling_height);
        btn_exploder.setLayoutX(btn_swimmer.getLayoutX() + btn_stickling_width);
        btn_exploder.setLayoutY(game_height - btn_stickling_height - label_height);
        btn_exploder.setBackground(btn_exploder_bg_unsel);
        btn_exploder.setCursor(Cursor.HAND);
        btn_exploder.setOnAction(e -> selectType(btn_exploder, SticklingType.Exploder));
        
        qty_exploder = set_qty_label(btn_exploder, qty_exploder);
        
        lbl_exploder = set_name_label("Exploder", btn_exploder, lbl_exploder);

        //----------------------------------------------------------------------
        btn_speed_normal.setText(">");
        btn_speed_normal.setTextAlignment(TextAlignment.CENTER);
        btn_speed_normal.setMinSize(30, 25);
        btn_speed_normal.setLayoutX(btn_exploder.getLayoutX() + btn_stickling_width + 10);
        btn_speed_normal.setLayoutY(btn_exploder.getLayoutY());
        
        btn_speed_fast.setText(">>");
        btn_speed_fast.setTextAlignment(TextAlignment.CENTER);
        btn_speed_fast.setMinSize(30, 25);
        btn_speed_fast.setLayoutX(btn_speed_normal.getLayoutX() + 30 + 10);
        btn_speed_fast.setLayoutY(btn_exploder.getLayoutY());
        
        btn_speed_faster.setText(">>>");
        btn_speed_faster.setTextAlignment(TextAlignment.CENTER);
        btn_speed_faster.setMinSize(30, 25);
        btn_speed_faster.setLayoutX(btn_speed_fast.getLayoutX() + 30 + 10);
        btn_speed_faster.setLayoutY(btn_exploder.getLayoutY());
        
        btn_reset.setText("Reset");
        btn_reset.setTextAlignment(TextAlignment.CENTER);
        btn_reset.setMinSize(120, 25);
        btn_reset.setLayoutX(btn_exploder.getLayoutX() + btn_stickling_width + 10);
        btn_reset.setLayoutY(btn_speed_normal.getLayoutY() + 35);
                              
        btn_kill_all.setText("Kill All");
        btn_kill_all.setTextAlignment(TextAlignment.CENTER);
        btn_kill_all.setMinSize(120, 25);
        btn_kill_all.setLayoutX(btn_exploder.getLayoutX() + btn_stickling_width + 10);
        btn_kill_all.setLayoutY(btn_reset.getLayoutY() + 35);
        
        btn_kill_all.setOnAction(e -> scene.findEntities(Stickling.class).forEach(stick -> {
            Stickling newStickling = SticklingType.Exploder.create();
            newStickling.copyFrom(stick);            
            scene.addEntity(newStickling);
            stick.remove();
            
        }));
        
        lbl_goal.setText("Goal:");
        lbl_goal.setTextAlignment(TextAlignment.CENTER);
        lbl_goal.setFont(javafx.scene.text.Font.font(20));
        lbl_goal.setMinSize(60, 25);
        lbl_goal.setLayoutX(btn_reset.getLayoutX() + 120 + 10);
        lbl_goal.setLayoutY(btn_speed_faster.getLayoutY());
        lbl_goal.setTextFill(Paint.valueOf("White"));
        
        lbl_progress.setTextAlignment(TextAlignment.CENTER);
        lbl_progress.setFont(javafx.scene.text.Font.font(20));
        lbl_progress.setMinSize(60, 25);
        lbl_progress.setLayoutX(btn_reset.getLayoutX() + 120 + 10);
        lbl_progress.setLayoutY(btn_reset.getLayoutY());
        lbl_progress.setTextFill(Paint.valueOf("White"));
        
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
        root.getChildren().add(window);
                
        return root;
    }
    
    private void selectType(ToggleButton button, SticklingType type) {
    	if (!button.isSelected()) {
    		root.setCursor(Cursor.DEFAULT);
    		window.selectType(null);
                switch (type) {
                    case Blocker:
                        btn_blocker.setBackground(btn_blocker_bg_unsel);
                        break;
                    case Exploder:
                        btn_exploder.setBackground(btn_exploder_bg_unsel);
                        break;
                    case Floater:
                        btn_floater.setBackground(btn_floater_bg_unsel);
                        break;
                    case Miner:
                        btn_miner.setBackground(btn_miner_bg_unsel);
                        break;
                    case Swimmer:
                        btn_swimmer.setBackground(btn_swimmer_bg_unsel);
                        break;
                    default:
			throw new AssertionError(type);
                }             
                
    		return;
    	} else
        {
            switch (type) {
                    case Blocker:
                        btn_blocker.setBackground(btn_blocker_bg_sel);
                        break;
                         case Exploder:
                        btn_exploder.setBackground(btn_exploder_bg_sel);
                        break;
                    case Floater:
                        btn_floater.setBackground(btn_floater_bg_sel);
                        break;
                    case Miner:
                        btn_miner.setBackground(btn_miner_bg_sel);
                        break;
                    case Swimmer:
                        btn_swimmer.setBackground(btn_swimmer_bg_sel);
                        break;
                    default:
			throw new AssertionError(type);
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
        Level stats = Game.getInstance().getLevel().get();
    	lbl_progress.setText(String.format("%d/%d", scene.getSuccessfulSticklings(), scene.getTotalSticklings()));
        //do star stuff here
        if(scene.getSuccessfulSticklings() > (0.3 * scene.getTotalSticklings())){
            star1.setVisible(true);
        }
        if(scene.getSuccessfulSticklings() > (0.6 * scene.getTotalSticklings())){
            star2.setVisible(true);
        }
        if(scene.getSuccessfulSticklings() > (0.9 * scene.getTotalSticklings())){
            star3.setVisible(true);
        }
    }
}
