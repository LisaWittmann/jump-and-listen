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

        initResizeable();
        initialize();
    }

    @Override
    public void initResizeable() {
        menu.setPrefSize(application.getWidth().get() / 5, application.getScene().getHeight());
        layer.setPrefSize(application.getWidth().get(), application.getScene().getHeight());
        songs.setPrefSize(application.getWidth().get()/2, (application.getScene().getHeight()/6)*5);
        songs.setLayoutX(application.getWidth().get()/2 - songs.getPrefWidth()/2);
        songs.setLayoutY(application.getScene().getHeight()/2 - songs.getPrefHeight()/2);
    }

    @Override
    public void initialize() {
        menuButton.addEventHandler(ActionEvent.ACTION, event -> {
            menu.show();
        });

        menu.visibleProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                layer.setVisible(newValue);
            }  
        });

        application.getWidth().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                initResizeable();   
            }
        });

        songs.setItems(songManager.getSavedSongs());
        songs.setCellFactory(event -> new SongListCell(application));
    }
    
}