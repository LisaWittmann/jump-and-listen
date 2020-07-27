package de.hsrm.mi.eibo.presentation.scenes.buildview;

import de.hsrm.mi.eibo.business.gamelogic.Block;
import de.hsrm.mi.eibo.business.tone.SongBuilder;
import de.hsrm.mi.eibo.presentation.application.MainApplication;
import de.hsrm.mi.eibo.presentation.scenes.Scenes;
import de.hsrm.mi.eibo.presentation.scenes.ViewController;
import de.hsrm.mi.eibo.presentation.uicomponents.builder.Resizer;
import de.hsrm.mi.eibo.presentation.uicomponents.game.BlockView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

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
    
}