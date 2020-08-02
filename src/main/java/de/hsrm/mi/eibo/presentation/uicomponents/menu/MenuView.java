package de.hsrm.mi.eibo.presentation.uicomponents.menu;

import de.hsrm.mi.eibo.presentation.application.MainApplication;
import de.hsrm.mi.eibo.presentation.scenes.Scenes;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MenuView extends VBox {

    private MainApplication application;

    private HBox header;
    private Button close;

    private Button home;
    private Button create;
    private Button play;
    private Button songs;
    private Button settings;

    public MenuView(Application application) {
        this.application = (MainApplication) application;
        setVisible(false);

        header = new HBox();
        header.setAlignment(Pos.TOP_RIGHT);
        header.setPadding(new Insets(20));

        close = new Button("×");
        close.getStyleClass().add("text-button");
        header.getChildren().add(close);

        home = new Button("home");
        home.getStyleClass().add("text-button");

        create = new Button("create");
        create.getStyleClass().add("text-button");

        play = new Button("play");
        play.getStyleClass().add("text-button");

        songs = new Button("songs");
        songs.getStyleClass().add("text-button");

        settings = new Button("settings");
        settings.getStyleClass().add("text-button");

        setAlignment(Pos.TOP_LEFT);
        setPadding(new Insets(20, 10, 20, 10));
        setSpacing(10);
        getChildren().addAll(header, home, create, play, songs, settings);
        setId("menu");

        initialize();
    }

    public void initialize() {

        close.addEventHandler(ActionEvent.ACTION, event -> {
            close();
        });

        home.addEventHandler(ActionEvent.ACTION, event -> {
            application.switchScene(Scenes.START_VIEW);
            close();
        });

        create.addEventHandler(ActionEvent.ACTION, event -> {
            application.switchScene(Scenes.BUILD_VIEW);
            close();
        });

        play.addEventHandler(ActionEvent.ACTION, event -> {
            application.switchScene(Scenes.SELECT_VIEW);
            close();
        });

        songs.addEventHandler(ActionEvent.ACTION, event -> {
            close();
        });

        settings.addEventHandler(ActionEvent.ACTION, event -> {
            close();
        });
    }

    public void show() {
        this.setVisible(true);
        TranslateTransition animation = new TranslateTransition();
        animation.setNode(this);
        animation.setDuration(Duration.millis(500));
        animation.setFromX(0 - getPrefWidth());
        animation.setToX(0);
        animation.play();
    }

    public void close() {
        TranslateTransition animation = new TranslateTransition();
        animation.setNode(this);
        animation.setDuration(Duration.millis(500));
        animation.setFromX(0);
        animation.setToX(0 - getPrefWidth());
        animation.play();

        animation.setOnFinished(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				setVisible(false);
			}
        });

    }

}