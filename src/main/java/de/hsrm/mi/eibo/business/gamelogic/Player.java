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

    private SimpleBooleanProperty jump, boost, drop, move, right, left, landed;
    protected double posX, posY;
    private double vFalling;

    public PropertyChangeSupport changes;

    public Player(){
        jump = new SimpleBooleanProperty(false);
        boost = new SimpleBooleanProperty(false);
        drop = new SimpleBooleanProperty(false);
        move = new SimpleBooleanProperty(false);
        right = new SimpleBooleanProperty(false);
        left = new SimpleBooleanProperty(false);
        landed = new SimpleBooleanProperty(false);

        changes = new PropertyChangeSupport(this.getClass());

        vFalling = 0;
        moveTo(120, 400);
    }

    public void setOnStartPosition(){
        moveTo(120, 400);
        vFalling = 0;
        
        jump.set(false);
        boost.set(false);
        drop.set(false);
        move.set(false);
        right.set(false);
        left.set(false);
        landed.set(true);
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

    public SimpleBooleanProperty getRightProperty() {
        return right;
    }

    public SimpleBooleanProperty getLeftProperty() {
        return left;
    }

    public SimpleBooleanProperty getLandedProperty() {
        return landed;
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

    public void setOnRight(boolean right) {
        this.right.set(right);
    }

    public void setOnLeft(boolean left) {
        this.left.set(left);
    }

    public void setLanded(boolean landed) {
        this.landed.set(landed);
    }

    public void moveTo(double x, double y) {
        posX = x;
        posY = y;
        changes.firePropertyChange("koordinaten", null, null);
    }

    public synchronized double getVFalling() {
        return vFalling;
    }

    public synchronized void setVFalling(double vFalling) {
        this.vFalling = vFalling;
    }

    public synchronized double vFalling(double value, boolean set) {
        if (set) {
            setVFalling(value);
            return value;
        } else {
            return getVFalling();
        }
    }
}