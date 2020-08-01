package de.hsrm.mi.eibo.presentation.scenes.buildview;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import de.hsrm.mi.eibo.business.gamelogic.Block;
import de.hsrm.mi.eibo.business.tone.NameException;
import de.hsrm.mi.eibo.business.tone.SongBuilder;
import de.hsrm.mi.eibo.business.tone.Tone;
import de.hsrm.mi.eibo.presentation.application.MainApplication;
import de.hsrm.mi.eibo.presentation.scenes.Scenes;
import de.hsrm.mi.eibo.presentation.scenes.ViewController;
import de.hsrm.mi.eibo.presentation.uicomponents.game.BlockView;
import de.hsrm.mi.eibo.presentation.uicomponents.tutorial.TutorialView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

public class BuildViewController extends ViewController<MainApplication> {

    private BuildView view;
    private SongBuilder songBuilder;

    private Button quitButton;
    private Button doneButton;
    private AnchorPane song;

    private HBox centerContainer;
    private TextField songName;

    private Block emptyBlock;

    private AnchorPane layer;
    private TutorialView tutorial;

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
        songName = view.songName;
        centerContainer = view.centerContainer;
        tutorial = view.tutorial;
        layer = view.layer;
    
        song.setPrefHeight(application.getScene().getHeight());
        layer.setPrefSize(application.getScene().getWidth(), application.getScene().getHeight());
        centerContainer.setPrefWidth(application.getScene().getWidth());
        tutorial.setPrefSize(400, 250);
        tutorial.setLayoutX(application.getScene().getWidth()/2 - 200);
        tutorial.setLayoutY(application.getScene().getHeight()/2 - 125);

        initialize();
    }

    @Override
    public void initialize() {
        quitButton.addEventHandler(ActionEvent.ACTION, event -> application.switchScene(Scenes.START_VIEW));
        doneButton.addEventHandler(ActionEvent.ACTION, event -> {
            try {
                application.getGame().setSong(songBuilder.confirm(songName.getText()));
                application.switchScene(Scenes.GAME_VIEW);
            } catch (NameException e) {
                songName.setId("error");
            }
        });
        initToneLines();
        addBlock();
        addKeyListener();
        setTutorial();
    }

    public void addBlock() {
        if(scroll) scrollSong(-150);

        Block block = songBuilder.addEmpty(counter, application.getScene().getHeight());
        BlockView blockView = new BlockView(block);
        emptyBlock = block;
        
        song.getChildren().add(blockView);
        AnchorPane.setBottomAnchor(blockView, 0.0);
        counter += block.getWidth() + distance;
        scroll = (counter > application.getScene().getWidth() * 0.6667) ? true : false;

        emptyBlock.isInitialized().addListener(new ChangeListener<Boolean>() {
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
            name.getStyleClass().add("normal-text");
            name.setStyle("-fx-text-alignment: left;");
            name.setId("fading");

            double y = application.getScene().getHeight() - Block.getHeightByTone(tone);
            name.setLayoutY(y-15);
            name.setLayoutX(20);
            
            Line line = new Line(50, y, 5000, y);
            line.getStyleClass().add("line");
            
            view.getChildren().addAll(name, line);
        }
    }
    
    private void addKeyListener() {
        List<KeyCode> options = new ArrayList<>();
        options.add(KeyCode.C);
        options.add(KeyCode.D);
        options.add(KeyCode.E);
        options.add(KeyCode.F);
        options.add(KeyCode.G);
        options.add(KeyCode.A);
        options.add(KeyCode.H);

        application.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
                for(KeyCode code : options) {
                    if(new KeyCodeCombination(code, KeyCodeCombination.SHIFT_DOWN).match(event)) {
                        emptyBlock.setTone(Tone.valueOf(code.name() + "S"));
                        return;
                    }
                }

                if(options.contains(event.getCode())) {
                    emptyBlock.setTone(Tone.valueOf(event.getCode().name()));
                    return;
                }

                else if(event.getCode().equals(KeyCode.L)) { 
                    scrollSong(-150);
                }
                else if(event.getCode().equals(KeyCode.R)) { 
                    scrollSong(150);
                } else if(event.getCode().equals(KeyCode.ESCAPE)) {
                    application.switchScene(Scenes.START_VIEW);
                }
			}
        });
    }

    private void setTutorial() {
        LinkedHashMap<String, String> steps = new LinkedHashMap<>();
        steps.put("welcome", "learn how to create own songs");
        steps.put("add tone", "'+' will add a new tone to your song");
        steps.put("drag tone", "change the height of a tone by dragging it up or down");
        steps.put("keyboard", "you can also add songs by typing in the letter");
        steps.put("scroll", "press R to scroll right and L to scroll left");
        steps.put("save", "you need to enter a name for your song to save it");
        tutorial.setSteps(steps);

        layer.setVisible(true);
        layer.toFront();
        view.getChildren().add(tutorial);
        tutorial.show();

        tutorial.getVisibleProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                layer.setVisible(newValue);
            }
        });
    }

    private void scrollSong(double x) {
        song.setLayoutX(song.getLayoutX()+x);
    }

}