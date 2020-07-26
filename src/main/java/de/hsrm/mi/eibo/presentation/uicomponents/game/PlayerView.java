package de.hsrm.mi.eibo.presentation.uicomponents.game;

import de.hsrm.mi.eibo.business.gamelogic.*;

import javafx.animation.AnimationTimer;
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
    private AnimationTimer timer;

    private double posX, posY;

    private Image normalImg, jumpImg, dropImg;

    public PlayerView(Player player) {
        normalImg = new Image(getClass().getResource("/images/player.png").toString());
        jumpImg = new Image(getClass().getResource("/images/player_jump.png").toString());
        dropImg = new Image(getClass().getResource("/images/player_drop.png").toString());

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

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) { }
        };

        player.getJumpProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) image.setImage(jumpImg);
            }    
        });

        player.getBoostProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) image.setImage(jumpImg);
            }    
        });

        player.getDropProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) image.setImage(dropImg);
            }    
        });

        player.getMoveProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) image.setImage(normalImg);
            } 
        });

        player.changes.addPropertyChangeListener("koordinaten", event -> {
            setLayoutX(player.getPosX());
            setLayoutY(player.getPosY());
        });
    }
    
    public void startAnimation() {
        timer.start();
    }

    public void stopAnimation() {
        timer.stop();
    }
}