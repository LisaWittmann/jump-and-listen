package de.hsrm.mi.eibo.presentation.scenes.highscoreview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Anzeige des Spielstands und der Highscores für den Song, Wird nach Abschluss
 * eines Spiels angezeigt
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class HighscoreView extends AnchorPane {

    VBox content;
    VBox playerRank;
    Label playerScore;
    Label playerText;

    VBox highscores;

    Button retryButton;
    Button levelButton;
    Button menuButton;

    AnchorPane layer;

    public HighscoreView() {

        playerScore = new Label();
        playerScore.getStyleClass().add("h2");
        playerScore.setStyle("-fx-font-size: 65px;");

        playerText = new Label();
        playerText.getStyleClass().add("h3");

        playerRank = new VBox();
        playerRank.setSpacing(20);
        playerRank.setAlignment(Pos.CENTER);
        playerRank.getStyleClass().add("window");
        playerRank.getChildren().addAll(playerScore, playerText);

        highscores = new VBox();
        highscores.setSpacing(20);
        highscores.setAlignment(Pos.TOP_CENTER);
        highscores.getStyleClass().add("window");

        retryButton = new Button("try again");
        retryButton.getStyleClass().add("text-button");

        levelButton = new Button("next level");
        levelButton.getStyleClass().add("text-button");
        levelButton.setId("big-button");

        menuButton = new Button("");
        menuButton.setId("menu-button");

        AnchorPane.setTopAnchor(menuButton, 10.0);
        AnchorPane.setLeftAnchor(menuButton, 10.0);

        VBox buttonBox = new VBox();
        buttonBox.setSpacing(-5);
        buttonBox.setAlignment(Pos.TOP_CENTER);
        buttonBox.getChildren().addAll(levelButton, retryButton);

        content = new VBox();
        content.setSpacing(20);
        content.setPadding(new Insets(40, 20, 40, 20));
        content.setAlignment(Pos.TOP_CENTER);
        content.getChildren().addAll(playerRank, highscores, buttonBox);

        layer = new AnchorPane();
        layer.setId("transparent");
        layer.setVisible(false);

        getStyleClass().add("window");
        getChildren().addAll(content, menuButton, layer);
    }

}