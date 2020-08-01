package de.hsrm.mi.eibo.presentation.uicomponents.tutorial;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class TutorialView extends VBox {

    private AnchorPane buttons;
    private Button close;
    private Button forward;

    private Label header;
    private Label instruction;

    private Map<String, String> steps;
    private LinkedList<String> keyList;

    private SimpleBooleanProperty visible;

    public TutorialView() {
        visible = new SimpleBooleanProperty(false);

        setAlignment(Pos.TOP_CENTER);
        setSpacing(15);
        setId("tutorial-window");
        
        close = new Button();
        close.getStyleClass().add("icon-button");
        close.setId("close-button");
        
        forward = new Button();
        forward.getStyleClass().add("icon-button");
        forward.setId("forward-button");

        buttons = new AnchorPane();
        buttons.getChildren().addAll(close, forward);
        AnchorPane.setTopAnchor(close, 15.0);
        AnchorPane.setLeftAnchor(close, 20.0);
        AnchorPane.setTopAnchor(forward, 15.0);
        AnchorPane.setRightAnchor(forward, 20.0);

        header = new Label();
        header.getStyleClass().add("h3");

        instruction = new Label();
        instruction.getStyleClass().add("normal-text");
        instruction.setMaxWidth(300);
        instruction.setAlignment(Pos.CENTER);
        instruction.setWrapText(true);

        getChildren().addAll(buttons, header, instruction);
    }

    public void show() {
        visible.set(true);
        header.setText(keyList.getFirst());
        instruction.setText(steps.get(keyList.getFirst()));

        close.addEventHandler(ActionEvent.ACTION, event ->  {
            setVisible(false);
            visible.set(false);
        });

        forward.addEventFilter(ActionEvent.ACTION, event -> nextStep(steps));
    }

    private void nextStep(Map<String, String> steps) {
        boolean breakAfterNext = false;
        for(Map.Entry<String, String> entry : steps.entrySet()) {
            if(breakAfterNext) {
                header.setText(entry.getKey());
                instruction.setText(entry.getValue());
                if(entry.getKey().equals(keyList.getLast())) { 
                    forward.setVisible(false);
                }
                break;
            }
            if(header.getText().equals(entry.getKey())) {
                breakAfterNext = true;   
            }
        }
    }

    public void setSteps(LinkedHashMap<String, String> steps) {
        this.steps = steps;
        keyList = new LinkedList<>(steps.keySet());
    }

    public SimpleBooleanProperty getVisibleProperty() {
        return visible;
    }
    
}