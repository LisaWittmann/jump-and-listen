package de.hsrm.mi.eibo.business.gamelogic;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.eibo.business.tone.Song;
import de.hsrm.mi.eibo.business.tone.Tone;

/**
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class Game {

    private Level level;
    private Player player;
    private List<Block> blocks;

    public Game(Player player){
        this.player = player;

        level = null;
        blocks = new ArrayList<>();
    }

    public void setLevel(Level level){
        this.level = level;
        //initBlocks();
    }

    public Level getLevel(){
        return level;
    }
    
    /**
     * erzeugt Blöcke aus einem zufälligen Song der Schwierigkeitsstufe 
     */
    private void initBlocks(){
        if(level == null) return;

        Song song = level.getRandomSong();
        for(Tone tone : song.getTones()){
            blocks.add(new Block(tone));
        }
    }
}