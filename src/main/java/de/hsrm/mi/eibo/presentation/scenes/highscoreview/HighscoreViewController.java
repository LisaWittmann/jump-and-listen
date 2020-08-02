package de.hsrm.mi.eibo.presentation.scenes.highscoreview;

import java.util.List;

import de.hsrm.mi.eibo.business.gamelogic.Game;
import de.hsrm.mi.eibo.presentation.application.*;
import de.hsrm.mi.eibo.presentation.scenes.*;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controller der HighscoreView Initiiert angezeigte Elemente anhand der Daten
 * des Players
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class HighscoreViewController extends ViewController<MainApplication> {

    private HighscoreView view;

    private Label playerScore;
    private Label playerText;
    private VBox highscores;
    private VBox content;
    private Button retryButton;
    private Button levelButton;
    private Button menuButton;

    private Game game;
    private List<Integer> values;

    public HighscoreViewController(MainApplication application) {
        super(application);
        game = application.getGame();

        view = new HighscoreView();
        setRootView(view);

        playerScore = view.playerScore;
        playerText = view.playerText;
        highscores = view.highscores;
        retryButton = view.retryButton;
        levelButton = view.levelButton;
        content = view.content;
        menuButton = view.homeButton;

        initResizeableElements();

        application.getWidth().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                initResizeableElements();
            }
        });

        application.getGame().gameEnded().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            initialize();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void initialize() {
        playerScore.setText(String.valueOf(game.getScore()));
        values = game.getHighScores();
        if (game.getHighScores().size() > 0 && game.getScore() == values.get(0)) {
            playerText.setText("new personal record!");
        } else {
            playerText.setText("you should try again!");
        }

        highscores.getChildren().clear();
        for (int i = 0; i < values.size(); i++) {
            HBox module = new HBox();
            module.getStyleClass().add("module");
            module.setSpacing(160);
            module.setPadding(new Insets(0, 0, 0, 40));
            module.setAlignment(Pos.CENTER_LEFT);

            if (game.getScore() == values.get(i))
                module.setId("highscore-module");

            Label rank = new Label(String.valueOf(i + 1));
            rank.getStyleClass().add("h3");
            rank.setId("dark");

            Label score = new Label(String.valueOf(values.get(i)));
            score.getStyleClass().add("normal-text");
            score.setId("dark");

            module.getChildren().addAll(rank, score);
            highscores.getChildren().add(module);
        }

        levelButton.addEventHandler(ActionEvent.ACTION, event -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    game.setLevel(game.getLevel().getNextLevel());
                    game.restart();
                    application.switchScene(Scenes.GAME_VIEW);
                }

            });
        });

        retryButton.addEventHandler(ActionEvent.ACTION, event -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    game.restart();
                    application.switchScene(Scenes.GAME_VIEW);
                }
            });
        });

        menuButton.addEventHandler(ActionEvent.ACTION, event -> {
            application.switchScene(Scenes.START_VIEW);
        });

    }

    public void initResizeableElements() {
        content.setMinSize(application.getWidth().get(), application.getScene().getHeight());
        menu.setPrefSize(application.getWidth().get()/5, application.getScene().getHeight());
    }

}