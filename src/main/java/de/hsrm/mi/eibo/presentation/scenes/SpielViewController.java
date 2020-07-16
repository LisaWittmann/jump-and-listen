package de.hsrm.mi.eibo.presentation.scenes;

import de.hsrm.mi.eibo.MainApplication;
import de.hsrm.mi.eibo.presentation.ViewController;

public class SpielViewController extends ViewController<MainApplication> {

    private SpielView view;

    public SpielViewController(MainApplication application){
        super(application);

        view = new SpielView();
        setRootView(view);
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub

    }
    
}