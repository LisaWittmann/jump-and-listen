package de.hsrm.mi.eibo.presentation.scenes.createview;

import de.hsrm.mi.eibo.presentation.application.MainApplication;
import de.hsrm.mi.eibo.presentation.scenes.Scenes;
import de.hsrm.mi.eibo.presentation.scenes.ViewController;
import de.hsrm.mi.eibo.presentation.uicomponents.create.Resizer;
import de.hsrm.mi.eibo.presentation.uicomponents.game.BlockView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class CreateViewController extends ViewController<MainApplication> {

    private CreateView view;
    private Button quitButton;
    private HBox field;

    public CreateViewController(MainApplication application) {
        super(application);

        view = new CreateView();
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
        BlockView block = new BlockView(true);
        field.getChildren().add(block);

        block.isInitialized().addListener(new ChangeListener<Boolean>() {
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