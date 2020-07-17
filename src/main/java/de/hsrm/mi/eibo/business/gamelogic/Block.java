package de.hsrm.mi.eibo.business.gamelogic;

import de.hsrm.mi.eibo.business.tone.Tone;
import de.hsrm.mi.eibo.business.tone.ToneMaker;

/**
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class Block {

    protected Tone tone;
    protected double width, height;
    protected ToneMaker tonemaker;

    public Block(Tone tone, ToneMaker tonemaker) {
        this.tone = tone;
        this.tonemaker = tonemaker;
    
        width = calcHeight();
        height = calcWidth();
    }

    private double calcHeight() {
        return 0;
    }

    private double calcWidth() {
        return 0;
    }

    public void playTone() {
        try {
            tonemaker.createTone(tone.getFrequenz(), tonemaker.getVolume());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void resize(double widthFactor) {
        width *= widthFactor;
    }

}