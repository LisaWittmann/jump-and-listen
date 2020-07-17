package de.hsrm.mi.eibo.business.gamelogic;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.eibo.business.gamelogic.exceptions.RestartException;
import de.hsrm.mi.eibo.business.tone.Song;
import de.hsrm.mi.eibo.business.tone.Tone;
import de.hsrm.mi.eibo.business.tone.ToneMaker;

/**
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class Game {
 
    private Player player;

    private Level level;  
    private List<Block> blocks;

    private ToneMaker tonemaker;
    private double widthFactor;
    private double speedFactor;

    private boolean paused, running;

    public Game(Player player){
        this.player = player;

        level = null;
        tonemaker = new ToneMaker();
        blocks = new ArrayList<>();

        paused = true;
        running = false;

        widthFactor = 1;
        speedFactor = 1;
    }

    public Level getLevel() {
        return level;
    }

    public double getSpeed() {
        return speedFactor;
    }

    public double getWidth() {
        return widthFactor;
    }

    public void setSpeed(double speedFactor) {
        this.speedFactor = speedFactor;
    }

    public void setWidth(double widthFactor) {
        this.widthFactor = widthFactor;
        for(Block block : blocks){
            block.resize(widthFactor);
        }
    }

    public void setLevel(Level level) throws RestartException {
        if(running) throw new RestartException("your current game progress will be reset. are you sure?");
        this.level = level;
        //initBlocks();
    }
    
    private void initBlocks() {
        if(level == null) return;

        Song song = level.getRandomSong();
        for(Tone tone : song.getTones()){
            blocks.add(new Block(tone, tonemaker));
        }
    }

    public void restart() {
        running = false;
        paused = true;
        blocks.clear();
    }

    public void start() {
        running = true;
        //TODO
    }

    public void pause() {
        paused = true;
        //TODO
    }

    public void end() {
        running = false;
        player.allScores.add(player.score);
        //TODO
    }

}