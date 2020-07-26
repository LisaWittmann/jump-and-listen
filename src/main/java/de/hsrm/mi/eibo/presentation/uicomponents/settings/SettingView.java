package de.hsrm.mi.eibo.presentation.uicomponents.settings;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Overlay View für Spieleinstellungen
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class SettingView extends StackPane {

    Slider blockwidth, speed, volume;
    GridPane settings;
    VBox content;

    public SettingView() {

        Label header = new Label("settings");
        header.getStyleClass().add("h2-dark");

        Label widthLabel = new Label("block width:");
        widthLabel.getStyleClass().add("h3-dark");
        widthLabel.setStyle("-fx-text-alignment: left;");
        blockwidth = new Slider(0.1, 2.0, 1.0);

        Label speedLabel = new Label("speed:");
        speedLabel.getStyleClass().add("h3-dark");
        speedLabel.setStyle("-fx-text-alignment: left;");
        speedLabel.setMinWidth(widthLabel.getWidth());
        speed = new Slider(0.1, 2, 1);

        Label volumeLabel = new Label("volume:");
        volumeLabel.getStyleClass().add("h3-dark");
        volumeLabel.setStyle("-fx-text-alignment: left;");
        volumeLabel.setMinWidth(widthLabel.getWidth());
        volume = new Slider(0, 100, 80);

        settings = new GridPane();
        settings.setPadding(new Insets(40));
        settings.setHgap(60);
        settings.setVgap(20);
        settings.add(widthLabel, 0, 0);
        settings.add(blockwidth, 1, 0);
        settings.add(speedLabel, 0, 1);
        settings.add(speed, 1, 1);
        settings.add(volumeLabel, 0, 2);
        settings.add(volume, 1, 2);

        content = new VBox();
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
    }
    
    
}