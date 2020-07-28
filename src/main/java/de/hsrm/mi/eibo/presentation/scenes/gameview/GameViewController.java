package de.hsrm.mi.eibo.presentation.scenes.gameview;

import java.util.List;

import de.hsrm.mi.eibo.business.gamelogic.*;
import de.hsrm.mi.eibo.presentation.application.*;
import de.hsrm.mi.eibo.presentation.scenes.*;
import de.hsrm.mi.eibo.presentation.uicomponents.game.*;
import de.hsrm.mi.eibo.presentation.uicomponents.settings.*;
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

    private PlayerView player;

    public GameViewController(MainApplication application) {
        super(application);
        game = application.getGame();

        view = new GameView();
        setRootView(view);

        player = new PlayerView(game.getPlayer());

        SettingViewController controller = new SettingViewController(application);
        settingView = controller.getRootView();
        view.getChildren().add(settingView);
        settingView.setVisible(false);

        settings = view.settings;
        score = view.score;

        initialize();
    }

    @Override
    public void initialize() {
        score.setText(String.valueOf(game.getScore()));

        score.setOnMouseClicked(event -> application.switchScene(Scenes.HIGHCSCORE_VIEW)); // TODO: später wieder entfernen

        settings.addEventHandler(ActionEvent.ACTION, event -> {
            settingView.setVisible(true);
            view.setOnMouseClicked(e -> settingView.setVisible(false));
        });

        game.isInitialized().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) setBlockLayout(50, game.getBlocks());
            }
        });

        addKeyListener();
    }

    public void addKeyListener() {
        KeyCombination boost = new KeyCodeCombination(KeyCode.UP, KeyCodeCombination.CONTROL_DOWN);
        application.getScene().setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event) {
                if(boost.match(event)){
                    game.getPlayer().setOnBoost(true);
                    game.playerJump();
                } else if(event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.W)) {
                    game.getPlayer().setOnJump(true);
                    game.playerJump();
                } else if(event.getCode().equals(KeyCode.DOWN)) {
                    game.getPlayer().setOnDrop(true);
                    game.playerDrop();
                } else if(event.getCode().equals(KeyCode.LEFT) || event.getCode().equals(KeyCode.A)) {
                    game.movePlayerLeft(true);
                } else if(event.getCode().equals(KeyCode.RIGHT) || event.getCode().equals(KeyCode.D)) {
                    game.movePlayerRight(true);
                } else if(event.getCode().equals(KeyCode.SPACE)) {
                    if(game.isRunning() && !game.isPaused()) {
                        game.pause();
                        player.stopAnimation();
                    } else if(game.isRunning() && game.isPaused()) {
                        game.cont();
                        player.startAnimation();
                    } else if(!game.isRunning()) {
                        game.start();
                        player.startAnimation();
                    }
                }  else if(event.getCode().equals(KeyCode.S)) {
                    game.start();
                }
            }
        });

        application.getScene().setOnKeyReleased(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event) {
                if(boost.match(event)){
                    game.getPlayer().setOnBoost(true);
                    game.getPlayer().setOnBoost(false);
                } else if(event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.W)) {
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

    public void setBlockLayout(double spacing, List<Block> blocks) {
        double x = 0;
        BlockView view = null;
        double sceneHeight = application.getScene().getHeight();
        for(Block block : blocks) {
            view = new BlockView(block);
            block.setPosY(sceneHeight - block.getHeight());
            block.setPosX(x);
            x += block.getWidth() + spacing;
            this.view.getChildren().add(view);
            System.out.println(block.getPosX() + block.getPosY());
        }
        this.view.getChildren().add(player);
    }

}