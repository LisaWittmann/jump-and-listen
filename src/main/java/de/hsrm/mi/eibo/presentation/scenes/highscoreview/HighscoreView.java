package de.hsrm.mi.eibo.presentation.scenes.highscoreview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Anzeige des Spielstands und der persönlichen Highscores
 * Wird nach Abschluss eines Spiels angezeigt
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class HighscoreView extends VBox {

    VBox playerRank;
    Label playerScore;
    Label playerText;

    VBox highscores;

    Button retryButton;
    Button levelButton;

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
        levelButton.getStyleClass().add("big-text-button");

        VBox buttonBox = new VBox();
        buttonBox.setSpacing(-5);
        buttonBox.setAlignment(Pos.TOP_CENTER);
        buttonBox.getChildren().addAll(levelButton, retryButton);

        setSpacing(40);
        setPadding(new Insets(100));
        setAlignment(Pos.TOP_CENTER);
        getStyleClass().add("window");

        getChildren().addAll(playerRank, highscores, buttonBox);
    }
    
}