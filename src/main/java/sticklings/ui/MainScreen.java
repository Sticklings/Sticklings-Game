/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sticklings.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author swann
 */

public class MainScreen extends Screen {
    public int btn_height = 75;
    public int btn_width = 150;
    
    @Override
    public Parent initialize() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Image main_background_image = null;
        Image btn_play_image = null;
        Image btn_help_image = null;
        Image btn_settings_image = null;
        try{ //Load images for the main screen
            main_background_image = new Image (MainScreen.class.getClass().getResourceAsStream("/ui/title screen.png"));
            btn_play_image = new Image (MainScreen.class.getClass().getResourceAsStream("/ui/btn_play.png"));
            btn_help_image = new Image (MainScreen.class.getClass().getResourceAsStream("/ui/btn_help.png"));
            btn_settings_image = new Image (MainScreen.class.getClass().getResourceAsStream("/ui/btn_settings.png"));
        }
        catch(Exception e ){
            System.out.print("Can't load an image");
            e.printStackTrace();
        }
            
        BackgroundSize main_bg_size = new BackgroundSize(500, 500, true, true, true, false);
        BackgroundSize btn_bg_size = new BackgroundSize(btn_width, btn_height, true, true, true, false);
        
        BackgroundImage main_bg_image = new BackgroundImage(main_background_image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, main_bg_size);
        BackgroundImage btn_play_bg_image = new BackgroundImage(btn_play_image,BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_bg_size);
        BackgroundImage btn_help_bg_image = new BackgroundImage(btn_help_image,BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_bg_size);
        BackgroundImage btn_settings_bg_image = new BackgroundImage(btn_settings_image,BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, btn_bg_size);
        
        Background main_bg = new Background(main_bg_image);
        Background btn_play_bg = new Background(btn_play_bg_image);
        Background btn_help_bg = new Background(btn_help_bg_image);
        Background btn_settings_bg = new Background(btn_settings_bg_image);
        
        Button btn_play = new Button();
        btn_play.setText("Play");
        btn_play.setBackground(btn_play_bg);
        btn_play.setCursor(Cursor.HAND);
        btn_play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Play the game");
            }
        });
        
        Button btn_settings = new Button();
        btn_settings.setText("Settings");
        btn_settings.setBackground(btn_settings_bg);
        btn_settings.setCursor(Cursor.HAND);
        btn_settings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                System.out.println("Settings");
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Settings");
                alert.setHeaderText("How to mod Sticklings");
                alert.setContentText("yeah not quite there yet");
                alert.showAndWait();
            }
        });
        
        Button btn_help = new Button();
        btn_help.setText("Help");
        btn_help.setBackground(btn_help_bg);
        btn_help.setCursor(Cursor.HAND);
        btn_help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                System.out.println("HELP ME");
                Alert alert = new Alert(AlertType.INFORMATION);
                
                alert.setTitle("Help");
                alert.setHeaderText("How to play Sticklings");
                alert.setContentText("Look up Lemmings, not that we want any copyright issues...");
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
