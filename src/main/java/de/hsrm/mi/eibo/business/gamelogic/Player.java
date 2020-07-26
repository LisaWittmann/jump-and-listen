package de.hsrm.mi.eibo.business.gamelogic;

import java.beans.PropertyChangeSupport;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Spieler bzw User
 * Schnittstelle für Persistence Schicht zum Speichern und Laden persönlicher Highscores
 * 
 * @author pwieg001, lwitt001, lger001
 */
public class Player {

    private SimpleBooleanProperty jump, boost, drop, move;
    protected double posX, posY;

    public PropertyChangeSupport changes;

    public Player(){
        jump = new SimpleBooleanProperty(false);
        boost = new SimpleBooleanProperty(false);
        drop = new SimpleBooleanProperty(false);
        move = new SimpleBooleanProperty(false);

        changes = new PropertyChangeSupport(this.getClass());
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public SimpleBooleanProperty getJumpProperty() {
        return jump;
    }

    public SimpleBooleanProperty getBoostProperty() {
        return boost;
    }

    public SimpleBooleanProperty getDropProperty() {
        return drop;
    }

    public SimpleBooleanProperty getMoveProperty() {
        return move;
    }

    public void setOnJump(boolean jump) {
        this.jump.set(jump);
    }

    public void setOnBoost(boolean boost) {
        this.boost.set(boost);
    }

    public void setOnDrop(boolean drop) {
        this.drop.set(drop);
    }

    public void setOnMove(boolean move) {
        this.move.set(move);
    }

    public void moveTo(double x, double y) {
        posX = x;
        posY = y;
        changes.firePropertyChange("koordinaten", null, null);
    }
    
}