package de.hsrm.mi.eibo.presentation.scenes.gameview;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GameView extends AnchorPane {

    Button settings;
    Label score;

    public GameView() {
        score = new Label();
        score.getStyleClass().add("h3");
        score.setStyle("-fx-text-alignment: right;");
        score.setLayoutX(1320);
        score.setLayoutY(20);

        settings = new Button("settings");
        settings.getStyleClass().add("text-button");
        settings.setStyle("-fx-font-size: 32px;");
        settings.setLayoutY(10);

        setPadding(new Insets(20, 20, 0, 20));
        getStyleClass().add("window");
        getChildren().addAll(score, settings);
    }
    
}