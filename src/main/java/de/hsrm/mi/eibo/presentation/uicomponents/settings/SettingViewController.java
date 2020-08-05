package de.hsrm.mi.eibo.presentation.uicomponents.settings;

import de.hsrm.mi.eibo.business.gamelogic.Game;
import de.hsrm.mi.eibo.presentation.application.MainApplication;
import de.hsrm.mi.eibo.presentation.application.Theme;
import de.hsrm.mi.eibo.presentation.scenes.ViewController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;

/**
 * Controller für die Overlay SettingsView 
 * Gibt neue Einstellungen an das Game weiter
 * @author pwieg001, lwitt001, lgers001
 */
public class SettingViewController extends ViewController<MainApplication> {

    private SettingView view;

    private Slider blockdistance, speed, volume;
    private ComboBox<Theme> theme;

    private Game game;

    public SettingViewController(MainApplication application) {
        super(application);
        game = application.getGame();

        view = new SettingView();
        setRootView(view);

        theme = view.theme;
        blockdistance = view.blockdistance;
        speed = view.speed;
        volume = view.volume;

        initialize();
    }

    @Override
    public void initialize() {
    
        // Alle Themes als Auswahlmöglichkeit in DropDown anbieten
        theme.getItems().addAll(Theme.values());
        theme.setValue(application.getTheme());
        theme.setOnAction(event -> application.switchTheme(theme.getValue()));

        // Slider, der Blockdistanz im Spiel ändert
        blockdistance.valueProperty().addListener(new ChangeListener<Number>() {
        
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                game.setBlockDistance(newValue.doubleValue());
            }

        });

        // Slider, der Geschwindigkeit des Spielers ändert
        speed.valueProperty().addListener(new ChangeListener<Number>() {
         
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                game.setSpeedFactor(newValue.doubleValue());
            }

        });

        // Slider, der Lautstärke der Töne ändert
        volume.valueProperty().addListener(new ChangeListener<Number>() {
            
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                game.getToneMaker().setVolume((int) newValue.doubleValue());
            }

        });

        // View neu zentrieren, wenn Breite verändert wird
        application.getScene().widthProperty().addListener(new ChangeListener<Number> () {

            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                initPosition();
            }

        });
        
    }

    public void initPosition() {
        view.setLayoutX(application.getScene().getWidth()/2 - view.getPrefWidth()/2);    
        view.setLayoutY(application.getScene().getHeight()/2 - view.getPrefHeight()/2);    
    }
    
}