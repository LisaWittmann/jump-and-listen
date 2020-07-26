package de.hsrm.mi.eibo.presentation.uicomponents.game;

import de.hsrm.mi.eibo.business.tone.Tone;

import javafx.scene.shape.Rectangle;

/**
 * Darstellung eines Tons
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class BlockView extends Rectangle {

    Tone tone;
    boolean flagged;
    
    final static double minWidth = 250;
    final static double minHeight = 300;

    public BlockView(Tone tone) {
        super(minWidth, minHeight + tone.getFrequenz() / 5);
        this.tone = tone;
        flagged = false;

        getStyleClass().add("block");
        
    }

    public BlockView() {
        super(minWidth, minHeight);
        tone = null;
        flagged = true;

        getStyleClass().add("block");
    }

    public Tone getTone() {
       return tone;
    }

    public boolean isFlagged() {
        return flagged;
    }
}