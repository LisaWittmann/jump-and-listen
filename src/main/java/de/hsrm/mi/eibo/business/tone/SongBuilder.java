package de.hsrm.mi.eibo.business.tone;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.hsrm.mi.eibo.business.gamelogic.Block;
import de.hsrm.mi.eibo.business.gamelogic.Level;
import de.hsrm.mi.eibo.persistence.SongPersitinator;

/**
 * Erzeugung eines Songs aus Blöcken
 * @author pwieg001, lwitt001, lgers001
 */
public class SongBuilder {

    private SongPersitinator songPersitinator;
    private Song buildedSong;
    private List<Block> inputBlocks;

    public SongBuilder() {
        songPersitinator = new SongPersitinator();
        inputBlocks = new ArrayList<>();
        buildedSong = null;
    }

    public Level calcLevel() {
        if(inputBlocks.size() < 25) return Level.BEGINNER;
        if(inputBlocks.size() < 70) return Level.INTERMEDIATE;
        else return Level.EXPERT; 
    }

    public List<Tone> transform() {
        List<Tone> tones = new LinkedList<>();
        for(Block block : inputBlocks) {
            block.isInitialized().set(false);
            block.setTone(); 
            tones.add(block.getTone());
        }
        return tones;
    }

    public void add(Block block) {
        block.setHeight(Block.roundHeight(block.getHeight()));
        inputBlocks.add(block);
    }

    public Block addEmpty(double x, double height) {
        Block block = new Block(false);
        block.setPosX(x);
        block.setPosY(height-block.getHeight());
        return block;
    }

    public void discard(Block block) {
        if(inputBlocks.contains(block)) inputBlocks.remove(block);
    }

    public void discardAll() {
        inputBlocks.clear();
    }
    
    public Song confirm() {
        buildedSong = new Song();
        buildedSong.setTones(transform());
        buildedSong.setLevel(calcLevel());
        songPersitinator.saveData(buildedSong);
        return buildedSong;
    }

    public Song getBuildedSong() {
        return buildedSong;
    }
}