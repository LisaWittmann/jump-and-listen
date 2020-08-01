package de.hsrm.mi.eibo.presentation.uicomponents.game;

import de.hsrm.mi.eibo.business.gamelogic.*;
import de.hsrm.mi.eibo.presentation.scenes.gameview.GameViewController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Darstellung des Spielers
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class PlayerView extends StackPane {

    private ImageView image;

    private double posX, posY;

    private Image normalImg, moveImg;

    public PlayerView(Player player, GameViewController game) {
        normalImg = new Image(getClass().getResource("/images/player.png").toString());
        moveImg = new Image(getClass().getResource("/images/player_drop.png").toString());

        image = new ImageView();
        image.setFitWidth(100);
        image.setFitHeight(100);
        image.setImage(normalImg);

        setPrefSize(100, 100);
        getChildren().add(image);

        posX = 120;
        posY = 400;
        setLayoutX(posX);
        setLayoutY(posY);

        player.getJumpProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) image.setImage(moveImg);
            }    
        });

        player.getBoostProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) image.setImage(moveImg);
            }    
        });

        player.getDropProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) image.setImage(moveImg);
            }    
        });

        player.getMoveProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) image.setImage(normalImg);
            } 
        });

        player.getLandedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) image.setImage(normalImg);
            }
            
        });

        player.changes.addPropertyChangeListener("koordinaten", event -> {
            if(player.getPosX() > game.getMid()) {
                game.scrollBlocks((player.getPosX()-game.getMid()));
                setLayoutY(player.getPosY());
            }
            else {
                setLayoutX(player.getPosX());
                setLayoutY(player.getPosY());
            }
        });
    }
}