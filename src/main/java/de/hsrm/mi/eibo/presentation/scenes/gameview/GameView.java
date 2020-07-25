package de.hsrm.mi.eibo.presentation.scenes.gameview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class GameView extends AnchorPane {

    Button settings;
    Label score;

    HBox field;

    public GameView() {
        score = new Label();
        score.getStyleClass().add("h3");
        score.setLayoutX(1280);
        score.setLayoutY(20);

        settings = new Button("settings");
        settings.getStyleClass().add("text-button");
        settings.setStyle("-fx-font-size: 32px;");
        settings.setLayoutY(10);

        field = new HBox();
        field.setAlignment(Pos.BASELINE_LEFT);
        field.setSpacing(50);
        AnchorPane.setBottomAnchor(field, 0.0);

        setPadding(new Insets(20, 20, 0, 20));
        getStyleClass().add("window");
        getChildren().addAll(settings, score, field);
    }
    
}