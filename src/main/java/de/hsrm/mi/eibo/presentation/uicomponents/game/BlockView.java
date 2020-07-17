package de.hsrm.mi.eibo.presentation.uicomponents.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BlockView extends Rectangle {

    Rectangle innerRectangle;

    public BlockView(double width, double height, double x, double y) {
        super(width, height, x, y);
        setFill(Color.WHITE);

        innerRectangle  = new Rectangle(width/2, height/3, Color.BLACK);
        innerRectangle.setX(x + width/4);
        innerRectangle.setY(y);

        //TODO: Width-Listener
    }
    
}