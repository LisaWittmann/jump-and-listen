package de.hsrm.mi.eibo.presentation.scenes;

import javafx.application.Application;
import javafx.scene.layout.Pane;

/**
 * gibt einheitliche Implementierung von Controller-Klassen vor
 * @param <T> laufende Applikaion
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public abstract class ViewController<T extends Application> {

    protected Pane rootView;
    protected T application;

    public ViewController() {}
    
    public ViewController(T application){
        this.application = application;
    }

    public Pane getRootView(){
        return rootView;
    }

    public void setRootView(Pane pane){
        rootView = pane;
    }

    public void setApplication(T application){
        this.application = application;
    }

    /**
     * Binding der View Kompomenten
     */
    public abstract void initialize();
    
}