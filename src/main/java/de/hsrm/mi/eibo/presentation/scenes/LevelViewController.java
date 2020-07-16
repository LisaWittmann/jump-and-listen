package de.hsrm.mi.eibo.presentation.scenes;

import de.hsrm.mi.eibo.MainApplication;
import de.hsrm.mi.eibo.business.gamelogic.Level;
import de.hsrm.mi.eibo.presentation.Scenes;
import de.hsrm.mi.eibo.presentation.ViewController;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class LevelViewController extends ViewController<MainApplication> {

    private LevelView view;
    private HBox options;

    public LevelViewController(MainApplication application){
        super(application);

        view = new LevelView();
        setRootView(view);

        options = view.options;

        initialize();
    }

    @Override
    public void initialize() {
        
        for(Level l : Level.values()){
            Label name = new Label(l.toString());
            name.getStyleClass().add("h3");
            options.getChildren().add(name);

            name.setOnMouseClicked(event -> application.switchScene(Scenes.HIGHCSCORE_VIEW)); //TODO: wieder zu GameView ändern
        }
    }

    
}