package de.hsrm.mi.eibo.business.gamelogic;

import java.beans.PropertyChangeSupport;

import de.hsrm.mi.eibo.business.tone.Tone;
import de.hsrm.mi.eibo.business.tone.ToneMaker;

/**
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class Block {

    protected Tone tone;
    protected ToneMaker tonemaker;

    protected double width, height;
    protected boolean flagged;

    protected double widthFactor;

    protected static double minWidth = 250; 
    protected static double minHeight = 300;

    public PropertyChangeSupport changes;

    public Block(Tone tone, ToneMaker tonemaker) {
        this.tone = tone;
        this.tonemaker = tonemaker;
        
        flagged = false;
        
        widthFactor = 1;
        width = calcHeight();
        height = calcWidth();

        changes = new PropertyChangeSupport(this.getClass());
    }

    public Block() {
        flagged = true;

        widthFactor = 1;
        width = calcWidth();
        height = calcHeight();

        changes = new PropertyChangeSupport(this.getClass());
    }

    private double calcHeight() {
    if(flagged) return minHeight;
        else return minHeight + tone.getFrequenz() / 5;
    }

    private double calcWidth() {
        if(flagged) return widthFactor * minWidth;
        else return minWidth;
    }

    public double getWidth() {
        return widthFactor * width;
    }

    public double getHeight() {
        return height;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void playTone() {
        try {
            tonemaker.createTone(tone.getFrequenz(), tonemaker.getVolume());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void resize(double widthFactor) {
        double oldValue = this.widthFactor;
        this.widthFactor = widthFactor;

        changes.firePropertyChange("widthFactor", oldValue, width);    
    }

}