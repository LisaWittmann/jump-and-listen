package de.hsrm.mi.eibo.presentation.scenes.gameview;

import de.hsrm.mi.eibo.business.gamelogic.*;
import de.hsrm.mi.eibo.presentation.application.*;
import de.hsrm.mi.eibo.presentation.scenes.*;
import de.hsrm.mi.eibo.presentation.uicomponents.settings.SettingViewController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GameViewController extends ViewController<MainApplication> {

    private GameView view;
    private Game game;
    private Player player;

    private Button settings;
    private Label score;

    public GameViewController(MainApplication application) {
        super(application);
        game = application.getGame();
        player = application.getPlayer();

        view = new GameView();
        setRootView(view);

        settings = view.settings;
        score = view.score;

        initialize();
    }

    @Override
    public void initialize() {
       score.setText(String.valueOf(player.getScore()));

       settings.addEventHandler(ActionEvent.ACTION, event -> {
           SettingViewController controller = new SettingViewController(application);
           view.getChildren().add(controller.getRootView());
       });

    }
    
}