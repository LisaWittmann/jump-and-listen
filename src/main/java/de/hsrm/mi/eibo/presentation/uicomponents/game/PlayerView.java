package de.hsrm.mi.eibo.presentation.uicomponents.game;

import de.hsrm.mi.eibo.business.gamelogic.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Darstellung eines Spielers
 * Wechsel des Anzeigebildes
 * @author pwieg001, lwitt001, lgers001
 */
public class PlayerView extends StackPane {

    private ImageView image;
    private Image normalImg, moveImg;

    public PlayerView(Player player) {
        normalImg = new Image(getClass().getResource("/images/player.png").toString());
        moveImg = new Image(getClass().getResource("/images/player_drop.png").toString());

        image = new ImageView();
        image.setFitWidth(100);
        image.setFitHeight(100);
        image.setImage(normalImg);

        setPrefSize(100, 100);
        getChildren().add(image);

        setLayoutX(player.getPosX());
        setLayoutY(player.getPosY());

        // Ändern des Anzeigebilds, je nach gefeuertem Event
        player.jumpProperty().addListener(new ChangeListener<Boolean>() {
        
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) image.setImage(moveImg);
            }    

        });

        player.boostProperty().addListener(new ChangeListener<Boolean>() {
        
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) image.setImage(moveImg);
            }    

        });

        player.dropProperty().addListener(new ChangeListener<Boolean>() {
        
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) image.setImage(moveImg);
            }   

        });

        player.moveProperty().addListener(new ChangeListener<Boolean>(){
            
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) image.setImage(normalImg);
            } 

        });

        player.landedProperty().addListener(new ChangeListener<Boolean>(){
    
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) image.setImage(normalImg);
            }
            
        });

        
        // PlayerView auf Startposition setzen, wenn Game gestartet wird
        player.startProperty().addListener(new ChangeListener<Boolean>(){

            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    setLayoutX(player.getStartPosX());
                    setLayoutY(player.getStartPosY());
                }
            }
            
        });
    }
}