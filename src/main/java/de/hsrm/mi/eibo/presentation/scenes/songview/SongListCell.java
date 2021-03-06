package de.hsrm.mi.eibo.presentation.scenes.songview;

import de.hsrm.mi.eibo.business.tone.Song;
import de.hsrm.mi.eibo.presentation.application.MainApplication;
import de.hsrm.mi.eibo.presentation.scenes.Scenes;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * Darstellung eines Songs als Listenzeile Kapselung der Song-Businessdaten
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class SongListCell extends ListCell<Song> {

    private MainApplication application;

    private AnchorPane content;
    private Label name;

    private HBox buttonBox;
    private Button edit;
    private Button remove;

    public SongListCell(MainApplication application) {
        this.application = application;

        content = new AnchorPane();
        content.setPadding(new Insets(10));

        name = new Label();
        name.getStyleClass().add("normal-text");

        edit = new Button();
        edit.setVisible(false);
        edit.getStyleClass().add("icon-button");
        edit.setId("edit-button");

        remove = new Button();
        remove.setVisible(false);
        remove.getStyleClass().add("icon-button");
        remove.setId("remove-button");

        buttonBox = new HBox();
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.TOP_LEFT);
        buttonBox.getChildren().addAll(remove, edit);

        AnchorPane.setTopAnchor(name, 0.0);
        AnchorPane.setLeftAnchor(name, 0.0);

        AnchorPane.setTopAnchor(buttonBox, 0.0);
        AnchorPane.setRightAnchor(buttonBox, 10.0);

        content.getChildren().addAll(name, buttonBox);

        setGraphic(content);
    }

    @Override
    protected void updateItem(Song song, boolean empty) {
        super.updateItem(song, empty);

        if (song != null) {

            // Name des Songs
            name.setText(song.getName());

            if (song.isEditable()) {

                // Wenn Song editierbar ist, Funktionen zur Datenmanipulation anbieten
                edit.setVisible(true);
                remove.setVisible(true);

                // Wechselt zur BuildView mit dem ausgew??hlten Song und erm??glicht dort
                // editieren
                edit.addEventHandler(ActionEvent.ACTION, event -> {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            application.getSongManager().editSong(song);
                            application.switchScene(Scenes.BUILD_VIEW);

                        }
                    });
                });

                // L??scht ausgew??hlten Song aus der Datei
                remove.addEventHandler(ActionEvent.ACTION, event -> {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            application.getSongManager().removeSong(song);
                        }
                    });
                });
            }

            else {
                edit.setVisible(false);
                remove.setVisible(false);
            }

            setGraphic(content);

            // Mit Doppelklick auf Zelle wird ein neues Spiel mit dem Song gestartet
            setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                application.getGame().setSong(song);
                                application.switchScene(Scenes.GAME_VIEW);
                            }
                        });
                    }
                }
            });

        }

        else {
            setText(null);
            setGraphic(null);
        }
    }

    public void setApplication(MainApplication application) {
        this.application = application;
    }

}