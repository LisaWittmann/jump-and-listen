package de.hsrm.mi.eibo.presentation.scenes.buildview;

import de.hsrm.mi.eibo.presentation.uicomponents.game.BlockView;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class BlockResizer {

    private static final int RESIZE_MARGIN = 5;

    private double x, y;
    
    private BlockView region;
    private Scene scene;
    private Cursor cursor;

    private BlockResizer(BlockView region, Scene scene) {
        this.region = region;
        this.scene = scene;
        this.cursor = scene.getCursor();
    }

    public static void makeResizable(BlockView region, Scene scene) {
        BlockResizer resizer = new BlockResizer(region, scene);
        region.setOnMousePressed(event -> resizer.mousePressed(event));
        region.setOnMouseDragged(event -> resizer.mouseDragged(event));
        region.setOnMouseMoved(event -> resizer.mouseOver(event));
        region.setOnMouseReleased(event -> resizer.mouseReleased(event));
    }

    protected void mouseReleased(MouseEvent event) {
        scene.setCursor(Cursor.DEFAULT);
    }

    protected void mousePressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();   
    }

    protected void mouseOver(MouseEvent event) {
        cursor = getCursor(event); 
        if(cursor != null) scene.setCursor(cursor); 
        else scene.setCursor(Cursor.DEFAULT);
    }

    protected void mouseDragged(MouseEvent event) {
        if(cursor.equals(Cursor.N_RESIZE)) {
            region.getBlock().setHeight(region.getPrefHeight() + (y - event.getSceneY()));
        }
        else if(cursor.equals(Cursor.E_RESIZE)) {
            region.getBlock().setWidth(event.getSceneX() - region.getLayoutX());
        }
        x = event.getSceneX();
        y = event.getSceneY();
    }

    private Cursor getCursor(MouseEvent event) {
        if(event.getY() < RESIZE_MARGIN) {
            return Cursor.N_RESIZE;
        } 
        else if(event.getX() >= (region.getWidth() - RESIZE_MARGIN)) {
            return Cursor.E_RESIZE;
        } 
        else if(event.getY() > RESIZE_MARGIN) {
            return Cursor.S_RESIZE;
        }
        else if(event.getX() <= (region.getWidth())) {
            return Cursor.W_RESIZE;
        }
        return null;
    }

}