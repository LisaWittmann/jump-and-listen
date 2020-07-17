package de.hsrm.mi.eibo.presentation.scenes.gameview;

import de.hsrm.mi.eibo.presentation.application.*;
import de.hsrm.mi.eibo.presentation.scenes.*;

public class GameViewController extends ViewController<MainApplication> {

    private GameView view;

    public GameViewController(MainApplication application){
        super(application);

        view = new GameView();
        setRootView(view);
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub

    }
    
}