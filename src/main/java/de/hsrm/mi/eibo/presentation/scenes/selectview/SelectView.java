package de.hsrm.mi.eibo.presentation.scenes.selectview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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
public class SelectView extends AnchorPane {

    VBox content;
    HBox options;
    Button menuButton;
    AnchorPane layer;

    public SelectView() {
        Label header = new Label("select your level of expertise");
        header.getStyleClass().add("h2");

        options = new HBox();
        options.setAlignment(Pos.TOP_CENTER);
        options.setSpacing(40);

        content = new VBox();
        content.getStyleClass().add("window");
        content.setSpacing(50); 
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(60,40,60, 40));
        content.getChildren().addAll(header, options);

        layer = new AnchorPane();
        layer.setId("transparent");
        layer.setVisible(false);

        menuButton = new Button("");
        menuButton.setId("menu-button");
        
        AnchorPane.setTopAnchor(menuButton, 10.0);
        AnchorPane.setLeftAnchor(menuButton, 10.0);

        getChildren().addAll(content, menuButton, layer);
        getStyleClass().add("window");
    }
    
}