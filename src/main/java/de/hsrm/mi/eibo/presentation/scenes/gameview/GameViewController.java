package de.hsrm.mi.eibo.presentation.scenes.gameview;

import java.util.List;

import de.hsrm.mi.eibo.business.gamelogic.*;
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
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
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

    private Label score;
    private AnchorPane field;

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
        field = view.field;
        field.setPrefSize(view.getWidth(), view.getHeight());

        mid = application.getScene().getWidth()/2;
        initialize();
    }

    @Override
    public void initialize() {
        score.setText(String.valueOf(game.getScore()));

        score.setOnMouseClicked(event -> application.switchScene(Scenes.HIGHCSCORE_VIEW)); // TODO: später wieder entfernen

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
                if(newValue) initGameSetup(game.getBlockDistanz(), game.getBlocks());
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
                application.switchScene(Scenes.HIGHCSCORE_VIEW);
            }
        });

        addKeyListener();
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
                } else if(event.getCode().equals(KeyCode.SPACE)) {
                    if(game.isRunning() && !game.isPaused()) {
                        game.pause();
                    } else if(game.isRunning() && game.isPaused()) {
                        game.cont();
                    } else if(!game.isRunning()) {
                        game.start();
                    }
                }  else if(event.getCode().equals(KeyCode.S)) {
                    Platform.runLater(new Runnable() {
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
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            game.getPlayer().setOnBoost(true);
                            game.getPlayer().setOnBoost(false);
                        }
                    });
                } else if(event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.W)) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            game.getPlayer().setOnBoost(false);
                            game.getPlayer().setOnJump(true);
                            game.getPlayer().setOnJump(false);
                        }
                    });
                } else if(event.getCode().equals(KeyCode.DOWN)) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            game.getPlayer().setOnDrop(true);
                            game.getPlayer().setOnDrop(false);
                        }
                    });
                } else if(event.getCode().equals(KeyCode.LEFT) || event.getCode().equals(KeyCode.A)){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            game.movePlayerLeft(false);
                        }
                    });
                } else if(event.getCode().equals(KeyCode.RIGHT) || event.getCode().equals(KeyCode.D)) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            game.movePlayerRight(false);
                        }
                    });
                }
            }
        }); 

    }

    public void initGameSetup(double spacing, List<Block> blocks) {
        double x = 0;
        BlockView blockview = null;
        double sceneHeight = application.getScene().getHeight();
        for(Block block : blocks) {
            blockview = new BlockView(block);
            block.setPosY(sceneHeight - block.getHeight());
            block.setPosX(x);
            x += block.getWidth() + spacing;
            field.getChildren().add(blockview);
        }
        view.getChildren().add(player);
        view.getChildren().add(settingView);
        settingView.setVisible(false);
    }

    public void scrollBlocks(double x) {
        field.setLayoutX(-x);
    }

    public double getMid() {
        return mid;
    }

}