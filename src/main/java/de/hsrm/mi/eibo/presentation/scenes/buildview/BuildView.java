package de.hsrm.mi.eibo.presentation.scenes.buildview;

import de.hsrm.mi.eibo.presentation.uicomponents.tutorial.TutorialView;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class BuildView extends AnchorPane {

    Button saveButton, menuButton;
    TextField songName;

    AnchorPane song;

    AnchorPane layer;
    TutorialView tutorial;
    
    public BuildView() {
        
        menuButton = new Button("");        
        menuButton.setId("menu-button");

        saveButton = new Button("save");
        saveButton.getStyleClass().add("text-button");

        song = new AnchorPane();
        song.setLayoutX(0);
        
        songName = new TextField();
        songName.setPrefWidth(300);
        songName.setPromptText("name your song");

        layer = new AnchorPane();
        layer.setId("transparent");
        layer.setVisible(false);
        tutorial = new TutorialView();

        AnchorPane.setTopAnchor(menuButton, 10.0);
        AnchorPane.setLeftAnchor(menuButton, 10.0);

        AnchorPane.setTopAnchor(saveButton, 0.0);
        AnchorPane.setRightAnchor(saveButton, 0.0);

        AnchorPane.setBottomAnchor(song, 0.0);
        
        AnchorPane.setTopAnchor(songName, 10.0);

        getChildren().addAll(song, songName, menuButton, saveButton, layer);
        getStyleClass().add("window");
    }
    
}