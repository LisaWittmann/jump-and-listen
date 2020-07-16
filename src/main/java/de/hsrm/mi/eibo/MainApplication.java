package de.hsrm.mi.eibo;

import java.util.HashMap;

import de.hsrm.mi.eibo.presentation.*;
import de.hsrm.mi.eibo.presentation.scenes.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApplication extends Application {

    private Stage primaryStage;
    private Scene scene;

    private Pane currentScene;
    private HashMap<Scenes, Pane> scenes;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            ViewController<MainApplication> controller;
            scenes = new HashMap<>();

            controller = new StartViewController(this);
            scenes.put(Scenes.START_VIEW, controller.getRootView());

            controller = new LevelViewController(this);
            scenes.put(Scenes.LEVEL_VIEW, controller.getRootView());

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