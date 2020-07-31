package de.hsrm.mi.eibo.presentation.scenes.gameview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class GameView extends AnchorPane {

    Button settings;
    Label score;
    ComboBox<String> song;
    HBox songBox;
    AnchorPane field;

    public GameView() {
        score = new Label();
        score.getStyleClass().add("h3");
        score.setStyle("-fx-text-alignment: right;");

        settings = new Button("settings");
        settings.getStyleClass().add("text-button");
        settings.setStyle("-fx-font-size: 32px;");

        song = new ComboBox<>();
        song.getStyleClass().add("combo-box");
        song.setId("transparent-combo-box");

        songBox = new HBox();
        songBox.setAlignment(Pos.TOP_CENTER);
        songBox.getChildren().add(song);

        field = new AnchorPane();

        getChildren().addAll(field, songBox, score,  settings);

        AnchorPane.setBottomAnchor(field, 0.0);

        AnchorPane.setTopAnchor(settings, 0.0);
        AnchorPane.setLeftAnchor(settings, 10.0);

        AnchorPane.setTopAnchor(songBox, 10.0);

        AnchorPane.setTopAnchor(score, 10.0);
        AnchorPane.setRightAnchor(score, 15.0);

        setPadding(new Insets(0,20,0,0));
        getStyleClass().add("window");
    }
    
}