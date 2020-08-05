package de.hsrm.mi.eibo.presentation.uicomponents.game;

import de.hsrm.mi.eibo.business.gamelogic.*;
import de.hsrm.mi.eibo.presentation.scenes.gameview.GameViewController;
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
    private Image normalImg, moveImg;

    private AnimationTimer timer;

    public PlayerView(Player player, GameViewController game) {
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

        player.startProperty().addListener(new ChangeListener<Boolean>(){

            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    setLayoutX(player.getStartPosX());
                    setLayoutY(player.getStartPosY());
                }
            }
            
        });

        timer = new AnimationTimer() {

            private long lastUpdated = 0;
            private long lastRendered = 0;
            private final int UPS = 30;
            private final int FPS = 60;

            private final int SECONDS2NANO_SECONDS = 1_000 * 1_000_000; 
            private final int UPNS_DELTA = SECONDS2NANO_SECONDS / UPS;
            private final int FPNS_DELTA = SECONDS2NANO_SECONDS / FPS;

            @Override
            public void handle(long now) {
                
                if(lastUpdated + UPNS_DELTA < now) {
                    game.getApplication().getGame().activateMovement();
                    lastUpdated = now;
                }

                if(lastRendered + FPNS_DELTA < now) {
                    
                    if(player.getPosX() > game.getMid()) {
                        game.scrollBlocks(player.getPosX() - game.getMid());
                        setLayoutY(player.getPosY());
                    }
                    
                    else {
                        setLayoutX(player.getPosX());
                        setLayoutY(player.getPosY());
                    }
                }

            }
            
        };
    }

    public void startAnimation() {
        timer.start();
    }

    public void stopAnimtion() {
        timer.stop();
    }
}