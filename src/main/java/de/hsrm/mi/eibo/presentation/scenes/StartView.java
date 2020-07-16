package de.hsrm.mi.eibo.presentation.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StartView extends VBox {

    public StartView(){
        
        Label titleLabel = new Label("Title");
        titleLabel.getStyleClass().add("h1");

        Label headerLabel = new Label("Header");
        headerLabel.getStyleClass().add("h2");

        Label textLabel = new Label("Normal Text");
        textLabel.getStyleClass().add("normal-text");

        setPadding(new Insets(100));
        setSpacing(20);
        setAlignment(Pos.CENTER);
        getStyleClass().add("window");

        getChildren().addAll(titleLabel, headerLabel, textLabel);
        
    }
    
}