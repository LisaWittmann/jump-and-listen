package de.hsrm.mi.eibo.presentation.scenes.gameview;

import java.util.LinkedHashMap;

import de.hsrm.mi.eibo.business.gamelogic.*;
import de.hsrm.mi.eibo.business.tone.Song;
import de.hsrm.mi.eibo.presentation.application.*;
import de.hsrm.mi.eibo.presentation.scenes.*;
import de.hsrm.mi.eibo.presentation.uicomponents.game.*;
import de.hsrm.mi.eibo.presentation.uicomponents.tutorial.TutorialView;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Controller der GameView, handled Kommunikation während des Spiels mit dem
 * Nutzer
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class GameViewController extends ViewController<MainApplication> {

    private GameView view;
    private Game game;

    private Button menuButton;

    private ComboBox<String> song;
    private Label score;
    private AnchorPane field;

    private AnchorPane layer;
    private TutorialView tutorial;

    private PlayerView player;
    private AnimationTimer gameThread;

    public GameViewController(MainApplication application) {
        super(application);
        game = application.getGame();

        view = new GameView();
        setRootView(view);

        player = new PlayerView(game.getPlayer());

        menuButton = view.menuButton;
        score = view.score;
        song = view.song;
        field = view.field;

        layer = view.layer;
        tutorial = view.tutorial;

        view.getChildren().add(menu);
        menu.enableSettings();

        initialize();
    }

    @Override
    public void initialize() {

        field.prefWidthProperty().bind(application.getScene().widthProperty());
        field.prefHeightProperty().bind(application.getScene().heightProperty());

        layer.prefWidthProperty().bind(application.getScene().widthProperty());
        layer.prefHeightProperty().bind(application.getScene().heightProperty());

        menu.prefHeightProperty().bind(application.getScene().heightProperty());
        menu.prefWidthProperty().bind(application.getScene().widthProperty().divide(5));

        placeCenter();

        // GameThread initialisieren
        gameThread = new AnimationTimer() {

            private long lastUpdated = 0;
            private long lastRendered = 0;
            private final int UPS = 40;
            private final int FPS = 50;

            private final int SECONDS2NANO_SECONDS = 1_000 * 1_000_000;
            private final int UPNS_DELTA = SECONDS2NANO_SECONDS / UPS;
            private final int FPNS_DELTA = SECONDS2NANO_SECONDS / FPS;

            @Override
            public void handle(long now) {

                if (lastUpdated + UPNS_DELTA < now) {
                    game.activateMovement();
                    lastUpdated = now;
                }

                if (lastRendered + FPNS_DELTA < now) {

                    if (game.getPlayer().getPosX() > application.getScene().getWidth() / 2) {
                        scrollBlocks(game.getPlayer().getPosX() - application.getScene().getWidth() / 2);
                        player.setLayoutY(game.getPlayer().getPosY());
                    }

                    else {
                        player.setLayoutX(game.getPlayer().getPosX());
                        player.setLayoutY(game.getPlayer().getPosY());
                    }
                }
            }

        };

        score.setText(String.valueOf(game.getScore()));

        menuButton.addEventHandler(ActionEvent.ACTION, event -> {

            layer.visibleProperty().unbind();
            layer.visibleProperty().bind(menu.visibleProperty());
            layer.toFront();

            menu.toFront();
            menu.show();
        });

        // Wenn Spiel initialisiert wurde, Spielfeld aufbauen, wenn Spiel beendet wurde,
        // Spielfeld zurücksetzen
        game.initializedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    initGameSetup();
                } else {
                    field.setLayoutX(0);
                    field.getChildren().clear();
                }
            }

        });

        // Änderungen des Scores abbilden
        game.scoreProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        score.setText(String.valueOf(newValue.intValue()));
                    }
                });
            }

        });

        // Wenn Spiel beendet ist, Animation stoppen und zur HighscoreView wechseln
        game.endedProperty().addListener(new ChangeListener<Boolean>() {

            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    gameThread.stop();
                    application.switchScene(Scenes.HIGHCSCORE_VIEW);
                }
            }

        });

        application.getScene().widthProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                placeCenter();
            }

        });

    }

    public void addKeyListener() {
        application.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {

            public void handle(KeyEvent event) {

                // BoostJump
                if (event.getCode().equals(KeyCode.SHIFT)) {
                    game.getPlayer().setOnBoost(true);
                    game.playerJump();
                }

                // Jump
                else if (event.getCode().equals(KeyCode.W)) {
                    game.getPlayer().setOnJump(true);
                    game.playerJump();
                }

                // Bewegen nach links
                else if (event.getCode().equals(KeyCode.A)) {
                    game.movePlayerLeft(true);
                }

                // Bewegen nach rechts
                else if (event.getCode().equals(KeyCode.D)) {
                    game.movePlayerRight(true);
                }

                // Spiel starten
                else if (event.getCode().equals(KeyCode.S)) {
                    Platform.runLater(new Runnable() {

                        public void run() {
                            game.start();
                            gameThread.start();
                        }

                    });
                }
            }
        });

        // Zum Setzen der Bilder des Players
        application.getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {

            public void handle(KeyEvent event) {

                if (event.getCode().equals(KeyCode.SHIFT)) {
                    game.getPlayer().setOnBoost(true);
                    game.getPlayer().setOnBoost(false);
                }

                else if (event.getCode().equals(KeyCode.W)) {
                    game.getPlayer().setOnBoost(false);
                    game.getPlayer().setOnJump(true);
                    game.getPlayer().setOnJump(false);
                }

                else if (event.getCode().equals(KeyCode.A)) {
                    game.movePlayerLeft(false);
                }

                else if (event.getCode().equals(KeyCode.D)) {
                    game.movePlayerRight(false);
                }
            }
        });

    }

    private void initGameSetup() {
        BlockView blockview = null;

        // Blöcke des Spiels abbilden
        for (Block block : game.getBlocks()) {
            blockview = new BlockView(block);
            field.getChildren().add(blockview);
        }

        // player einfügen
        if (!view.getChildren().contains(player)) {
            view.getChildren().add(player);
        }

        // Drop Down für Songs des selben Levels
        if (!song.getItems().contains(game.getSong().getName())) {
            song.getItems().clear();
            for (Song s : game.getSongManager().getSongsByLevel(game.getLevel())) {
                song.getItems().add(s.getName());
            }
        }

        song.setValue(game.getSong().getName());
        song.setOnAction(event -> game.setSongByName(song.getValue()));

        // Tutorial
        if (game.needsTutorial()) {
            initTutoral();
        }

        addKeyListener();
    }

    private void initTutoral() {

        // Anleitungstexte initialisieren
        LinkedHashMap<String, String> steps = new LinkedHashMap<>();
        steps.put("welcome", "learn to play");
        steps.put("start", "press S to start");
        steps.put("move", "press A to move left and D to move right");
        steps.put("jump", "press W to jump or SHIFT to boostJump");
        steps.put("settings", "customize speed, blocks and theme in settings");

        // Anleitungen setzen
        tutorial.setSteps(steps);

        // Tutorial anzeigen
        layer.visibleProperty().bind(tutorial.visibleProperty());
        layer.toFront();

        tutorial.toFront();
        view.getChildren().add(tutorial);
        tutorial.show();

        game.setTutorial(false);
    }

    public void placeCenter() {
        song.setLayoutX(application.getScene().getWidth() / 2 - song.getPrefWidth() / 2);
        tutorial.setLayoutX(application.getScene().getWidth() / 2 - tutorial.getPrefWidth() / 2);
        tutorial.setLayoutY(application.getScene().getHeight() / 2 - tutorial.getPrefHeight() / 2);
    }

    public void scrollBlocks(double x) {
        field.setLayoutX(-x);
    }

}
