package de.hsrm.mi.eibo.presentation.scenes.buildview;

import de.hsrm.mi.eibo.business.gamelogic.Block;
import de.hsrm.mi.eibo.business.tone.SongBuilder;
import de.hsrm.mi.eibo.business.tone.Tone;
import de.hsrm.mi.eibo.presentation.application.MainApplication;
import de.hsrm.mi.eibo.presentation.scenes.Scenes;
import de.hsrm.mi.eibo.presentation.scenes.ViewController;
import de.hsrm.mi.eibo.presentation.uicomponents.game.BlockView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class BuildViewController extends ViewController<MainApplication> {

    private BuildView view;
    private SongBuilder songBuilder;

    private Button quitButton;
    private Button doneButton;
    private AnchorPane song;

    private double counter = 100;
    private double distance = 50;

    private boolean scroll;

    public BuildViewController(MainApplication application) {
        super(application);
        songBuilder = new SongBuilder();
        scroll = false;

        view = new BuildView();
        setRootView(view);

        quitButton = view.quitButton;
        doneButton = view.doneButton;
        song = view.song;

        song.setPrefHeight(application.getScene().getHeight());

        initialize();
    }

    @Override
    public void initialize() {
        quitButton.addEventHandler(ActionEvent.ACTION, e -> application.switchScene(Scenes.START_VIEW));
        doneButton.addEventHandler(ActionEvent.ACTION, e -> {
            application.getGame().setSong(songBuilder.confirm());
            application.switchScene(Scenes.GAME_VIEW);
        });
        initToneLines();
        addBlock();

    }

    public void addBlock() {
        if(scroll) scrollSong(150);

        Block block = songBuilder.addEmpty(counter, application.getScene().getHeight());
        BlockView blockView = new BlockView(block);
        
        song.getChildren().add(blockView);
        AnchorPane.setBottomAnchor(blockView, 0.0);
        counter += block.getWidth() + distance;
        scroll = (counter > application.getScene().getWidth() * 0.6667) ? true : false;

        block.isInitialized().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    BlockResizer.makeResizable(blockView, application.getScene());
                    songBuilder.add(block);
                    addBlock();
                }
            }
        });
    }

    private void initToneLines() {
        for(Tone tone : Tone.values()) {
            if(tone.isHalbton()) {
                continue;
            }
            Label name = new Label(tone.name());
            name.getStyleClass().add("fading-text");
            name.setStyle("-fx-text-alignment: left;");

            double y = application.getScene().getHeight() - Block.getHeightByTone(tone);
            name.setLayoutY(y-15);
            name.setLayoutX(20);
            
            Line line = new Line(50, y, 5000, y);
            line.getStyleClass().add("line");
            
            view.getChildren().addAll(name, line);
        }
    }

    public void scrollSong(double x) {
        song.setLayoutX(song.getLayoutX()-x);
    }
    
}