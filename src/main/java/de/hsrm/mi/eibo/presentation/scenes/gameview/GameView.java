package de.hsrm.mi.eibo.presentation.scenes.gameview;

import de.hsrm.mi.eibo.presentation.uicomponents.tutorial.TutorialView;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class GameView extends AnchorPane {

    Button menuButton;
    Label score;
    ComboBox<String> song;
    HBox songBox;
    AnchorPane field;
    
    AnchorPane layer;
    TutorialView tutorial;

    public GameView() {
        score = new Label();
        score.getStyleClass().add("h3");
        score.setStyle("-fx-text-alignment: right;");

        menuButton = new Button("menu");
        menuButton.getStyleClass().add("text-button");
        menuButton.setStyle("-fx-font-size: 32px;");

        song = new ComboBox<>();
        song.setPrefWidth(550);
        song.getStyleClass().add("combo-box");
        song.setId("transparent-combo-box");

        field = new AnchorPane();
        
        layer = new AnchorPane();
        layer.setId("transparent");
        layer.setVisible(false);

        tutorial = new TutorialView();

        getChildren().addAll(field, song, score, menuButton, layer);

        AnchorPane.setBottomAnchor(field, 0.0);

        AnchorPane.setTopAnchor(menuButton, 0.0);
        AnchorPane.setLeftAnchor(menuButton, 10.0);

        AnchorPane.setTopAnchor(song, 10.0);

        AnchorPane.setTopAnchor(score, 10.0);
        AnchorPane.setRightAnchor(score, 15.0);

        setPadding(new Insets(0,20,0,0));
        getStyleClass().add("window");
    }
    
}