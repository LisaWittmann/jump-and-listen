package de.hsrm.mi.eibo.presentation.scenes.buildview;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class BuildView extends AnchorPane {

    Button doneButton, quitButton;
    AnchorPane song;
    
    public BuildView() {
        
        quitButton = new Button("quit");
        quitButton.getStyleClass().add("text-button");

        doneButton = new Button("save");
        doneButton.getStyleClass().add("text-button");

        song = new AnchorPane();

        AnchorPane.setTopAnchor(quitButton, 0.0);
        AnchorPane.setLeftAnchor(quitButton, 0.0);

        AnchorPane.setTopAnchor(doneButton, 0.0);
        AnchorPane.setRightAnchor(doneButton, 0.0);

        AnchorPane.setBottomAnchor(song, 0.0);

        getChildren().addAll(song, quitButton, doneButton);
        getStyleClass().add("window");
    }
    
}