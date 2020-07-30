package de.hsrm.mi.eibo.presentation.scenes.buildview;

import de.hsrm.mi.eibo.business.gamelogic.Block;
import de.hsrm.mi.eibo.business.tone.SongBuilder;
import de.hsrm.mi.eibo.business.tone.Tone;
import de.hsrm.mi.eibo.presentation.application.MainApplication;
import de.hsrm.mi.eibo.presentation.scenes.Scenes;
import de.hsrm.mi.eibo.presentation.scenes.ViewController;
import de.hsrm.mi.eibo.presentation.uicomponents.game.BlockView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class BuildViewController extends ViewController<MainApplication> {

    private BuildView view;
    private SongBuilder songBuilder;

    private Button quitButton;
    private HBox field;

    public BuildViewController(MainApplication application) {
        super(application);
        songBuilder = new SongBuilder();

        view = new BuildView();
        setRootView(view);

        quitButton = view.quitButton;
        field = view.field;

        initialize();
    }

    @Override
    public void initialize() {
        quitButton.addEventHandler(ActionEvent.ACTION, e -> application.switchScene(Scenes.START_VIEW));
        initLines();
        addBlock();

    }

    public void addBlock() {
        BlockView block = new BlockView(new Block(false));
        field.getChildren().add(block);

        block.getBlock().isInitialized().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    Resizer.makeResizable(block, application.getScene());
                    addBlock();
                }
            }
        });
    }

    private void initLines() {
        for(Tone tone : Tone.values()) {
            if(tone.isHalbton()) continue;
            Label name = new Label(tone.name());
            name.getStyleClass().add("fading-text");
            name.setStyle("-fx-text-alignment: left;");

            double y = application.getScene().getHeight() - Block.getHeightByTone(tone);
            name.setLayoutY(y-15);
            name.setLayoutX(20);
            Line line = new Line(50, y, application.getScene().getWidth(), y);
            line.getStyleClass().add("line");
            view.getChildren().addAll(name, line);
        }
    }
    
}