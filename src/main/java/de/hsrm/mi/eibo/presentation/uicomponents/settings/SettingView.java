package de.hsrm.mi.eibo.presentation.uicomponents.settings;

import de.hsrm.mi.eibo.presentation.application.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Viewkomponente für Spieleinstellungen
 * @author pwieg001, lwitt001, lgers001
 */
public class SettingView extends StackPane {

    Slider blockdistance, speed, volume;
    ComboBox<Theme> theme;
    GridPane settings;
    VBox content;

    public SettingView() {

        Label header = new Label("settings");
        header.getStyleClass().add("h2");
        header.setId("dark");

        Label distanceLabel = new Label("distance:");
        distanceLabel.getStyleClass().add("h3");
        distanceLabel.setStyle("-fx-text-alignment: left;");
        distanceLabel.setId("dark");
        blockdistance = new Slider(50, 150, 100);

        Label speedLabel = new Label("speed:");
        speedLabel.getStyleClass().add("h3");
        speedLabel.setStyle("-fx-text-alignment: left;");
        speedLabel.setId("dark");
        speed = new Slider(0.1, 2, 1);

        Label volumeLabel = new Label("volume:");
        volumeLabel.getStyleClass().add("h3");
        volumeLabel.setStyle("-fx-text-alignment: left;");
        volumeLabel.setId("dark");
        volume = new Slider(0, 100, 80);

        Label themeLabel = new Label("theme:");
        themeLabel.getStyleClass().add("h3");
        themeLabel.setStyle("-fx-text-alignment: left;");
        themeLabel.setId("dark");
        theme = new ComboBox<>();
        theme.getStyleClass().add("combo-box");

        settings = new GridPane();
        settings.setPadding(new Insets(40));
        settings.setHgap(60);
        settings.setVgap(20);
        settings.add(distanceLabel, 0, 0);
        settings.add(blockdistance, 1, 0);
        settings.add(speedLabel, 0, 1);
        settings.add(speed, 1, 1);
        settings.add(volumeLabel, 0, 2);
        settings.add(volume, 1, 2);
        settings.add(themeLabel, 0, 3);
        settings.add(theme, 1, 3);

        content = new VBox();
        content.setSpacing(20);
        content.setAlignment(Pos.TOP_CENTER);
        content.getChildren().addAll(header, settings);

        setPadding(new Insets(40));
        prefWidthProperty().set(600);
        prefHeightProperty().set(500);
        getChildren().add(content);
        setAlignment(content, Pos.TOP_CENTER);
        
        getStyleClass().add("overlay");
    }
    
    
}