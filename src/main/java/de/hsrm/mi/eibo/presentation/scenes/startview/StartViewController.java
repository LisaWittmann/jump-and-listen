package de.hsrm.mi.eibo.presentation.scenes.startview;

import de.hsrm.mi.eibo.presentation.application.*;
import de.hsrm.mi.eibo.presentation.scenes.*;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 * Controller der StartView
 * Initiiert StartButton
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class StartViewController extends ViewController<MainApplication> {

    private StartView view;

    private Button startButton;
    private Button createButton;

    public StartViewController(MainApplication application) {
        super(application);
        
        view = new StartView();
        setRootView(view);

        startButton = view.startButton;
        createButton = view.createButton;

        initialize();
    }

    @Override
    public void initialize() {
        startButton.addEventHandler(ActionEvent.ACTION, e -> application.switchScene(Scenes.SELECT_VIEW));
        createButton.addEventHandler(ActionEvent.ACTION, e -> application.switchScene(Scenes.CREATE_VIEW));
    }

    
}