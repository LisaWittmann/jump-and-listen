package de.hsrm.mi.eibo.presentation.scenes.gameview;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GameView extends AnchorPane {

    Button settings;
    Label score;

    public GameView() {
        score = new Label();
        score.getStyleClass().add("h3");

        settings = new Button("settings");
        settings.getStyleClass().add("text-button");
        
        getStyleClass().add("window");
    }
    
}