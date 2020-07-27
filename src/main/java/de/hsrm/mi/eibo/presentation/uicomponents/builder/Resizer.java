package de.hsrm.mi.eibo.presentation.uicomponents.builder;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

public class Resizer {

    private static final int RESIZE_MARGIN = 5;

    private double lastX, lastY;
    
    private Region region;
    private Scene scene;
    private Cursor cursor;

    private Resizer(Region region, Scene scene) {
        this.region = region;
        this.scene = scene;
        this.cursor = scene.getCursor();
    }

    public static void makeResizable(Region region, Scene scene) {
        Resizer resizer = new Resizer(region, scene);
        region.setOnMousePressed(event -> resizer.mousePressed(event));
        region.setOnMouseDragged(event -> resizer.mouseDragged(event));
        region.setOnMouseMoved(event -> resizer.mouseOver(event));
        region.setOnMouseReleased(event -> region.setCursor(Cursor.DEFAULT));
    }

    protected void mousePressed(MouseEvent event) {
        lastX = event.getSceneX();
        lastY = event.getSceneY();   
    }

    protected void mouseOver(MouseEvent event) {
        cursor = getCursor(event); 
        if(cursor != null) scene.setCursor(cursor); 
        else scene.setCursor(Cursor.DEFAULT);
    }

    protected void mouseDragged(MouseEvent event) {
        if(cursor.equals(Cursor.N_RESIZE)) {
            region.setPrefHeight(region.getPrefHeight() + (lastY - event.getSceneY()));
        }
        else if(cursor.equals(Cursor.E_RESIZE)) {
            region.setPrefWidth(event.getSceneX() - region.getLayoutX());
        }
        lastX = event.getSceneX();
        lastY = event.getSceneY();
    }

    private Cursor getCursor(MouseEvent event) {
        if(event.getY() < RESIZE_MARGIN) {
            return Cursor.N_RESIZE;
        } 
        else if(event.getX() >= (region.getWidth() - RESIZE_MARGIN)) {
            return Cursor.E_RESIZE;
        } 
        return null;
    }

}