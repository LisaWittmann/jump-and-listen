package de.hsrm.mi.eibo.presentation.scenes.songview;

import de.hsrm.mi.eibo.business.tone.Song;
import de.hsrm.mi.eibo.presentation.application.MainApplication;
import de.hsrm.mi.eibo.presentation.scenes.Scenes;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SongListCell extends ListCell<Song> {

    private MainApplication application;

    private VBox content;
    private Label name;

    private HBox buttonBox;
    private Button edit;
    private Button remove;

    public SongListCell(MainApplication application) {
        this.application = application;
        
        content = new VBox();
        content.setAlignment(Pos.TOP_LEFT);
        content.setPadding(new Insets(10));

        name = new Label();
        name.getStyleClass().add("h3");

        edit = new Button("edit");
        edit.setVisible(false);
        edit.getStyleClass().add("text-button");

        remove = new Button("remove");
        remove.setVisible(false);
        remove.getStyleClass().add("text-button");

        buttonBox = new HBox();
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.TOP_LEFT);
        buttonBox.getChildren().addAll(remove, edit);

        content.getChildren().addAll(name, buttonBox);

        setGraphic(content);
    }

    @Override
    protected void updateItem(Song song, boolean empty) {
        super.updateItem(song, empty);

        if(song != null) {
            name.setText(song.getName());
            if(song.isEditable()) {
                edit.setVisible(true);
                edit.addEventHandler(ActionEvent.ACTION, event -> {
                    application.getSongManager().editSong(song);
                    application.switchScene(Scenes.BUILD_VIEW);
                });

                remove.setVisible(true);
                remove.addEventHandler(ActionEvent.ACTION, event -> {
                    application.getSongManager().removeSong(song);
                });
            }
            setGraphic(content);
        }
        else {
            setGraphic(null);
        }
    }

    public void setApplication(MainApplication application) {
        this.application = application;
    }
    
}