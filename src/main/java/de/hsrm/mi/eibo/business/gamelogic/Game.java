package de.hsrm.mi.eibo.business.gamelogic;

public class Game {

    private Level level;

    public Game(){
        level = null;
    }

    public void setLevel(Level level){
        this.level = level;
    }

    public Level getLevel(){
        return level;
    }
    
}