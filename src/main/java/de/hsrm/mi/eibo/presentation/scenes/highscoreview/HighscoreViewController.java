package de.hsrm.mi.eibo.presentation.scenes.highscoreview;

import de.hsrm.mi.eibo.business.gamelogic.Player;
import de.hsrm.mi.eibo.presentation.application.*;
import de.hsrm.mi.eibo.presentation.scenes.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controller der HighscoreView
 * Initiiert angezeigte Elemente anhand der Daten des Players
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class HighscoreViewController extends ViewController<MainApplication> {

    private HighscoreView view;

    private Label playerScore;
    private Label playerText;
    private VBox highscores;

    private Player player;

    public HighscoreViewController(MainApplication application) {
        super(application);
        player = application.getPlayer();

        view = new HighscoreView();
        setRootView(view);

        playerScore = view.playerScore;
        playerText = view.playerText;
        highscores = view.highscores;
        
        initialize();
    }

    @Override
    public void initialize() {
        playerScore.setText(String.valueOf(player.getScore()));

        if(player.getScore() == player.getHighScores().get(0)) playerText.setText("new personal record!");
        else playerText.setText("you should try again!");
        
        for(int currentScore : player.getHighScores()){

            HBox module = new HBox();
            module.getStyleClass().add("module");
            module.setSpacing(160);
            module.setPadding(new Insets(0, 0, 0, 40));
            module.setAlignment(Pos.CENTER_LEFT);

            if(player.getScore() == currentScore) module.setStyle("-fx-background-color: #cef6e3;");

            Label rank = new Label(String.valueOf(player.getHighScores().indexOf(currentScore) + 1));
            rank.getStyleClass().add("h3");
            rank.setStyle("-fx-text-fill: #2e2e2e;");

            Label score = new Label(String.valueOf(currentScore));
            score.getStyleClass().add("normal-text");
            score.setStyle("-fx-text-fill: #2e2e2e");

            module.getChildren().addAll(rank, score);
            highscores.getChildren().add(module);
        }

    }
}