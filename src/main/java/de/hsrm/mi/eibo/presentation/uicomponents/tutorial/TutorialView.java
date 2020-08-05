package de.hsrm.mi.eibo.presentation.uicomponents.tutorial;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Viewkomponente für das Tutorial, dieses wird schrittweise angezeigt
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class TutorialView extends VBox {

    private AnchorPane buttons;
    private Button close;
    private Button forward;

    private VBox text;
    private Label header;
    private Label instruction;

    private Map<String, String> steps;
    private LinkedList<String> keyList;

    public TutorialView() {
        text = new VBox();
        text.setAlignment(Pos.TOP_CENTER);
        text.setSpacing(15);

        setAlignment(Pos.TOP_CENTER);
        setId("tutorial-window");

        close = new Button("\u0445");
        close.getStyleClass().add("text-button");

        forward = new Button("\u2192");
        forward.getStyleClass().add("text-button");

        buttons = new AnchorPane();
        buttons.getChildren().addAll(close, forward);
        AnchorPane.setTopAnchor(close, 0.0);
        AnchorPane.setLeftAnchor(close, 0.0);
        AnchorPane.setTopAnchor(forward, 0.0);
        AnchorPane.setRightAnchor(forward, 0.0);
        setPadding(new Insets(10));

        header = new Label();
        header.getStyleClass().add("h3");

        instruction = new Label();
        instruction.getStyleClass().add("normal-text");
        instruction.setMaxWidth(300);
        instruction.setAlignment(Pos.CENTER);
        instruction.setWrapText(true);

        text.getChildren().addAll(header, instruction);

        setPrefSize(400, 250);
        getChildren().addAll(buttons, text);
    }

    public void show() {
        header.setText(keyList.getFirst());
        instruction.setText(steps.get(keyList.getFirst()));

        close.addEventHandler(ActionEvent.ACTION, event -> {
            setVisible(false);
        });

        forward.addEventFilter(ActionEvent.ACTION, event -> nextStep(steps));
    }

    private void nextStep(Map<String, String> steps) {
        boolean breakAfterNext = false;
        for (Map.Entry<String, String> entry : steps.entrySet()) {
            if (breakAfterNext) {
                header.setText(entry.getKey());
                instruction.setText(entry.getValue());
                if (entry.getKey().equals(keyList.getLast())) {
                    forward.setVisible(false);
                }
                break;
            }
            if (header.getText().equals(entry.getKey())) {
                breakAfterNext = true;
            }
        }
    }

    public void setSteps(LinkedHashMap<String, String> steps) {
        this.steps = steps;
        keyList = new LinkedList<>(steps.keySet());
    }

}