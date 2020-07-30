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

        doneButton = new Button("save");
        doneButton.getStyleClass().add("text-button");

        field = new HBox();
        field.setPadding(new Insets(0, 0, 0, 100));
        field.setSpacing(50);
        field.setMinHeight(Block.getMinHeight());
        field.setMaxHeight(Block.getMaxHeight());
        field.setStyle("-fx-background-color: transparent;");
        
        AnchorPane.setBottomAnchor(field, 0.0);

        AnchorPane.setTopAnchor(quitButton, 0.0);
        AnchorPane.setLeftAnchor(quitButton, 0.0);

        AnchorPane.setTopAnchor(doneButton, 0.0);
        AnchorPane.setRightAnchor(doneButton, 0.0);

        getChildren().addAll(quitButton, doneButton, field);
        getStyleClass().add("window");
    }
    
}