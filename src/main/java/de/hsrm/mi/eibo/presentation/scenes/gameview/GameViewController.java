package de.hsrm.mi.eibo.presentation.scenes.gameview;

import java.util.HashMap;
import java.util.Map;

import de.hsrm.mi.eibo.business.gamelogic.*;
import de.hsrm.mi.eibo.business.tone.Song;
import de.hsrm.mi.eibo.presentation.application.*;
import de.hsrm.mi.eibo.presentation.scenes.*;
import de.hsrm.mi.eibo.presentation.uicomponents.game.*;
import de.hsrm.mi.eibo.presentation.uicomponents.settings.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
/**
 * Controller der GameView handled Kommunikation während des Spiels mit dem
 * Nutzer
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class GameViewController extends ViewController<MainApplication> {

    private GameView view;
    private Game game;

    private Button settings;
    private Pane settingView;

    private HBox songBox;
    private ComboBox<String> song;
    private Label score;
    private AnchorPane field;

    private Label instruction;

    private double mid;
    private PlayerView player;

    public GameViewController(MainApplication application) {
        super(application);
        game = application.getGame();

        view = new GameView();
        setRootView(view);

        player = new PlayerView(game.getPlayer(), this);

        SettingViewController controller = new SettingViewController(application);
        settingView = controller.getRootView();

        settings = view.settings;
        score = view.score;
        song = view.song;
        songBox = view.songBox;
        field = view.field;

        songBox.setPrefWidth(application.getScene().getWidth());
        field.setPrefSize(view.getWidth(), view.getHeight());

        mid = application.getScene().getWidth()/2;

        instruction = new Label();
        initialize();
    }

    @Override
    public void initialize() {
        score.setText(String.valueOf(game.getScore()));

        settings.addEventHandler(ActionEvent.ACTION, event -> {
            if(!view.getChildren().contains(settingView)){
                view.getChildren().add(settingView);
            }
            else {
                settingView.setVisible(true);
            }
            view.setOnMouseClicked(e -> settingView.setVisible(false));
        });

        game.isInitialized().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) initGameSetup();
                else {
                    field.setLayoutX(0);
                    field.getChildren().clear();
                }
            }
        });

        game.changes.addPropertyChangeListener("score", event -> {
            Platform.runLater(new Runnable() {
                public void run() {
                    score.setText(String.valueOf(game.getScore()));
                }
            });
        });
        game.gameEnded().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) application.switchScene(Scenes.HIGHCSCORE_VIEW);
            }
        });
    }

    public void addKeyListener() {
        KeyCombination boost = new KeyCodeCombination(KeyCode.UP, KeyCodeCombination.CONTROL_DOWN);
        KeyCombination boostAlt = new KeyCodeCombination(KeyCode.W, KeyCodeCombination.CONTROL_DOWN);
        application.getScene().setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if(boost.match(event) || boostAlt.match(event) || event.getCode().equals(KeyCode.CONTROL)){
                    game.getPlayer().setOnBoost(true);
                    game.playerJump();
                } else if(event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.W)) {
                    game.getPlayer().setOnJump(true);
                    game.playerJump();
                } else if(event.getCode().equals(KeyCode.DOWN)) {
                    game.getPlayer().setOnDrop(true);
                    game.playerYCalculation();
                } else if(event.getCode().equals(KeyCode.LEFT) || event.getCode().equals(KeyCode.A)) {
                    game.movePlayerLeft(true);
                } else if(event.getCode().equals(KeyCode.RIGHT) || event.getCode().equals(KeyCode.D)) {
                    game.movePlayerRight(true);
                }  else if(event.getCode().equals(KeyCode.S)) {
						Platform.runLater(new Runnable(){
							@Override
							public void run() {
								game.start();
							}
                        }); 
                    }
                }
        });

        application.getScene().setOnKeyReleased(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event) {
                if(boost.match(event) || boostAlt.match(event) || event.getCode().equals(KeyCode.CONTROL)){
                    game.getPlayer().setOnBoost(true);
                    game.getPlayer().setOnBoost(false);
                } else if(event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.W)) {
                    game.getPlayer().setOnBoost(false);
                    game.getPlayer().setOnJump(true);
                    game.getPlayer().setOnJump(false);
                } else if(event.getCode().equals(KeyCode.DOWN)) {
                    game.getPlayer().setOnDrop(true);
                    game.getPlayer().setOnDrop(false);
                } else if(event.getCode().equals(KeyCode.LEFT) || event.getCode().equals(KeyCode.A)){
                    game.movePlayerLeft(false);
                } else if(event.getCode().equals(KeyCode.RIGHT) || event.getCode().equals(KeyCode.D)) {
                    game.movePlayerRight(false);
                }
            }
        }); 

    }

    public void initGameSetup() {
        BlockView blockview = null;
        for(Block block : game.getBlocks()) {
            blockview = new BlockView(block);
            field.getChildren().add(blockview);
        }
        if(game.needsTutorial()) {
            startTutorial();
        } else addKeyListener();

        if(!view.getChildren().contains(player)){
            view.getChildren().add(player);
        }
        if(!view.getChildren().contains(settingView)){
            view.getChildren().add(settingView);
            settingView.setVisible(false);
        }
        if(!song.getItems().contains(game.getSong().getName())){
            for(Song s : game.songsForLevel()) {
                song.getItems().add(s.getName());
            }
        }
        song.setValue(game.getSong().getName());
        song.setOnAction(event -> game.setSong(song.getValue()));
    }

    public void startTutorial() {
        view.getChildren().add(instruction);
        instruction.setLayoutX(getMid()-110);
        instruction.setLayoutY(200);
        instruction.getStyleClass().add("fading-text");

        instruction.setText("press S to start");
        Map<KeyCode, String> steps = new HashMap<>();
        steps.put(KeyCode.S, "press A to move left");
        steps.put(KeyCode.A, "press D to move right");
        steps.put(KeyCode.D, "press W to jump");
        steps.put(KeyCode.W, "press W and Control to boost jump");
        steps.put(KeyCode.CONTROL, "press Q to quit tutorial");
        application.getScene().setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if(steps.keySet().contains(event.getCode())) {
                    instruction.setText(steps.get(event.getCode()));
                } else if(event.getCode().equals(KeyCode.Q)) {
                    view.getChildren().remove(instruction);
                    addKeyListener();
                }
            }
        });

        //TODO: wäre cool für springen und so weiter kurze Vorschau zu haben @Philipp geht sowas?

    }

    public void scrollBlocks(double x) {
        field.setLayoutX(-x);
    }

    public double getMid() {
        return mid;
    }

}