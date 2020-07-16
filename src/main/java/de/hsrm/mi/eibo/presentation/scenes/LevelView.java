package de.hsrm.mi.eibo.presentation.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LevelView extends VBox {

    HBox options;

    public LevelView(){

        Label header = new Label("select your level of expertise");
        header.getStyleClass().add("h2");

        options = new HBox();
        options.setAlignment(Pos.TOP_CENTER);
        options.setSpacing(40);

        getStyleClass().add("window");
        setAlignment(Pos.CENTER);
        setSpacing(50);
        setPadding(new Insets(40));
        getChildren().addAll(header, options);
        
    }
    
}