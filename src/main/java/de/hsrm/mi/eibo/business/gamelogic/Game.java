package de.hsrm.mi.eibo.business.gamelogic;

public class Game {

    private Level level;
    private Player player;

    public Game(Player player){
        level = null;
        this.player = player;
    }

    public void setLevel(Level level){
        this.level = level;
    }

    public Level getLevel(){
        return level;
    }
    
}