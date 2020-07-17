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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApplication extends Application {

    private Stage primaryStage;
    private Scene scene;

    private Pane currentScene;
    private HashMap<Scenes, Pane> scenes;

    private Game game;
    private Player player;

    @Override
    public void start(Stage primaryStage) {
        try{
            player = new Player();
            game = new Game(player);

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
            scene.getStylesheets().add(getClass().getResource("/stylesheets/application.css").toExternalForm());
            
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

    public void switchScene(Scenes sceneName){
        Pane nextScene;

        if(scenes.containsKey(sceneName)){
            nextScene = scenes.get(sceneName);
            scene.setRoot(nextScene);
            currentScene = nextScene;
        }
    }
    
    public static void main(String[] args){
        launch(args);
    }
}