package de.hsrm.mi.eibo.presentation.uicomponents.settings;

import de.hsrm.mi.eibo.business.gamelogic.Block;
import de.hsrm.mi.eibo.business.gamelogic.Game;
import de.hsrm.mi.eibo.presentation.application.MainApplication;
import de.hsrm.mi.eibo.presentation.scenes.ViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;

/**
 * Controller für die Overlay View Gibt neue Einstellungen an das Game weiter
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class SettingViewController extends ViewController<MainApplication> {

    private SettingView view;
    private Slider blockwidth, speed, volume;

    private Game game;

    public SettingViewController(MainApplication application) {
        super(application);
        game = application.getGame();

        view = new SettingView();
        setRootView(view);

        blockwidth = view.blockwidth;
        speed = view.speed;
        volume = view.volume;

        initialize();
    }

    @Override
    public void initialize() {
        blockwidth.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                for(Block block : game.getBlocks()){
                    block.resize(newValue.doubleValue());                    
                }
            }

        });

        speed.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // TODO Auto-generated method stub
            }

        });

        //so wäre es schöner: volume.valueProperty().bind(observable);

        volume.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				
			}

        });
    }
    
}