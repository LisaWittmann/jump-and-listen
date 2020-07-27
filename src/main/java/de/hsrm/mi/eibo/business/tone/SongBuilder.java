package de.hsrm.mi.eibo.business.tone;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.hsrm.mi.eibo.business.gamelogic.Block;
import de.hsrm.mi.eibo.business.gamelogic.Level;
import de.hsrm.mi.eibo.persistence.SongPersitinator;

public class SongBuilder {

    private SongPersitinator songPersitinator;
    private List<Block> inputBlocks;

    public SongBuilder() {
        inputBlocks = new ArrayList<>();
    }

    public Level calcLevel() {
        //TODO: was schönes ausdenken
        return null;
    }

    public void discard(Block block) {
        if(inputBlocks.contains(block)) inputBlocks.remove(block);
    }

    public void discardAll() {
        inputBlocks.clear();
    }
    
    public void confirm() {
        Song buildedSong = new Song();
        buildedSong.setTones(transform());
        songPersitinator.saveData(buildedSong);
    }

    public List<Tone> transform() {
        List<Tone> tones = new LinkedList<>();
        for(Block block : inputBlocks) {
            block.setTone(); 
            tones.add(block.getTone());
        }
        return tones;
    }
}