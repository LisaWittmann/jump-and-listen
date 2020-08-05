package de.hsrm.mi.eibo.presentation.scenes.buildview;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import de.hsrm.mi.eibo.business.tone.*;
import de.hsrm.mi.eibo.business.gamelogic.Block;
import de.hsrm.mi.eibo.presentation.application.MainApplication;
import de.hsrm.mi.eibo.presentation.scenes.Scenes;
import de.hsrm.mi.eibo.presentation.scenes.ViewController;
import de.hsrm.mi.eibo.presentation.uicomponents.game.BlockView;
import de.hsrm.mi.eibo.presentation.uicomponents.tutorial.TutorialView;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class BuildViewController extends ViewController<MainApplication> {

    private BuildView view;
    private SongManager songManager;

    private Button menuButton;
    private Button saveButton;
    private TextField songName;

    private AnchorPane song;

    private Block emptyBlock;

    private AnchorPane layer;
    private TutorialView tutorial;

    public BuildViewController(MainApplication application) {
        super(application);
        songManager = application.getSongManager();

        view = new BuildView();
        setRootView(view);

        menuButton = view.menuButton;
        saveButton = view.saveButton;
        songName = view.songName;
        song = view.song;

        tutorial = view.tutorial;
        layer = view.layer;

        view.getChildren().add(menu);

        initResizeable();
        initialize();
    }

    @Override
    public void initResizeable() {
        song.setPrefHeight(application.getScene().getHeight());
        layer.setPrefSize(application.getWidth().get(), application.getScene().getHeight());
        songName.setLayoutX(application.getWidth().get()/2 - songName.getPrefWidth()/2);
        tutorial.setPrefSize(400, 250);
        tutorial.setLayoutX(application.getWidth().get() / 2 - tutorial.getPrefWidth() / 2);
        tutorial.setLayoutY(application.getScene().getHeight() / 2 - tutorial.getPrefHeight() / 2);
        menu.setPrefSize(application.getWidth().get() / 5, application.getScene().getHeight());
    }

    @Override
    public void initialize() {
        menuButton.addEventHandler(ActionEvent.ACTION, event -> {
            menu.toFront();
            menu.show();
        });

        saveButton.addEventHandler(ActionEvent.ACTION, event -> {
            try {
                application.getGame().setSong(songManager.confirm(songName.getText()));
                application.switchScene(Scenes.GAME_VIEW);
            } catch (NameException e) {
                songName.getStyleClass().add("error");
            }
        });

        application.getWidth().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                initResizeable();
            }
        });

        menu.visibleProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                layer.setVisible(newValue);
            }
        });

        songManager.getInputBlocks().addListener(new ListChangeListener<Block>() {
            @Override
            public void onChanged(Change<? extends Block> changes) {
                while (changes.next()) {
                    if (changes.wasAdded()) {
                        
                        if(song.getChildren().isEmpty()) {
                            addKeyListener();
                        }
                        
                        if(songManager.getEditSong() != null) {
                            songName.setText(songManager.getEditSong().getName());
                        }


                        for (Block block : changes.getAddedSubList()) {
                            if(block.getPosX() > application.getWidth().get()) {
                                scrollSong(-150);
                            }
                            convert(block, block.isInitialized().get());
                        }
                    }

                    else if(changes.wasRemoved()) {

                        if(songManager.getInputBlocks().isEmpty()) {   
                            resetView();
                        }

                        else {
                            List<BlockView> remove = new ArrayList<>();
                        
                            for(Node node : song.getChildren()) {
                                BlockView blockView = (BlockView) node;
                                if(changes.getRemoved().contains(blockView.getBlock())){
                                    remove.add(blockView);
                                }
                            }
                        
                            for(BlockView blockView : remove) {
                                song.getChildren().remove(blockView);
                            }
                        }
                    }
                }
            }
        });

        addKeyListener();
        initToneLines();
        initTutorial();
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
                for (KeyCode code : options) {
                    if (new KeyCodeCombination(code, KeyCodeCombination.SHIFT_DOWN).match(event)) {
                        if (code.name().equals("H"))
                            continue;
                        emptyBlock.setTone(Tone.valueOf(code.name() + "S"));
                        return;
                    }
                }

                if (options.contains(event.getCode())) {
                    emptyBlock.setTone(Tone.valueOf(event.getCode().name()));
                    return;
                }

                else if (event.getCode().equals(KeyCode.L)) {
                    scrollSong(-150);
                } else if (event.getCode().equals(KeyCode.R)) {
                    scrollSong(150);
                } else if (event.getCode().equals(KeyCode.ESCAPE)) {
                    application.switchScene(Scenes.START_VIEW);
                }
            }
        });
    }

    private void initToneLines() {
        for (Tone tone : Tone.values()) {
            if (tone.isHalbton())
                continue;

            double height = Block.getHeightByTone(tone);

            Label name = new Label(tone.name());
            name.getStyleClass().add("normal-text");
            name.setStyle("-fx-text-alignment: left;");
            name.setId("fading");

            double y = application.getScene().getHeight() - height;
            name.setLayoutY(y - 15);
            name.setLayoutX(20);

            Line line = new Line(50, y, 5000, y);
            line.getStyleClass().add("line");

            AnchorPane.setBottomAnchor(line, height);
            AnchorPane.setLeftAnchor(line, 50.0);

            AnchorPane.setBottomAnchor(name, height - 15);

            view.getChildren().addAll(name, line);
        }
    }

    private void initTutorial() {
        LinkedHashMap<String, String> steps = new LinkedHashMap<>();
        steps.put("welcome", "learn how to create own songs");
        steps.put("add tone", "'\u002b' will add a new tone to your song");
        steps.put("drag tone", "change the height of a tone by dragging it up or down");
        steps.put("keyboard", "you can also add songs by typing in the letter");
        steps.put("remove", "click right on a tone to remove it from your song");
        steps.put("scroll", "press R to scroll right and L to scroll left");
        steps.put("name", "the name must only contain lower case letters and spaces");
        steps.put("save", "you need to enter a name for your song to save it");
        tutorial.setSteps(steps);

        layer.setVisible(true);
        layer.toFront();
        view.getChildren().add(tutorial);
        tutorial.show();

        tutorial.visibleProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                layer.setVisible(newValue);
            }
        });
    }

    private void scrollSong(double x) {
        song.setLayoutX(song.getLayoutX() + x);
    }

    public void convert(Block block, boolean initialized) {
        BlockView blockView = new BlockView(block);
        song.getChildren().add(blockView);
        AnchorPane.setBottomAnchor(blockView, 0.0);

        if(!initialized) {
            emptyBlock = block;
            block.isInitialized().addListener(new InvalidationListener(){
				@Override
				public void invalidated(Observable observable) {
					if(block.isInitialized().get()){
                        BlockEditor.makeEditable(blockView, songManager);
                        songManager.addLast();
                    }
				}
            });
        }
        
        else {
            BlockEditor.makeEditable(blockView, songManager);
        }
    }

    public void resetView() {
        song.setLayoutX(0);
        song.getChildren().clear();
        
        if(songName.getStyleClass().contains("error")) {
            songName.getStyleClass().remove("error");
        }
        songName.setText("");
        
    }

}