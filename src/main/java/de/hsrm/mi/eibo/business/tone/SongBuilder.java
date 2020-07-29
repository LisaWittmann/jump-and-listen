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
        inputBlocks = new ArrayList<>();
        buildedSong = null;
    }

    public Level calcLevel() {
        return null;
    }

    public List<Tone> transform() {
        List<Tone> tones = new LinkedList<>();
        for(Block block : inputBlocks) {
            block.setTone(); 
            tones.add(block.getTone());
        }
        return tones;
    }

    public void add(Block block) {
        block.setHeight(block.roundHeight(block.getHeight()));
        inputBlocks.add(block);
    }

    public void discard(Block block) {
        if(inputBlocks.contains(block)) inputBlocks.remove(block);
    }

    public void discardAll() {
        inputBlocks.clear();
    }
    
    public void confirm() {
        buildedSong = new Song();
        buildedSong.setTones(transform());
        songPersitinator.saveData(buildedSong);
    }

    public Song getBuildedSong() {
        return buildedSong;
    }
}