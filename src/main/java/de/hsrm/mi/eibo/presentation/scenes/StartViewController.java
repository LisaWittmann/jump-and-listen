package de.hsrm.mi.eibo.presentation.scenes;

import de.hsrm.mi.eibo.MainApplication;
import de.hsrm.mi.eibo.presentation.ViewController;

public class StartViewController extends ViewController<MainApplication> {

    private StartView view;

    public StartViewController(MainApplication application){
        super(application);
        
        view = new StartView();
        setRootView(view);
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub

    }

    
}