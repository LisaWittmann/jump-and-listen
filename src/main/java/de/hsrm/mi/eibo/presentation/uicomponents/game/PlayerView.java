package de.hsrm.mi.eibo.presentation.uicomponents.game;

import de.hsrm.mi.eibo.business.gamelogic.*;
import javafx.animation.AnimationTimer;
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
    private boolean jump, boostJump, drop;

    public PlayerView(Player player) {
        image = new ImageView();
        image.setFitWidth(100);
        image.setFitHeight(100);
        //image.setImage(new Image(getClass().getResource("/images/player.png").toExternalForm()));

        posX = 200;
        posY = 200;
        //TODO: anpassen
        setLayoutX(posX);
        setLayoutY(posY);

        drop = false;
        jump = false;
        boostJump = false;

        timer = new AnimationTimer(){

            @Override
            public void handle(long now) {
                if(jump) {
                    //TODO
                }
                if(boostJump) {
                    //TODO
                }
                if(drop){
                    //TODO
                }
            }
        };
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void setBoostJump(boolean boostJump) {
        this.boostJump = boostJump;
    }

    public void setDrop(boolean drop) {
        this.drop = drop;
    }
    
    public void startAnimation() {
        timer.start();
    }

    public void stopAnimation() {
        timer.stop();
    }
}