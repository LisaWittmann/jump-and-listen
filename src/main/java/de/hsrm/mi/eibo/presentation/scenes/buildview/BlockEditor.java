package de.hsrm.mi.eibo.presentation.scenes.buildview;

import de.hsrm.mi.eibo.presentation.uicomponents.game.BlockView;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class BlockEditor {

    private static final int RESIZE_MARGIN = 20;

    private double y;
    
    private BlockView blockView;
    private Scene scene;
    private Cursor cursor;

    private BlockEditor(BlockView blockView, Scene scene) {
        this.blockView = blockView;
        this.scene = scene;
        this.cursor = scene.getCursor();
    }

    public static void makeResizable(BlockView blockView, Scene scene) {
        BlockEditor editor = new BlockEditor(blockView, scene);
        blockView.setOnMousePressed(event -> editor.mousePressed(event));
        blockView.setOnMouseDragged(event -> editor.mouseDragged(event));
        blockView.setOnMouseMoved(event -> editor.mouseOver(event));
        blockView.setOnMouseReleased(event -> editor.mouseReleased(event));
    }

    protected void mouseReleased(MouseEvent event) {
        blockView.setCursor(Cursor.DEFAULT);
        blockView.getBlock().setHeight(blockView.getPrefHeight());
    }

    protected void mousePressed(MouseEvent event) {
        y = event.getSceneY();   
    }

    protected void mouseOver(MouseEvent event) {
        cursor = getCursor(event); 
        if(cursor != null) scene.setCursor(cursor); 
        else scene.setCursor(Cursor.DEFAULT);
    }

    protected void mouseDragged(MouseEvent event) {
        if(cursor.equals(Cursor.N_RESIZE)) {
            double height = blockView.getPrefHeight() + (y-event.getSceneY());
            blockView.setPrefHeight(height);
        }
        y = event.getSceneY();
    }

    private Cursor getCursor(MouseEvent event) {
        if(event.getY() < RESIZE_MARGIN) {
            return Cursor.N_RESIZE;
        } 
        else if(event.getX() >= (blockView.getWidth() - RESIZE_MARGIN)) {
            return Cursor.E_RESIZE;
        } 
        else if(event.getY() > RESIZE_MARGIN) {
            return Cursor.S_RESIZE;
        }
        else if(event.getX() <= (blockView.getWidth())) {
            return Cursor.W_RESIZE;
        }
        return null;
    }

}