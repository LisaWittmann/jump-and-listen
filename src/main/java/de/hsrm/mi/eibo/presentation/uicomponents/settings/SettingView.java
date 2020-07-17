package de.hsrm.mi.eibo.presentation.uicomponents.settings;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SettingView extends StackPane {

    Slider blockwidth, speed, volume;

    public SettingView() {

        Label header = new Label("settings");
        header.getStyleClass().add("h2");
        header.setStyle("-fx-text-fill: #2e2e2e;");

        Label widthLabel = new Label("block width:");
        widthLabel.getStyleClass().add("h3");
        widthLabel.setStyle("-fx-text-alignment: left; -fx-text-fill: #2e2e2e;");
        blockwidth = new Slider(0, 2, 1);

        Label speedLabel = new Label("speed:");
        speedLabel.getStyleClass().add("h3");
        speedLabel.setStyle("-fx-text-alignment: left; -fx-text-fill: #2e2e2e;");
        speedLabel.setMinWidth(widthLabel.getWidth());
        speed = new Slider(0, 2, 1);

        Label volumeLabel = new Label("volume:");
        volumeLabel.getStyleClass().add("h3");
        volumeLabel.setStyle("-fx-text-alignment: left; -fx-text-fill: #2e2e2e;");
        volumeLabel.setMinWidth(widthLabel.getWidth());
        volume = new Slider(0, 100, 80);

        GridPane settings = new GridPane();
        settings.setPadding(new Insets(40));
        settings.setHgap(60);
        settings.setVgap(20);
        settings.add(widthLabel, 0, 0);
        settings.add(blockwidth, 1, 0);
        settings.add(speedLabel, 0, 1);
        settings.add(speed, 1, 1);
        settings.add(volumeLabel, 0, 2);
        settings.add(volume, 1, 2);

        VBox content = new VBox();
        content.setSpacing(20);
        content.setAlignment(Pos.TOP_CENTER);
        content.getChildren().addAll(header, settings);

        setLayoutX(400);
        setLayoutY(200);
        setPadding(new Insets(40));
        setMaxSize(800, 400);
        getChildren().add(content);
        setAlignment(content, Pos.TOP_CENTER);
        
        getStyleClass().add("overlay");
        setEffect(new DropShadow(10, Color.LIGHTGREY));
    }
    
    
}