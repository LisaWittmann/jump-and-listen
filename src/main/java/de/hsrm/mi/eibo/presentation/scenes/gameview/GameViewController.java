package de.hsrm.mi.eibo.presentation.scenes.gameview;

import de.hsrm.mi.eibo.business.gamelogic.*;
import de.hsrm.mi.eibo.presentation.application.*;
import de.hsrm.mi.eibo.presentation.scenes.*;
import de.hsrm.mi.eibo.presentation.uicomponents.game.*;
import de.hsrm.mi.eibo.presentation.uicomponents.settings.*;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Controller der GameView
 * handled Kommunikation während des Spiels mit dem Nutzer
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class GameViewController extends ViewController<MainApplication> {

    private GameView view;
    private Player player;
    private Game game;

    private Button settings;
    private Pane settingView;

    private Label score;

    private HBox field;
    private PlayerView playerView;

    protected Color mainColor;

    public GameViewController(MainApplication application) {
        super(application);
        player = application.getPlayer();
        game = application.getGame();

        mainColor = application.getMainColor();

        view = new GameView();
        setRootView(view);

        playerView = new PlayerView(player);
        view.getChildren().add(playerView);

        SettingViewController controller = new SettingViewController(application);
        settingView = controller.getRootView();
        view.getChildren().add(settingView);
        settingView.setVisible(false);

        settings = view.settings;
        score = view.score;
        field = view.field;

        initialize();
    }

    @Override
    public void initialize() {
        score.setText(String.valueOf(player.getScore()));
        score.setTextFill(mainColor);

        //TODO: später wieder entfernen
        score.setOnMouseClicked(event -> application.switchScene(Scenes.HIGHCSCORE_VIEW));

        settings.addEventHandler(ActionEvent.ACTION, event -> {
            settingView.setVisible(true);
            view.setOnMouseClicked(e -> settingView.setVisible(false));
        });

       game.getBlocks().addListener(new ListChangeListener<Block>() {
            @Override
            public void onChanged(Change<? extends Block> c) {
                while(c.next()) {
                    if(c.wasAdded()) {
                        for(Block block : c.getAddedSubList()) {
                            field.getChildren().add(new BlockView(block));
                            addKeyListener();
                        } 
                    }
                }
           }
       });
    }

    public void addKeyListener() {
        KeyCombination boost = new KeyCodeCombination(KeyCode.UP, KeyCodeCombination.CONTROL_DOWN);
        application.getScene().setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event) {
                if(boost.match(event)){
                    player.setBoost(true);
                } else if(event.getCode().equals(KeyCode.UP)) {
                    player.setJump(true);
                } else if(event.getCode().equals(KeyCode.DOWN)) {
                    player.setDrop(true);
                } else if(event.getCode().equals(KeyCode.SPACE)) {
                    if(game.isRunning() && !game.isPaused()) {
                        game.pause();
                        playerView.stopAnimation();
                    } else if(game.isRunning() && game.isPaused()) {
                        game.cont();
                        playerView.startAnimation();
                    } else if(!game.isRunning()) {
                        game.start();
                        playerView.startAnimation();
                    }
                }
            }
            
        });

        application.getScene().setOnKeyReleased(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event) {
                if(boost.match(event)){
                    player.setBoost(true);
                    player.setBoost(false);
                } else if(event.getCode().equals(KeyCode.UP)) {
                    player.setJump(true);
                    player.setJump(false);
                } else if(event.getCode().equals(KeyCode.DOWN)) {
                    player.setDrop(true);
                    player.setDrop(false);
                }
            }
        }); 

    }

}