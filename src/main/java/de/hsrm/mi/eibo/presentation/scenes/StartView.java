package de.hsrm.mi.eibo.presentation.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StartView extends VBox {

    Button startButton;

    public StartView(){

        startButton = new Button("Start now");
        startButton.getStyleClass().add("text-button");
        
        Label titleLabel = new Label("Jump & Listen");
        titleLabel.getStyleClass().add("h1");

        setSpacing(20);
        setAlignment(Pos.CENTER);
        getStyleClass().add("window");
        setId("start-view");

        getChildren().addAll(titleLabel, startButton);
        
    }
    
}