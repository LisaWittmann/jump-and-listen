package de.hsrm.mi.eibo.presentation.scenes.startview;

import de.hsrm.mi.eibo.presentation.application.*;
import de.hsrm.mi.eibo.presentation.scenes.*;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controller der StartView 
 * Auswahl des Spielmodus
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class StartViewController extends ViewController<MainApplication> {

    private StartView view;

    private Button startButton;
    private Button createButton;
    private ImageView image;

    public StartViewController(MainApplication application) {
        super(application);

        view = new StartView();
        setRootView(view);

        startButton = view.startButton;
        createButton = view.createButton;
        image = view.image;

        initialize();
    }

    @Override
    public void initialize() {
        image.setImage(new Image(
                getClass().getResource("/images/intro_" + application.getTheme().name().toLowerCase() + ".png")
                        .toExternalForm()));
        startButton.addEventHandler(ActionEvent.ACTION, e -> application.switchScene(Scenes.SELECT_VIEW));
        createButton.addEventHandler(ActionEvent.ACTION, e -> application.switchScene(Scenes.BUILD_VIEW));
    }



    
}