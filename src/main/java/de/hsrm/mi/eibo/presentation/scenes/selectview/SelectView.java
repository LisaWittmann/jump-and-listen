package de.hsrm.mi.eibo.presentation.scenes.selectview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Anzeige Wahlmöglichkeiten bezüglich des Spiels vor Beginn
 * Auswahl der Schwierigkeitsstufe
 * 
 * Wird vor Start des Spiels gezeigt
 * Leitet nach Auswahl auf GameView weiter
 * 
 * @author pwieg001, lwitt001, lger001
 */
public class SelectView extends VBox {

    HBox options;

    public SelectView() {

        Label header = new Label("select your level of expertise");
        header.getStyleClass().add("h2");

        options = new HBox();
        options.setAlignment(Pos.TOP_CENTER);
        options.setSpacing(40);

        getStyleClass().add("window");
        setSpacing(50);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(40));
        
        getChildren().addAll(header, options);
    }
    
}