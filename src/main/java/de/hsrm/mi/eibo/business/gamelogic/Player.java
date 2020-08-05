package de.hsrm.mi.eibo.business.gamelogic;

import javafx.beans.property.SimpleBooleanProperty;

/**
 * Spieler bzw User
 * Schnittstelle für Persistence Schicht zum Speichern und Laden persönlicher Highscores
 * 
 * @author pwieg001, lwitt001, lger001
 */
public class Player {

    private SimpleBooleanProperty jump, boost, drop, move, right, left, landed;
    private SimpleBooleanProperty start;
    protected double posX, posY;
    private double startPosX, startPosY;
    private double vFalling;

    public Player(){
        startPosX = 120;
        startPosY = 400;

        jump = new SimpleBooleanProperty(false);
        boost = new SimpleBooleanProperty(false);
        drop = new SimpleBooleanProperty(false);
        move = new SimpleBooleanProperty(false);
        right = new SimpleBooleanProperty(false);
        left = new SimpleBooleanProperty(false);
        landed = new SimpleBooleanProperty(false);

        start = new SimpleBooleanProperty(true);

        vFalling = 0;
        moveTo(startPosX, startPosY);
    }

    /** 
     * Setzt Player auf Anfangszustand, wenn Spiel neu gestartet wird
     */
    public void setOnStartPosition(){
        moveTo(startPosX, startPosY);
        start.set(true);
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

    public double getStartPosX() {
        return startPosX;
    }

    public double getStartPosY() {
        return startPosY;
    }

    public SimpleBooleanProperty jumpProperty() {
        return jump;
    }

    public SimpleBooleanProperty boostProperty() {
        return boost;
    }

    public SimpleBooleanProperty dropProperty() {
        return drop;
    }

    public SimpleBooleanProperty moveProperty() {
        return move;
    }

    public SimpleBooleanProperty rightProperty() {
        return right;
    }

    public SimpleBooleanProperty leftProperty() {
        return left;
    }

    public SimpleBooleanProperty landedProperty() {
        return landed;
    }

    public SimpleBooleanProperty startProperty() {
        return start;
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
        start.set(false);
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