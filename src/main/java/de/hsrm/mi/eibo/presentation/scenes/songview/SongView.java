package de.hsrm.mi.eibo.presentation.scenes.songview;

import de.hsrm.mi.eibo.business.tone.Song;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * Anzeige aller vorhandenen Songs
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class SongView extends AnchorPane {

    AnchorPane layer;
    Button menuButton;

    ListView<Song> songs;

    public SongView() {

        songs = new ListView<Song>();

        menuButton = new Button();
        menuButton.setId("menu-button");

        layer = new AnchorPane();
        layer.setId("transparent");
        layer.setVisible(false);

        AnchorPane.setTopAnchor(menuButton, 10.0);
        AnchorPane.setLeftAnchor(menuButton, 10.0);

        getChildren().addAll(songs, menuButton, layer);
        getStyleClass().add("window");
    }

}