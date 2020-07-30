package de.hsrm.mi.eibo.presentation.scenes.buildview;

import de.hsrm.mi.eibo.business.gamelogic.Block;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class BuildView extends AnchorPane {

    Button doneButton, quitButton;
    HBox field;
    
    public BuildView() {
        
        quitButton = new Button("quit");
        quitButton.getStyleClass().add("text-button");

        field = new HBox();
        field.setPadding(new Insets(0, 0, 0, 100));
        field.setSpacing(50);
        field.setMinHeight(Block.getMinHeight());
        field.setMaxHeight(Block.getMaxHeight());
        field.setStyle("-fx-background-color: transparent;");
        AnchorPane.setBottomAnchor(field, 0.0);

        getChildren().addAll(quitButton, field);
        getStyleClass().add("window");
    }
    
}