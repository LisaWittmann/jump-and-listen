package de.hsrm.mi.eibo.presentation.scenes.buildview;

import de.hsrm.mi.eibo.presentation.uicomponents.tutorial.TutorialView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class BuildView extends AnchorPane {

    Button doneButton, quitButton;
    TextField songName;
    HBox centerContainer;
    AnchorPane song;

    AnchorPane layer;
    TutorialView tutorial;
    
    public BuildView() {
        
        quitButton = new Button("quit");
        quitButton.getStyleClass().add("text-button");

        doneButton = new Button("save");
        doneButton.getStyleClass().add("text-button");

        song = new AnchorPane();
        songName = new TextField();
        songName.setPromptText("name your song");
        centerContainer = new HBox();
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setPadding(new Insets(20));
        centerContainer.getChildren().add(songName);

        layer = new AnchorPane();
        layer.setId("transparent");
        layer.setVisible(false);
        tutorial = new TutorialView();

        AnchorPane.setTopAnchor(quitButton, 0.0);
        AnchorPane.setLeftAnchor(quitButton, 0.0);

        AnchorPane.setTopAnchor(doneButton, 0.0);
        AnchorPane.setRightAnchor(doneButton, 0.0);

        AnchorPane.setBottomAnchor(song, 0.0);
        
        AnchorPane.setTopAnchor(centerContainer, 0.0);

        getChildren().addAll(song, centerContainer, quitButton, doneButton, layer);
        getStyleClass().add("window");
    }
    
}