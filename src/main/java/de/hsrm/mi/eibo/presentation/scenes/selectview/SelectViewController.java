package de.hsrm.mi.eibo.presentation.scenes.selectview;

import de.hsrm.mi.eibo.presentation.application.*;
import de.hsrm.mi.eibo.presentation.scenes.*;
import de.hsrm.mi.eibo.business.gamelogic.Game;
import de.hsrm.mi.eibo.business.gamelogic.Level;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controller der SelectView 
 * Initiiert die Wahlmöglichkeiten aus der Businesslogik
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

        view.getChildren().add(menu);

        initialize();
    }
    
    @Override
    public void initialize() {

        content.prefWidthProperty().bind(application.getScene().widthProperty());
        content.prefHeightProperty().bind(application.getScene().heightProperty());

        layer.prefWidthProperty().bind(application.getScene().widthProperty());
        layer.prefHeightProperty().bind(application.getScene().heightProperty());

        menu.prefHeightProperty().bind(application.getScene().heightProperty());
        menu.prefWidthProperty().bind(application.getScene().widthProperty().divide(5));

        menuButton.addEventHandler(ActionEvent.ACTION, event -> menu.show());
        layer.visibleProperty().bind(menu.visibleProperty());

        for (Level currentLevel : Level.values()) {
            
            Label name = new Label(currentLevel.toString());
            name.getStyleClass().add("h3");
            options.getChildren().add(name);

            name.setOnMouseClicked(event -> {
                game.setLevel(currentLevel);
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        application.switchScene(Scenes.GAME_VIEW);
                    }

                });
            });
        }
    }

    
}