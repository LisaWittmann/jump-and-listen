package de.hsrm.mi.eibo.presentation.application;

import java.util.HashMap;

import de.hsrm.mi.eibo.business.gamelogic.Game;
import de.hsrm.mi.eibo.business.gamelogic.Player;
import de.hsrm.mi.eibo.presentation.scenes.*;
import de.hsrm.mi.eibo.presentation.scenes.startview.*;
import de.hsrm.mi.eibo.presentation.scenes.selectview.*;
import de.hsrm.mi.eibo.presentation.scenes.highscoreview.*;
import de.hsrm.mi.eibo.presentation.scenes.gameview.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

    private ColorPicker colorPicker;

    @Override
    public void start(Stage primaryStage) {
        try{
            player = new Player();
            game = new Game(player);

            colorPicker = new ColorPicker();
            colorPicker.setValue(Color.web("#cef6e3"));

            ViewController<MainApplication> controller;
            scenes = new HashMap<>();

            controller = new StartViewController(this);
            scenes.put(Scenes.START_VIEW, controller.getRootView());

            controller = new SelectViewController(this);
            scenes.put(Scenes.SELECT_VIEW, controller.getRootView());

            controller = new GameViewController(this);
            scenes.put(Scenes.GAME_VIEW, controller.getRootView());

            controller = new HighscoreViewController(this);
            scenes.put(Scenes.HIGHCSCORE_VIEW, controller.getRootView());

            currentScene = scenes.get(Scenes.START_VIEW);
            scene = new Scene(currentScene, 1400, 800);
            scene.getStylesheets().add(getClass().getResource("/stylesheets/darkTheme.css").toExternalForm());
            
            this.primaryStage = primaryStage;
            this.primaryStage.setTitle("jump & listen");
            this.primaryStage.setScene(scene);
            this.primaryStage.show();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public Game getGame(){
        return game;
    }

    public Player getPlayer(){
        return player;
    }

    public ColorPicker getColorPicker() {
        return colorPicker;
    }

    public Color getMainColor() {
        return colorPicker.getValue();
    }

    public Scene getScene() {
        return scene;
    }

    /**
     * Ändert die angezeigte Scene
     * @param sceneName Scene, die angezeigt werden soll
     */
    public void switchScene(Scenes sceneName){
        Pane nextScene;

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