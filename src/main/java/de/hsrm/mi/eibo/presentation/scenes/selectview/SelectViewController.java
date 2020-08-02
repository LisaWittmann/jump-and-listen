package de.hsrm.mi.eibo.presentation.scenes.selectview;

import de.hsrm.mi.eibo.presentation.application.*;
import de.hsrm.mi.eibo.presentation.scenes.*;

import de.hsrm.mi.eibo.business.gamelogic.Game;
import de.hsrm.mi.eibo.business.gamelogic.Level;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controller der SelectView Initiiert die Wahlmöglichkeiten aus der
 * Businesslogik
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class SelectViewController extends ViewController<MainApplication> {

    private Game game;
    private SelectView view;

    private VBox content;
    private HBox options;
    private Button menuButton;
    private AnchorPane layer;

    public SelectViewController(MainApplication application) {
        super(application);
        game = application.getGame();

        view = new SelectView();
        setRootView(view);

        content = view.content;
        options = view.options;
        menuButton = view.menuButton;
        layer = view.layer;

        initResizeableElements();
        initialize();
    }

    @Override
    public void initialize() {

        for (Level currentLevel : Level.values()) {
            Label name = new Label(currentLevel.toString());
            name.getStyleClass().add("h3");
            options.getChildren().add(name);

            name.setOnMouseClicked(event -> {
                game.setLevel(currentLevel);
                application.switchScene(Scenes.GAME_VIEW);
            });
        }

        menuButton.addEventHandler(ActionEvent.ACTION, event -> {
            menu.show();
        });

        application.getWidth().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				initResizeableElements();
			}
        });

    }

    public void initResizeableElements() {
        content.setPrefSize(application.getWidth().get(), application.getScene().getHeight());
        layer.setPrefSize(application.getWidth().get(), application.getScene().getHeight());
    }

    
}