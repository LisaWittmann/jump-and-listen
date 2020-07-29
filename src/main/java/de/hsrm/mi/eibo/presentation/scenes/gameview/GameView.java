package de.hsrm.mi.eibo.presentation.scenes.gameview;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GameView extends AnchorPane {

    Button settings;
    Label score;
    AnchorPane field;

    public GameView() {
        score = new Label();
        score.getStyleClass().add("h3");
        score.setStyle("-fx-text-alignment: right;");

        settings = new Button("settings");
        settings.getStyleClass().add("text-button");
        settings.setStyle("-fx-font-size: 32px;");

        field = new AnchorPane();

        getChildren().addAll(field, score, settings);

        AnchorPane.setBottomAnchor(field, 0.0);

        AnchorPane.setTopAnchor(settings, 0.0);
        AnchorPane.setLeftAnchor(settings, 10.0);

        AnchorPane.setTopAnchor(score, 10.0);
        AnchorPane.setRightAnchor(score, 15.0);

        setPadding(new Insets(0,20,0,0));
        getStyleClass().add("window");
    }
    
}