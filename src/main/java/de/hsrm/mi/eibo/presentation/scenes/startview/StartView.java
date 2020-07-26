package de.hsrm.mi.eibo.presentation.scenes.startview;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Startbildschirm
 * Leitet weiter zur SelectView
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class StartView extends VBox {

    Button startButton, createButton;

    public StartView() {

        startButton = new Button("start");
        startButton.getStyleClass().add("text-button");

        createButton = new Button("create");
        createButton.getStyleClass().add("text-button");

        Label label = new Label("or");
        label.getStyleClass().add("h4");

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(startButton, label, createButton);
        
        Label titleLabel = new Label("Jump & Listen");
        titleLabel.getStyleClass().add("h1");

        setSpacing(20);
        setAlignment(Pos.CENTER);
        getStyleClass().add("window");
        setId("start-view");

        getChildren().addAll(titleLabel, buttonBox);
    }
    
}