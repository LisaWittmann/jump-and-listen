package de.hsrm.mi.eibo.presentation.scenes.createview;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class CreateView extends AnchorPane {

    Button doneButton, quitButton;
    
    public CreateView() {
        
        quitButton = new Button("quit");
        quitButton.getStyleClass().add("text-button");

        getChildren().add(quitButton);
        getStyleClass().add("window");
    }
    
}