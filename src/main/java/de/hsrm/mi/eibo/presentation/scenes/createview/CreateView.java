package de.hsrm.mi.eibo.presentation.scenes.createview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class CreateView extends AnchorPane {

    Button doneButton, quitButton;
    HBox field;
    
    public CreateView() {
        
        quitButton = new Button("quit");
        quitButton.getStyleClass().add("text-button");

        field = new HBox();
        field.setAlignment(Pos.BOTTOM_LEFT);
        field.setSpacing(50);
        field.setPadding(new Insets(0, 50, 0, 50));
        AnchorPane.setBottomAnchor(field, 0.0);

        getChildren().addAll(quitButton, field);
        getStyleClass().add("window");
    }
    
}