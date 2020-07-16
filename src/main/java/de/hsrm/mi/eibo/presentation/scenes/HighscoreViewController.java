package de.hsrm.mi.eibo.presentation.scenes;

import de.hsrm.mi.eibo.MainApplication;
import de.hsrm.mi.eibo.presentation.ViewController;

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