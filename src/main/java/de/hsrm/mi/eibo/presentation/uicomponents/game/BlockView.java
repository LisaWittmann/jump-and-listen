package de.hsrm.mi.eibo.presentation.uicomponents.game;

import de.hsrm.mi.eibo.business.gamelogic.Block;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * Darstellung eines Blocks
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class BlockView extends StackPane {

    Block block;
    Button addButton;

    public BlockView(Block block) {
        this.block = block;

        setPrefSize(block.getWidth(), block.getHeight());
        if (block.isInitialized().get()) {
            getStyleClass().add("block");
        } else
            initEmptyBlock();
        setLayoutX(block.getPosX());
        setLayoutY(block.getPosY());
        addChangeListener();
    }

    public void initEmptyBlock() {
        addButton = new Button("+");
        addButton.getStyleClass().add("text-button");

        addButton.addEventHandler(ActionEvent.ACTION, event -> block.isInitialized().set(true));

        getStyleClass().add("empty");
        getChildren().add(addButton);
        StackPane.setAlignment(addButton, Pos.CENTER);
    }

    public void addChangeListener() {
        if (block == null)
            return;
        block.changes.addPropertyChangeListener("posX", event -> setLayoutX(block.getPosX()));
        block.changes.addPropertyChangeListener("posY", event -> setLayoutY(block.getPosY()));
        block.changes.addPropertyChangeListener("height", event -> setPrefHeight(block.getHeight()));

        block.isIntersected().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    getStyleClass().clear();
                    getStyleClass().add("hit");
                }
            }          
        });

        block.isInitialized().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                getStyleClass().clear();
                getStyleClass().add("block");
                if(addButton != null) {
                    addButton.setVisible(false);
                }
            }   
        });
    }

    public Block getBlock() {
       return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public void setLayout(){
        block.setPosX(getLayoutX());
        block.setPosY(getLayoutY());
    }

}