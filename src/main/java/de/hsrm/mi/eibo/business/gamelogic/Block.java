package de.hsrm.mi.eibo.business.gamelogic;

import de.hsrm.mi.eibo.business.tone.Tone;

/**
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class Block {

    protected Tone tone;
    protected double width, height;

    public Block(Tone tone){
        this.tone = tone;
    
        calcHeight();
        calcWidth();
    }

    private void calcHeight(){

    }

    private void calcWidth(){

    }
    
}