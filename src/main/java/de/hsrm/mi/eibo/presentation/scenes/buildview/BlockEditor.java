package de.hsrm.mi.eibo.presentation.scenes.buildview;

import de.hsrm.mi.eibo.business.tone.SongManager;
import de.hsrm.mi.eibo.presentation.uicomponents.game.BlockView;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

/**
 * Zum Editieren und Löschen der Blöcke
 * @author pwieg001, lwitt001, lgers001
 */
public class BlockEditor {

    private static final int RESIZE_MARGIN = 20;

    private double y;
    
    private BlockView blockView;
    private Cursor cursor;

    private SongManager manager;

    /**
     * Konstruktor
     * @param blockView Block, der editierbar gemacht werden soll
     * @param manager Songmanager, in dem erstellter Song verwaltet wird
     */
    private BlockEditor(BlockView blockView, SongManager manager) {
        this.blockView = blockView;
        this.cursor = blockView.getCursor();
        this.manager = manager;
    }

    /**
     * Fügt einer BlockView Editier-Funktionen hinzu
     * @param blockView
     * @param manager
     */
    public static void makeEditable(BlockView blockView, SongManager manager) {
        BlockEditor editor = new BlockEditor(blockView, manager);
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
        
        if(event.isSecondaryButtonDown()) {
            manager.discard(blockView.getBlock());
        }
    }

    protected void mouseOver(MouseEvent event) {
        cursor = getCursor(event); 
        if(cursor != null) blockView.setCursor(cursor); 
        else blockView.setCursor(Cursor.DEFAULT);
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