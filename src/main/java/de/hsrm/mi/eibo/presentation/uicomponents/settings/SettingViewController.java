package de.hsrm.mi.eibo.presentation.uicomponents.settings;

import de.hsrm.mi.eibo.presentation.application.MainApplication;
import de.hsrm.mi.eibo.presentation.scenes.ViewController;

/**
 * Controller für die Overlay View
 * Gibt neue Einstellungen an das Game weiter
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class SettingViewController extends ViewController<MainApplication> {

    private SettingView view;

    public SettingViewController(MainApplication application) {
        super(application);

        view = new SettingView();
        setRootView(view);

        initialize();
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub

    }
    
}