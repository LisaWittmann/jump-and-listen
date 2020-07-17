package de.hsrm.mi.eibo.presentation.scenes.highscoreview;

import de.hsrm.mi.eibo.presentation.application.*;
import de.hsrm.mi.eibo.presentation.scenes.*;

public class HighscoreViewController extends ViewController<MainApplication> {

    private HighscoreView view;

    public HighscoreViewController(MainApplication application){
        super(application);

        view = new HighscoreView();
        setRootView(view);
        
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub

    }
    
}