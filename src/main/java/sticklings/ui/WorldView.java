/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sticklings.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;

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
    
    @Override
    public Parent initialize() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Pane root = new Pane();
        
        Button btn_speed_normal = new Button();
        Button btn_speed_fast = new Button();
        Button btn_speed_faster = new Button();
        Button btn_kill_all = new Button();
        Button btn_reset = new Button();
                
        Button btn_miner = new Button();
        Button btn_floater = new Button();
        Button btn_blocker = new Button();
        Button btn_swimmer = new Button();
        Button btn_exploder = new Button();
        
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
        
        
        Label lbl_goal = new Label();
        Label lbl_progress = new Label();
        
        BackgroundSize  btn_stickling_bg_size = new BackgroundSize(btn_stickling_width, btn_stickling_height, true, true, true, false);
        
       
        final Image img_miner = new Image(WorldView.class.getResourceAsStream("/ui/btn_miner.png"));
        final Image img_floater = new Image(WorldView.class.getResourceAsStream("/ui/btn_floater.png"));
        final Image img_blocker = new Image(WorldView.class.getResourceAsStream("/ui/btn_blocker.png"));
        final Image img_swimmer = new Image(WorldView.class.getResourceAsStream("/ui/btn_swimmer.png"));
        final Image img_exploder = new Image(WorldView.class.getResourceAsStream("/ui/btn_exploder.png"));
      
        
        //---------------------------------------------------------------------- 
        BackgroundImage btn_miner_image_bg = new BackgroundImage(img_miner, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
        Background btn_miner_bg = new Background(btn_miner_image_bg);
                
        btn_miner.setMinSize(btn_stickling_width, btn_stickling_height);
        btn_miner.setLayoutX(0);
        btn_miner.setLayoutY(game_height - btn_stickling_height - label_height);
        btn_miner.setBackground(btn_miner_bg);
        btn_miner.setCursor(Cursor.HAND);
        btn_miner.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                root.setCursor(new ImageCursor(img_miner, img_miner.getWidth()/2, img_miner.getHeight()/2));                
            }
        });
       
        qty_miner.setText("0");
        qty_miner.setMinSize(btn_stickling_width, label_height);
        qty_miner.setAlignment(Pos.CENTER);
        qty_miner.setLayoutX(btn_miner.getLayoutX());
        qty_miner.setLayoutY(btn_miner.getLayoutY() - label_height);        
        
        lbl_miner.setText("Miner");
        lbl_miner.setMinSize(btn_stickling_width, label_height);
        lbl_miner.setAlignment(Pos.CENTER);
        lbl_miner.setLayoutX(btn_miner.getLayoutX());
        lbl_miner.setLayoutY(btn_miner.getLayoutY() + btn_stickling_height);
        //---------------------------------------------------------------------- 
        BackgroundImage btn_floater_image_bg = new BackgroundImage(img_floater,BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
        Background btn_floater_bg = new Background(btn_floater_image_bg);
                
        btn_floater.setMinSize(btn_stickling_width, btn_stickling_height);
        btn_floater.setLayoutX(btn_miner.getLayoutX() + btn_stickling_width);
        btn_floater.setLayoutY(game_height - btn_stickling_height - label_height);
        btn_floater.setBackground(btn_floater_bg);
        btn_floater.setCursor(Cursor.HAND);
        btn_floater.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                root.setCursor(new ImageCursor(img_floater, img_floater.getWidth()/2, img_floater.getHeight()/2));                
            }
        });
        
        qty_floater.setText("0");
        qty_floater.setMinSize(btn_stickling_width, label_height);
        qty_floater.setAlignment(Pos.CENTER);
        qty_floater.setLayoutX(btn_floater.getLayoutX());
        qty_floater.setLayoutY(btn_floater.getLayoutY() - label_height);   
        
        lbl_floater.setText("Floater");
        lbl_floater.setMinSize(btn_stickling_width, label_height);
        lbl_floater.setAlignment(Pos.CENTER);
        lbl_floater.setLayoutX(btn_floater.getLayoutX());
        lbl_floater.setLayoutY(btn_floater.getLayoutY() + btn_stickling_height);
        //---------------------------------------------------------------------- 
        BackgroundImage btn_blocker_image_bg = new BackgroundImage(img_blocker, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
        Background btn_blocker_bg = new Background(btn_blocker_image_bg);
        
        btn_blocker.setMinSize(btn_stickling_width, btn_stickling_height);
        btn_blocker.setLayoutX(btn_floater.getLayoutX() + btn_stickling_width);
        btn_blocker.setLayoutY(game_height - btn_stickling_height - label_height);
        btn_blocker.setBackground(btn_blocker_bg);
        btn_blocker.setCursor(Cursor.HAND);
        btn_blocker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                root.setCursor(new ImageCursor(img_blocker, img_blocker.getWidth()/2, img_blocker.getHeight()/2));                
            }
        });
        
        qty_blocker.setText("0");
        qty_blocker.setMinSize(btn_stickling_width, label_height);
        qty_blocker.setAlignment(Pos.CENTER);
        qty_blocker.setLayoutX(btn_blocker.getLayoutX());
        qty_blocker.setLayoutY(btn_blocker.getLayoutY() - label_height);   
        
        lbl_blocker.setText("Blocker");
        lbl_blocker.setMinSize(btn_stickling_width, label_height);
        lbl_blocker.setAlignment(Pos.CENTER);
        lbl_blocker.setLayoutX(btn_blocker.getLayoutX());
        lbl_blocker.setLayoutY(btn_blocker.getLayoutY() + btn_stickling_height);
        //----------------------------------------------------------------------
        BackgroundImage btn_swimmer_image_bg = new BackgroundImage(img_swimmer, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
        Background btn_swimmer_bg = new Background(btn_swimmer_image_bg);
        
        btn_swimmer.setMinSize(btn_stickling_width, btn_stickling_height);
        btn_swimmer.setLayoutX(btn_blocker.getLayoutX() + btn_stickling_width);
        btn_swimmer.setLayoutY(game_height - btn_stickling_height - label_height);
        btn_swimmer.setBackground(btn_swimmer_bg);
        btn_swimmer.setCursor(Cursor.HAND);
        btn_swimmer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                root.setCursor(new ImageCursor(img_swimmer, img_swimmer.getWidth()/2, img_swimmer.getHeight()/2));                
            }
        });
          
        qty_swimmer.setText("0");
        qty_swimmer.setMinSize(btn_stickling_width, label_height);
        qty_swimmer.setAlignment(Pos.CENTER);
        qty_swimmer.setLayoutX(btn_swimmer.getLayoutX());
        qty_swimmer.setLayoutY(btn_swimmer.getLayoutY() - label_height);   
                
        lbl_swimmer.setText("Swimmer");
        lbl_swimmer.setMinSize(btn_stickling_width, label_height);
        lbl_swimmer.setAlignment(Pos.CENTER);
        lbl_swimmer.setLayoutX(btn_swimmer.getLayoutX());
        lbl_swimmer.setLayoutY(btn_swimmer.getLayoutY() + btn_stickling_height);        
        //----------------------------------------------------------------------
        BackgroundImage btn_exploder_image_bg = new BackgroundImage(img_exploder, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_stickling_bg_size);
        Background btn_exploder_bg = new Background(btn_exploder_image_bg);
        
        btn_exploder.setMinSize(btn_stickling_width, btn_stickling_height);
        btn_exploder.setLayoutX(btn_swimmer.getLayoutX() + btn_stickling_width);
        btn_exploder.setLayoutY(game_height - btn_stickling_height - label_height);
        btn_exploder.setBackground(btn_exploder_bg);
        btn_exploder.setCursor(Cursor.HAND);
        btn_exploder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                root.setCursor(new ImageCursor(img_exploder, img_exploder.getWidth()/2, img_exploder.getHeight()/2));                
            }
        });
        
        qty_exploder.setText("0");
        qty_exploder.setMinSize(btn_stickling_width, label_height);
        qty_exploder.setAlignment(Pos.CENTER);
        qty_exploder.setLayoutX(btn_exploder.getLayoutX());
        qty_exploder.setLayoutY(btn_exploder.getLayoutY() - label_height);   
                
        lbl_exploder.setText("Exploder");
        lbl_exploder.setMinSize(btn_stickling_width, label_height);
        lbl_exploder.setAlignment(Pos.CENTER);
        lbl_exploder.setLayoutX(btn_exploder.getLayoutX());
        lbl_exploder.setLayoutY(btn_exploder.getLayoutY() + btn_stickling_height);
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
        
        lbl_goal.setText("Goal:");
        lbl_goal.setTextAlignment(TextAlignment.CENTER);
        lbl_goal.setFont(javafx.scene.text.Font.font(20));
        lbl_goal.setMinSize(60, 25);
        lbl_goal.setLayoutX(btn_reset.getLayoutX() + 120 + 10);
        lbl_goal.setLayoutY(btn_speed_faster.getLayoutY());
        
        lbl_progress.setText("0 / 150");
        lbl_progress.setTextAlignment(TextAlignment.CENTER);
        lbl_progress.setFont(javafx.scene.text.Font.font(20));
        lbl_progress.setMinSize(60, 25);
        lbl_progress.setLayoutX(btn_reset.getLayoutX() + 120 + 10);
        lbl_progress.setLayoutY(btn_reset.getLayoutY());
        
  
        //Image star = new Image(WorldView.class.getResourceAsStream("/star.png"));
        //ImageView star1 = new ImageView(star);
        //star1.setLayoutX(btn_reset.getLayoutX() + 120 + 10);
        //star1.setLayoutY(btn_kill_all.getLayoutY());
        
        //ImageView star2 = new ImageView(star);
        //star2.setLayoutX(star1.getLayoutX() + 20 + 10);
        //star2.setLayoutY(btn_kill_all.getLayoutY());
        
        //ImageView star3 = new ImageView(star);
        //star3.setLayoutX(star2.getLayoutX() + 20 + 10);
        //star3.setLayoutY(btn_kill_all.getLayoutY());
        
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
        
        return root;
    }

    @Override
    public void onShow() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onHide() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(double deltaTime) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}