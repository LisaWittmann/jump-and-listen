package de.hsrm.mi.eibo.presentation.scenes.createview;

import de.hsrm.mi.eibo.presentation.application.MainApplication;
import de.hsrm.mi.eibo.presentation.scenes.Scenes;
import de.hsrm.mi.eibo.presentation.scenes.ViewController;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class CreateViewController extends ViewController<MainApplication> {

    private CreateView view;
    private Button quitButton;

    public CreateViewController(MainApplication application) {
        super(application);

        view = new CreateView();
        setRootView(view);

        quitButton = view.quitButton;

        initialize();
    }

    @Override
    public void initialize() {
        quitButton.addEventHandler(ActionEvent.ACTION, e -> application.switchScene(Scenes.START_VIEW));

    }
    
}