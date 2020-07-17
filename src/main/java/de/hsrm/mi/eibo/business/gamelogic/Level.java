package de.hsrm.mi.eibo.business.gamelogic;

public enum Level {

    BEGINNER(1), INTERMEDIATE(2), EXPERT(3);
    
    private int value;

    private Level(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    @Override 
    public String toString(){
        return name().toLowerCase();
    }
    
}