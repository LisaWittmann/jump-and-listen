package de.hsrm.mi.eibo.presentation.scenes;

import de.hsrm.mi.eibo.MainApplication;
import de.hsrm.mi.eibo.presentation.ViewController;

public class AuswahlViewController extends ViewController<MainApplication> {

    private AuswahlView view;

    public AuswahlViewController(MainApplication application){
        super(application);

        view = new AuswahlView();
        setRootView(view);
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub

    }
    
}