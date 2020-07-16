package de.hsrm.mi.eibo.business.gamelogic;

public enum Level {

    BEGINNER, INTERMEDIATE, EXPERT;

    @Override 
    public String toString(){
        return name().toLowerCase();
    }
    
}