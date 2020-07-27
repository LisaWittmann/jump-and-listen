package de.hsrm.mi.eibo.presentation.uicomponents.game;

import de.hsrm.mi.eibo.business.tone.Tone;
import de.hsrm.mi.eibo.presentation.uicomponents.create.Resizer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * Darstellung eines Tons
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class BlockView extends StackPane {

    Tone tone;
    Button addButton;

    SimpleBooleanProperty initialized;
    
    final static double maxWidth = 250;
    final static double minWidth = 100;
    final static double minHeight = 300;

    /**
     * Konstruktur
     * Erzeugung eines Blocks zu einem bestimmten Ton
     * 
     * @param tone dargestellter Ton
     */
    public BlockView(Tone tone) {
        this.tone = tone;
        initialized = new SimpleBooleanProperty(true);

        setPrefSize(minWidth, minHeight + tone.getFrequenz() / 5);
        getStyleClass().add("block");
    }

    /**
     * Konstruktor
     * Erzeugung einer Plattform
     * Start- und Endposition des Players
     */
    public BlockView() {
        tone = null;
        initialized = new SimpleBooleanProperty(true);

        setPrefSize(maxWidth, minHeight);
        getStyleClass().add("block");
    }

    /**
     * Konstruktor
     * Erzeugung eines leeren Blocks
     * Nutzung bei Songskonsturierung
     * 
     * @param empty Kennzeichnung
     */
    public BlockView(boolean empty) {
        tone = null;
        initialized = new SimpleBooleanProperty(false);

        addButton = new Button("+");
        addButton.getStyleClass().add("text-button");
        addButton.addEventHandler(ActionEvent.ACTION, event -> {
            getStyleClass().clear();
            getStyleClass().add("block");
            initialized.set(true);
            addButton.setVisible(false);
            addButton = null;
        });

        setPrefSize(minWidth, minHeight);
        getStyleClass().add("empty-block");
        getChildren().add(addButton);
        StackPane.setAlignment(addButton, Pos.CENTER);
    }

    public SimpleBooleanProperty isInitialized() {
        return initialized;
    }

    public Tone getTone() {
       return tone;
    }

    public void setTone(Tone tone) {
        this.tone = tone;
    }

}