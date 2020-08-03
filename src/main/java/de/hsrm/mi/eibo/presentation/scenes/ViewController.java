package de.hsrm.mi.eibo.presentation.scenes;

import de.hsrm.mi.eibo.presentation.uicomponents.menu.Menu;

import javafx.application.Application;
import javafx.scene.layout.Pane;

/**
 * gibt einheitliche Implementierung von Controller-Klassen vor
 * @param <T> laufende Applikaion
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public abstract class ViewController<T extends Application> {

    protected Menu menu;

    protected Pane rootView;
    protected T application;

    public ViewController() {}
    
    public ViewController(T application) {
        this.application = application;
        this.menu = new Menu(application);
    }

    public Pane getRootView() {
        return rootView;
    }

    public void setRootView(Pane pane) {
        rootView = pane;
        menu.setView(rootView);
    }

    public void setApplication(T application) {
        this.application = application;
    }

    public abstract void initialize();

    public abstract void initResizeable();
    

}