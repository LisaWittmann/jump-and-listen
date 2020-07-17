package de.hsrm.mi.eibo.presentation.scenes.selectview;

import de.hsrm.mi.eibo.presentation.application.*;
import de.hsrm.mi.eibo.presentation.scenes.*;

import de.hsrm.mi.eibo.business.gamelogic.Game;
import de.hsrm.mi.eibo.business.gamelogic.Level;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Controller der SelectView
 * Initiiert die Wahlmöglichkeiten aus der Businesslogik
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class SelectViewController extends ViewController<MainApplication> {

    private Game game;
    private SelectView view;

    private HBox options;

    public SelectViewController(MainApplication application){
        super(application);
        game = application.getGame();

        view = new SelectView();
        setRootView(view);

        options = view.options;

        initialize();
    }

    @Override
    public void initialize() {
        
        for(Level currentLevel : Level.values()){
            Label name = new Label(currentLevel.toString());
            name.getStyleClass().add("h3");
            options.getChildren().add(name);

            name.setOnMouseClicked(event -> {
                game.setLevel(currentLevel);
                application.switchScene(Scenes.HIGHCSCORE_VIEW); //TODO: wieder zu GameView ändern, wenn View fertig ist
            }); 
        }
    }
    
}