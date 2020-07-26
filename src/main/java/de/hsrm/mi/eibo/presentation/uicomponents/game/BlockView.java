package de.hsrm.mi.eibo.presentation.uicomponents.game;

import de.hsrm.mi.eibo.business.gamelogic.Block;
import javafx.scene.shape.Rectangle;

/**
 * Darstellung eines Blocks
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class BlockView extends Rectangle {

    Block block;
    Rectangle innerRect;

    public BlockView(Block block) {
        super(block.getWidth(), block.getHeight());
        getStyleClass().add("block");

        innerRect = new Rectangle();
        block.changes.addPropertyChangeListener("widthFactor", event -> this.setWidth(block.getWidth()));
    }

}