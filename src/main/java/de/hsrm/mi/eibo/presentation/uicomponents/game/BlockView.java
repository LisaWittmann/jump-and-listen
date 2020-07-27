package de.hsrm.mi.eibo.presentation.uicomponents.game;

import de.hsrm.mi.eibo.business.gamelogic.Block;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * Darstellung eines Blocks
 * @author pwieg001, lwitt001, lgers001
 */
public class BlockView extends StackPane {

    Block block;
    Button addButton;

    public BlockView(Block block) {
        this.block = block;

        setPrefSize(block.getWidth(), block.getHeight());
        if(block.isInitialized().get()) {
            getStyleClass().add("block");
        } 
        else initEmptyBlock();
    }

    public void initEmptyBlock() { 
        addButton = new Button("+");
        addButton.getStyleClass().add("text-button");
        
        addButton.addEventHandler(ActionEvent.ACTION, event -> {
            getStyleClass().clear();
            getStyleClass().add("block");
            block.isInitialized().set(true);
            addButton.setVisible(false);
            addButton = null;
        });

        getStyleClass().add("empty-block");
        getChildren().add(addButton);
        StackPane.setAlignment(addButton, Pos.CENTER);
    }

    public Block getBlock() {
       return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

}