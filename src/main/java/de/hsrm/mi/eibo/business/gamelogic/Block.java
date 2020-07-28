package de.hsrm.mi.eibo.business.gamelogic;

import java.beans.PropertyChangeSupport;

import de.hsrm.mi.eibo.business.tone.*;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Schnittstelle zwischen SongBuilding und Game
 * Kalkulationen eines Blocks aus einem Ton
 * Kalkulation eines Tons aus einem Block
 * Konfigurationen des Spiels
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class Block {

    private static double minWidth, maxWidth;
    private static double minHeight, maxHeight;
    private static double distance;

    public static void configureWidth(double min, double max){
        minWidth = min;
        maxWidth = max;
    }

    public static void configuteHeight(double min, double max){
        minHeight = min;
        maxHeight = max;
        distance = (maxHeight-minHeight) / (Tone.values().length-1);
    }

    private Tone tone;
    private ToneMaker toneMaker;

    private double posX, posY; //Achtung: Die sind bisher noch nicht gesetzt! 
    private double height, width;

    private SimpleBooleanProperty initialized;

    public PropertyChangeSupport changes;

    /**
     * Konstukor für Blöcke, doe einen eindeutigen Ton abbilden
     * @param tone eindeutiger Ton, null darf nicht übergeben werden
     * @param toneMaker ToneMaker der Ablaufumgebung
     */
    public Block(Tone tone, ToneMaker toneMaker){
        this.tone = tone;
        this.toneMaker = toneMaker;

        width = minWidth;
        height = getHeightByTone(tone);
        changes = new PropertyChangeSupport(getClass());
        initialized = new SimpleBooleanProperty(true);
    }

    /**
     * Konstukror für Blöcke, die keinen Ton abbilden
     * und für Blöcke, aus denen Töne konstuiert werden
     * @param platform Block ist Spielerplattform (also Start und Ende)
     */
    public Block(boolean platform) {
        tone = null;
        toneMaker = null;

        if(platform) {
            height = minHeight;
            width = maxWidth;
            initialized = new SimpleBooleanProperty(true);
        } 
        else {
            height = minHeight;
            width = minWidth;
            initialized = new SimpleBooleanProperty(false);
        }
        changes = new PropertyChangeSupport(getClass());
    }

    public void setHeight(double height) {
        double oldValue = this.height;
        this.height = height;
        changes.firePropertyChange("height", oldValue, height);
    }

    public void setWidth(double width) {
        double oldValue = this.width;
        this.width = width;
        changes.firePropertyChange("width", oldValue, width);
    }

    public void setPosX(double x) {
        double oldValue = posX;
        posX = x;
        changes.firePropertyChange("posX", oldValue, posX);
    }

    public void setPosY(double y) {
        double oldValue = posY;
        posY = y;
        changes.firePropertyChange("posY", oldValue, posY);
    }

    public void setToneMaker(ToneMaker toneMaker) {
        this.toneMaker = toneMaker;
    }

    public void setTone() { //TODO: Exception wäre Sinnvoll
        tone = getToneByHeight(height); 
    }

    public Tone getTone() {
        return tone;
    }

    public ToneMaker getToneMaker() {
        return toneMaker;
    }

    public SimpleBooleanProperty isInitialized() {
        return initialized;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

     /**
      * rundet Höhe, sodass diese in den min/max Grenzen und in der Schrittweite liegt
      * @return gerundende Höhe
      */
    public double roundHeight(double height) {
        if(height < minHeight) return minHeight;
        if(height > maxHeight) return maxHeight;

        for(double i = minHeight; i <= maxHeight; i += distance) {
            if(height > i + distance/2) continue;
            else return i;
        }
        return height;
    }

    /**
     * Sucht passende Höhe zu einem Ton anhand der konfigurierten Klassen-Variablen
     * @param tone Ton zu dem Höhe ermittelt werden soll
     * @return kalkulierte Höhe
     */
    public double getHeightByTone(Tone tone) {
        for(int i = 0; i < Tone.values().length; i++){
            if(Tone.values()[i].equals(tone)) return minHeight + i*distance;
        }
        return minHeight;
    }

    /**
     * Sucht Ton zu angegebener Höhe 
     * @return getroffener Ton, sonst null
     */
    public Tone getToneByHeight(double height) {
        height = roundHeight(height);
        for(int i = 0; i < Tone.values().length; i++) {
            if((minHeight + i*distance) == height) return Tone.values()[i];
        }
        return null;
    }
    
}