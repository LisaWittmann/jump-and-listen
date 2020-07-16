package de.hsrm.mi.eibo.presentation.scenes;

import de.hsrm.mi.eibo.MainApplication;
import de.hsrm.mi.eibo.presentation.ViewController;

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