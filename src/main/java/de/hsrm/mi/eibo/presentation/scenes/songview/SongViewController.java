package de.hsrm.mi.eibo.presentation.scenes.songview;

import de.hsrm.mi.eibo.business.tone.Song;
import de.hsrm.mi.eibo.business.tone.SongManager;
import de.hsrm.mi.eibo.presentation.application.MainApplication;
import de.hsrm.mi.eibo.presentation.scenes.ViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * Controller für die SongView
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class SongViewController extends ViewController<MainApplication> {

    private SongView view;
    private SongManager songManager;

    private ListView<Song> songs;

    private AnchorPane layer;
    private Button menuButton;

    public SongViewController(MainApplication application) {
        super(application);
        songManager = application.getSongManager();

        view = new SongView();
        setRootView(view);

        layer = view.layer;
        menuButton = view.menuButton;
        songs = view.songs;

        view.getChildren().add(menu);

        initialize();
    }

    public void placeCenter() {
        songs.setLayoutX(application.getScene().getWidth() / 2 - songs.getPrefWidth() / 2);
        songs.setLayoutY(application.getScene().getHeight() / 2 - songs.getPrefHeight() / 2);
    }

    @Override
    public void initialize() {

        menu.prefHeightProperty().bind(application.getScene().heightProperty());
        menu.prefWidthProperty().bind(application.getScene().widthProperty().divide(5));

        layer.prefHeightProperty().bind(application.getScene().heightProperty());
        layer.prefWidthProperty().bind(application.getScene().widthProperty());

        songs.prefHeightProperty().bind(application.getScene().heightProperty().divide(6).multiply(5));
        songs.prefWidthProperty().bind(application.getScene().widthProperty().divide(2));

        placeCenter();

        menuButton.addEventHandler(ActionEvent.ACTION, event -> menu.show());
        layer.visibleProperty().bind(menu.visibleProperty());

        application.getScene().widthProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                placeCenter();
            }

        });

        songs.setItems(songManager.getSavedSongs());
        songs.setCellFactory(event -> new SongListCell(application));
    }

}