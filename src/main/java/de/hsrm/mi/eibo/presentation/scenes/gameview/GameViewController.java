package de.hsrm.mi.eibo.presentation.scenes.gameview;

import de.hsrm.mi.eibo.business.gamelogic.*;
import de.hsrm.mi.eibo.presentation.application.*;
import de.hsrm.mi.eibo.presentation.scenes.*;
import de.hsrm.mi.eibo.presentation.uicomponents.game.*;
import de.hsrm.mi.eibo.presentation.uicomponents.settings.*;

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
    private Game game;

    private Button settings;
    private Pane settingView;

    private Label score;

    private HBox field;
    private PlayerView player;

    protected Color mainColor;

    public GameViewController(MainApplication application) {
        super(application);
        game = application.getGame();

        view = new GameView();
        setRootView(view);

        player = new PlayerView(game.getPlayer());
        view.getChildren().add(player);

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
        score.setText(String.valueOf(game.getScore()));
        score.setTextFill(mainColor);

        score.setOnMouseClicked(event -> application.switchScene(Scenes.HIGHCSCORE_VIEW)); //TODO: später wieder entfernen

        settings.addEventHandler(ActionEvent.ACTION, event -> {
            settingView.setVisible(true);
            view.setOnMouseClicked(e -> settingView.setVisible(false));
        });

        field.getChildren().addAll(new BlockView(), new BlockView());

        addKeyListener();
    }

    public void addKeyListener() {
        KeyCombination boost = new KeyCodeCombination(KeyCode.UP, KeyCodeCombination.CONTROL_DOWN);
        application.getScene().setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event) {
                if(boost.match(event)){
                    game.getPlayer().setBoost(true);
                } else if(event.getCode().equals(KeyCode.UP)) {
                    game.getPlayer().setJump(true);
                } else if(event.getCode().equals(KeyCode.DOWN)) {
                    game.getPlayer().setDrop(true);
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
                }
            }
            
        });

        application.getScene().setOnKeyReleased(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event) {
                if(boost.match(event)){
                    game.getPlayer().setBoost(true);
                    game.getPlayer().setBoost(false);
                } else if(event.getCode().equals(KeyCode.UP)) {
                    game.getPlayer().setJump(true);
                    game.getPlayer().setJump(false);
                } else if(event.getCode().equals(KeyCode.DOWN)) {
                    game.getPlayer().setDrop(true);
                    game.getPlayer().setDrop(false);
                }
            }
        }); 

    }

}