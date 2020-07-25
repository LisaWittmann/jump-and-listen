package de.hsrm.mi.eibo.presentation.scenes.gameview;

import de.hsrm.mi.eibo.business.gamelogic.*;
import de.hsrm.mi.eibo.presentation.application.*;
import de.hsrm.mi.eibo.presentation.scenes.*;
import de.hsrm.mi.eibo.presentation.uicomponents.game.*;
import de.hsrm.mi.eibo.presentation.uicomponents.settings.*;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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
    private Label score;

    private HBox field;

    protected Color mainColor;

    public GameViewController(MainApplication application) {
        super(application);
        player = application.getPlayer();
        game = application.getGame();

        mainColor = application.getMainColor();

        view = new GameView();
        setRootView(view);

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
            SettingViewController controller = new SettingViewController(application);
            view.getChildren().add(controller.getRootView());
        });

       game.getBlocks().addListener(new ListChangeListener<Block>(){
            @Override
            public void onChanged(Change<? extends Block> c) {
                while(c.next()) {
                    if(c.wasAdded()) {
                        for(Block block : c.getAddedSubList()) {
                            field.getChildren().add(new BlockView(block));
                        } 
                    }
                }
           }
       });
   
   
    }

}