package de.hsrm.mi.eibo.presentation;

import javafx.scene.layout.Pane;

public abstract class ViewController<T> {

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

    public abstract void initialize();
    
}