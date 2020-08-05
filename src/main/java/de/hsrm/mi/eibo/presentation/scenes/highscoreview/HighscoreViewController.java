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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controller der HighscoreView
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
    private Button nextLevelButton;
    private Button menuButton;
    private AnchorPane layer;

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
        nextLevelButton = view.levelButton;
        content = view.content;

        menuButton = view.menuButton;
        layer = view.layer;

        view.getChildren().add(menu);

        initialize();
    }

    @Override
    public void initialize() {

        content.prefWidthProperty().bind(application.getScene().widthProperty());
        content.prefHeightProperty().bind(application.getScene().heightProperty());

        layer.prefWidthProperty().bind(application.getScene().widthProperty());
        layer.prefHeightProperty().bind(application.getScene().heightProperty());

        menu.prefHeightProperty().bind(application.getScene().heightProperty());
        menu.prefWidthProperty().bind(application.getScene().widthProperty().divide(5));

        menuButton.addEventHandler(ActionEvent.ACTION, event -> menu.show());
        layer.visibleProperty().bind(menu.visibleProperty());

        application.getGame().endedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            show();
                        }
                    });
                }
            }
        });

    }

    public void show() {

        playerScore.setText(String.valueOf(game.getScore()));
        values = game.getHighScores();

        // Wennn aktueller Highscore der höchste Wert in Liste ist
        if (game.getHighScores().size() > 0 && game.getScore() == values.get(0)) {
            playerText.setText("new personal record!");
        }

        else {
            playerText.setText("you should try again!");
        }

        // Highscore-Anzeige leeren und wieder mit Modulen befüllen
        highscores.getChildren().clear();

        for (int i = 0; i < values.size(); i++) {

            HBox module = new HBox();
            module.getStyleClass().add("module");
            module.setSpacing(160);
            module.setPadding(new Insets(0, 0, 0, 40));
            module.setAlignment(Pos.CENTER_LEFT);

            if (game.getScore() == values.get(i)) {
                module.setId("highscore-module");
            }

            Label rank = new Label(String.valueOf(i + 1));
            rank.getStyleClass().add("h3");
            rank.setId("dark");

            Label score = new Label(String.valueOf(values.get(i)));
            score.getStyleClass().add("normal-text");
            score.setId("dark");

            module.getChildren().addAll(rank, score);
            highscores.getChildren().add(module);
        }

        nextLevelButton.addEventHandler(ActionEvent.ACTION, event -> {
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

                public void run() {
                    game.restart();
                    application.switchScene(Scenes.GAME_VIEW);
                }

            });
        });
    }

}