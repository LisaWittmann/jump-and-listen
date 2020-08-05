package de.hsrm.mi.eibo.presentation.scenes.buildview;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import de.hsrm.mi.eibo.business.tone.*;
import de.hsrm.mi.eibo.business.gamelogic.Block;
import de.hsrm.mi.eibo.presentation.application.MainApplication;
import de.hsrm.mi.eibo.presentation.scenes.Scenes;
import de.hsrm.mi.eibo.presentation.scenes.ViewController;
import de.hsrm.mi.eibo.presentation.uicomponents.game.BlockView;
import de.hsrm.mi.eibo.presentation.uicomponents.tutorial.TutorialView;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

/**
 * Controller der BuildView Kapselung der Daten des {@link SongManager}
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class BuildViewController extends ViewController<MainApplication> {

    private BuildView view;
    private SongManager songManager;

    private Button menuButton;
    private Button saveButton;
    private TextField songName;

    private AnchorPane song;

    private Block emptyBlock;

    private AnchorPane layer;
    private TutorialView tutorial;

    public BuildViewController(MainApplication application) {
        super(application);
        songManager = application.getSongManager();

        view = new BuildView();
        setRootView(view);

        menuButton = view.menuButton;
        saveButton = view.saveButton;
        songName = view.songName;
        song = view.song;

        tutorial = view.tutorial;
        layer = view.layer;

        view.getChildren().add(menu);

        initialize();
    }

    @Override
    public void initialize() {

        song.prefHeightProperty().bind(application.getScene().heightProperty());

        layer.prefWidthProperty().bind(application.getScene().widthProperty());
        layer.prefHeightProperty().bind(application.getScene().heightProperty());

        menu.prefWidthProperty().bind(application.getScene().widthProperty().divide(5));
        menu.prefHeightProperty().bind(application.getScene().heightProperty());

        placeCenter();

        menuButton.addEventHandler(ActionEvent.ACTION, event -> {
            layer.visibleProperty().unbind();
            layer.visibleProperty().bind(menu.visibleProperty());

            menu.toFront();
            menu.show();
        });

        saveButton.addEventHandler(ActionEvent.ACTION, event -> {
            try {
                application.getGame().setSong(songManager.confirm(songName.getText()));
                application.switchScene(Scenes.GAME_VIEW);
            } catch (NameException e) {
                // Wenn Name fehlerhaft ist Textfeld mit rotem Rand anzeigen
                songName.getStyleClass().add("error");
            }
        });

        // Wenn Fenstergröße geändert wird, Elemente erneut mittig platzieren
        application.getScene().widthProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                placeCenter();
            }

        });

        songManager.getInputBlocks().addListener(new ListChangeListener<Block>() {

            @Override
            public void onChanged(Change<? extends Block> changes) {
                while (changes.next()) {

                    if (changes.wasAdded()) {
                        // KeyListener initialisieren, wenn View aufgerufen wird
                        if (song.getChildren().isEmpty()) {
                            addKeyListener();
                        }
                        // Bei Bearbeiten eines Songs Namen übernehmen
                        if (songManager.getEditSong() != null) {
                            songName.setText(songManager.getEditSong().getName());
                        }

                        // Alle Blöcke abbilden
                        for (Block block : changes.getAddedSubList()) {
                            if (block.getPosX() > application.getScene().getWidth()) {
                                scrollSong(-150);
                            }
                            convert(block, block.isInitialized().get());
                        }
                    }

                    else if (changes.wasRemoved()) {
                        // Wenn alle Blöcke gelöscht werden, View zurücksetzen
                        if (songManager.getInputBlocks().isEmpty()) {
                            resetView();
                        }

                        else {
                            List<BlockView> remove = new ArrayList<>();
                            // Sonst zu löschende Töne ermitteln ...
                            for (Node node : song.getChildren()) {
                                BlockView blockView = (BlockView) node;
                                if (changes.getRemoved().contains(blockView.getBlock())) {
                                    remove.add(blockView);
                                }
                            }
                            // ... und aus der View entfernen
                            for (BlockView blockView : remove) {
                                song.getChildren().remove(blockView);
                            }
                        }
                    }
                }
            }
        });
        // Hilfslinien für Tonhöhen initialisieren
        initToneLines();

        // Wenn noch kein eigener Song hinzugefügt wurde Create-Tutorial einblenden
        if (!songManager.hasBuiltSongs()) {
            initTutorial();
        }
    }

    private void addKeyListener() {

        // Optionen anhand der möglichen Töne initialisieren
        List<KeyCode> options = new ArrayList<>();
        options.add(KeyCode.C);
        options.add(KeyCode.D);
        options.add(KeyCode.E);
        options.add(KeyCode.F);
        options.add(KeyCode.G);
        options.add(KeyCode.A);
        options.add(KeyCode.H);

        application.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {

            public void handle(KeyEvent event) {

                for (KeyCode code : options) {
                    // Wenn Shift gedrückt ist, den jeweiligen Halbton initialisieren
                    if (new KeyCodeCombination(code, KeyCodeCombination.SHIFT_DOWN).match(event)) {
                        // für H gibt es keinen Halbton
                        if (code.name().equals("H")) {
                            continue;
                        }
                        emptyBlock.setTone(Tone.valueOf(code.name() + "S"));
                        return;
                    }
                }

                // Ansonsten Ton für gedrückte Taste initialisieren
                if (options.contains(event.getCode())) {
                    emptyBlock.setTone(Tone.valueOf(event.getCode().name()));
                    return;
                }

                // Bildschirm nach links bewegen
                else if (event.getCode().equals(KeyCode.L)) {
                    scrollSong(-150);
                }
                // Bildschirm nach rechts bewegen
                else if (event.getCode().equals(KeyCode.R)) {
                    scrollSong(150);
                }
            }
        });
    }

    /**
     * Bildet Hilfslinien für Tonhöhen mit jeweiligem Tonnamen in der View ab
     */
    private void initToneLines() {
        for (Tone tone : Tone.values()) {

            // Halbtöne zur Übersichtlichkeit nicht anzeigen
            if (tone.isHalbton())
                continue;

            double height = Block.getHeightByTone(tone);

            Label name = new Label(tone.name());
            name.getStyleClass().add("normal-text");
            name.setStyle("-fx-text-alignment: left;");
            name.setId("fading");

            double y = application.getScene().getHeight() - height;
            name.setLayoutY(y - 15);
            name.setLayoutX(20);

            Line line = new Line(50, y, 5000, y);
            line.getStyleClass().add("line");

            AnchorPane.setBottomAnchor(line, height);
            AnchorPane.setLeftAnchor(line, 50.0);

            AnchorPane.setBottomAnchor(name, height - 15);

            view.getChildren().addAll(name, line);
        }
    }

    /**
     * Füllt die {@link TutorialView} mit Texten und zeigt sie danach an
     */
    private void initTutorial() {

        // Anleitungstexte initialisieren
        LinkedHashMap<String, String> steps = new LinkedHashMap<>();
        steps.put("welcome", "learn how to create own songs");
        steps.put("add tone", "'\u002b' will add a new tone to your song");
        steps.put("drag tone", "change the height of a tone by dragging it up or down");
        steps.put("keyboard", "you can also add songs by typing in the letter");
        steps.put("remove", "click right on a tone to remove it from your song");
        steps.put("scroll", "press R to scroll right and L to scroll left");
        steps.put("name", "the name must only contain lower case letters and spaces");
        steps.put("save", "you need to enter a name for your song to save it");

        // Anleitungstexte setzen
        tutorial.setSteps(steps);

        // Tutorial einblenden
        layer.toFront();
        layer.visibleProperty().bind(tutorial.visibleProperty());
        view.getChildren().add(tutorial);
        tutorial.show();
    }

    private void scrollSong(double x) {
        song.setLayoutX(song.getLayoutX() + x);
    }

    /**
     * Erzeugt aus Block eine {@link BlockView} Wenn Block leer, wird Listener
     * gesetzt, sonst wird Block editierbar gemacht
     */
    private void convert(Block block, boolean initialized) {
        BlockView blockView = new BlockView(block);
        song.getChildren().add(blockView);
        AnchorPane.setBottomAnchor(blockView, 0.0);

        if (!initialized) {
            emptyBlock = block;

            // Wenn leerer Block initialisiert wurde, Block editierbar machen und nächsten
            // Leeren Block einfügen
            block.isInitialized().addListener(new InvalidationListener() {

                public void invalidated(Observable observable) {
                    if (block.isInitialized().get()) {
                        BlockEditor.makeEditable(blockView, songManager);
                        songManager.addLast();
                    }
                }

            });
        }

        else {
            // Alle bereits initialisierten Blöcke editierbar machen
            BlockEditor.makeEditable(blockView, songManager);
        }
    }

    public void resetView() {
        // Zurück an Anfang scrollen und alle Blöcke entfernen
        song.setLayoutX(0);
        song.getChildren().clear();

        // Textfeld zurücksetzen
        if (songName.getStyleClass().contains("error")) {
            songName.getStyleClass().remove("error");
        }
        songName.setText("");
    }

    /**
     * Elemente mittig platzieren
     */
    public void placeCenter() {
        songName.setLayoutX(application.getScene().getWidth() / 2 - songName.getPrefWidth() / 2);
        tutorial.setLayoutX(application.getScene().getWidth() / 2 - tutorial.getPrefWidth() / 2);
        tutorial.setLayoutY(application.getScene().getHeight() / 2 - tutorial.getPrefHeight() / 2);
    }

}