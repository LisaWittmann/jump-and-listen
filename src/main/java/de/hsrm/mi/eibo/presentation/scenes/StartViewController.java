package de.hsrm.mi.eibo.presentation.scenes;

import de.hsrm.mi.eibo.MainApplication;
import de.hsrm.mi.eibo.presentation.Scenes;
import de.hsrm.mi.eibo.presentation.ViewController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class StartViewController extends ViewController<MainApplication> {

    private StartView view;
    private Button startButton;

    public StartViewController(MainApplication application){
        super(application);
        
        view = new StartView();
        setRootView(view);

        startButton = view.startButton;

        initialize();
    }

    @Override
    public void initialize() {
        startButton.addEventHandler(ActionEvent.ACTION, e -> {
            application.switchScene(Scenes.AUSWAHL_VIEW);
        });

    }

    
}