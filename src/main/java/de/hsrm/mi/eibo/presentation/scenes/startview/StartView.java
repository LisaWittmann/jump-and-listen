package de.hsrm.mi.eibo.presentation.scenes.startview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
    ImageView image;

    public StartView() {

        startButton = new Button("start");
        startButton.getStyleClass().add("text-button");

        createButton = new Button("create");
        createButton.getStyleClass().add("text-button");

        Label label = new Label("or");
        label.getStyleClass().add("h4");

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(-10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(startButton, label, createButton);
        
        Label titleLabel = new Label("Jump & Listen");
        titleLabel.getStyleClass().add("h1");
        titleLabel.setStyle("-fx-text-alignment: left;");

        image = new ImageView();
        image.setFitHeight(200);
        image.setFitWidth(200);

        setSpacing(25);
        setPadding(new Insets(100));
        setAlignment(Pos.CENTER);
        getStyleClass().add("window");
        setId("start-view");

        getChildren().addAll(image, titleLabel, buttonBox);
    }
    
}