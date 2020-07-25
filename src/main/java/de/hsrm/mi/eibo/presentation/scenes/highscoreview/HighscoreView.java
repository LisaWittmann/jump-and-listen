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

    public HighscoreView() {

        playerScore = new Label();
        playerScore.getStyleClass().add("h2");
        
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

        setSpacing(40);
        setPadding(new Insets(100));
        setAlignment(Pos.TOP_CENTER);
        getStyleClass().add("window");

        getChildren().addAll(playerRank, highscores, retryButton);
    }
    
}