package de.hsrm.mi.eibo.presentation.application;

import java.util.HashMap;

import de.hsrm.mi.eibo.business.gamelogic.Block;
import de.hsrm.mi.eibo.business.gamelogic.Game;
import de.hsrm.mi.eibo.business.gamelogic.Player;
import de.hsrm.mi.eibo.business.tone.SongManager;
import de.hsrm.mi.eibo.presentation.scenes.*;
import de.hsrm.mi.eibo.presentation.scenes.buildview.*;
import de.hsrm.mi.eibo.presentation.scenes.startview.*;
import de.hsrm.mi.eibo.presentation.scenes.selectview.*;
import de.hsrm.mi.eibo.presentation.scenes.songview.SongViewController;
import de.hsrm.mi.eibo.presentation.scenes.highscoreview.*;
import de.hsrm.mi.eibo.presentation.scenes.gameview.*;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Klasse zum Starten der JavaFX-Oberfläche
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class MainApplication extends Application {

    private Stage primaryStage;
    private Scene scene;

    private Pane currentScene;
    private HashMap<Scenes, Pane> scenes;

    private Game game;
    private Player player;
    private SongManager songManager;

    private Theme theme;
    private Image startImage;

    @Override
    public void start(Stage primaryStage) {
        try {
            setDesktopLayout();

            game = new Game();
            player = game.getPlayer();
            songManager = game.getSongManager();

            theme = Theme.LIGHT;
            startImage = new Image(getClass().getResource("/images/intro_" + theme.toString() + ".png").toExternalForm());

            ViewController<MainApplication> controller;
            scenes = new HashMap<>();

            controller = new StartViewController(this);
            scenes.put(Scenes.START_VIEW, controller.getRootView());

            currentScene = scenes.get(Scenes.START_VIEW);
            scene = new Scene(currentScene, 1400, 800);
            scene.getStylesheets().add(theme.getUrl());
            
            this.primaryStage = primaryStage;
            primaryStage.setTitle("jump & listen");
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.DECORATED);
            primaryStage.show();

            game.setSceneHeight(scene.getHeight());

            controller = new BuildViewController(this);
            scenes.put(Scenes.BUILD_VIEW, controller.getRootView());

            controller = new SongViewController(this);
            scenes.put(Scenes.SONG_VIEW, controller.getRootView());

            controller = new SelectViewController(this);
            scenes.put(Scenes.SELECT_VIEW, controller.getRootView());

            controller = new GameViewController(this);
            scenes.put(Scenes.GAME_VIEW, controller.getRootView());

            controller = new HighscoreViewController(this);
            scenes.put(Scenes.HIGHCSCORE_VIEW, controller.getRootView());
            
            this.primaryStage.setOnCloseRequest(event -> {
                game.close();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Image getStartImage() {
        return startImage;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public SongManager getSongManager() {
        return songManager;
    }

    public Scene getScene() {
        return scene;
    }

    public Theme getTheme() {
        return theme;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ReadOnlyDoubleProperty getWidth() {
        return primaryStage.widthProperty();
    }

    public ReadOnlyDoubleProperty getHeight() {
        return primaryStage.heightProperty();
    }

    public void switchTheme(Theme theme) {
        this.theme = theme;
        scene.getStylesheets().clear();
        scene.getStylesheets().add(theme.getUrl());
        startImage = new Image(getClass().getResource("/images/intro_" + theme.toString() + ".png").toExternalForm());
    }

    public void setDesktopLayout(){
        Block.configureWidth(100, 250);
        Block.configuteHeight(300, 600);
    }

    /**
     * Ändert die angezeigte Scene
     * @param sceneName Scene, die angezeigt werden soll
     */
    public void switchScene(Scenes sceneName) {
        Pane nextScene;

        if(game.isRunning()){
            game.close();
        }

        if(sceneName.equals(Scenes.GAME_VIEW)) {
            game.restart();
        }

        if(scene.getRoot() instanceof BuildView) {
            songManager.discardAll();
        }

        if(sceneName.equals(Scenes.BUILD_VIEW)) {
            songManager.addLast();
        }

        if(scenes.containsKey(sceneName)){
            nextScene = scenes.get(sceneName);
            scene.setRoot(nextScene);
            currentScene = nextScene;
        }
    }

    /** 
     * Hauptprogramm
     * @param args Komandozeilenparameter
     */
    public static void main(String[] args){
        launch(args);
    }
}