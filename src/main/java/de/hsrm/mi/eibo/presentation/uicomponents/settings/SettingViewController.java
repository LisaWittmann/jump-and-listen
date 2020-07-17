package de.hsrm.mi.eibo.presentation.uicomponents.settings;

import de.hsrm.mi.eibo.business.gamelogic.Game;
import de.hsrm.mi.eibo.presentation.application.MainApplication;
import de.hsrm.mi.eibo.presentation.scenes.ViewController;

public class SettingViewController extends ViewController<MainApplication> {

    private SettingView view;
    private Game game;

    public SettingViewController(MainApplication application) {
        super(application);
        game = application.getGame();

        view = new SettingView();
        setRootView(view);

        initialize();
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub

    }
    
}