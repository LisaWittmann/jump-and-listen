package de.hsrm.mi.eibo.business.tone;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.hsrm.mi.eibo.business.gamelogic.Block;
import de.hsrm.mi.eibo.business.gamelogic.Level;
import de.hsrm.mi.eibo.persistence.song.SongPersitinator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Erzeugung eines Songs aus Blöcken
 * @author pwieg001, lwitt001, lgers001
 */
public class SongBuilder {

    private SongPersitinator songPersitinator;
    private ObservableList<Song> savedSongs;
    
    private Song buildedSong;
    private List<Block> inputBlocks;

    public SongBuilder() {
        songPersitinator = new SongPersitinator();
        inputBlocks = new ArrayList<>();
        buildedSong = null;
        savedSongs = FXCollections.observableArrayList();
    }

    public Song getBuildedSong() {
        return buildedSong;
    }

    public Block addNext(double x, double height) {
        Block block = new Block(false);
        block.setPosX(x);
        block.setPosY(height-block.getHeight());
        return block;
    }

    public void add(Block block) {
        block.setHeight(Block.roundHeight(block.getHeight()));
        inputBlocks.add(block);
    }

    public Level calcLevel(List<Tone> tones) {
        int maxdist = 0;
        int dist;
        for (int i = 0; i < tones.size() - 2; i++) {
            dist = tones.get(i).ordinal() - tones.get(i+1).ordinal();
            if (dist < 0) dist *= (-1);
            if (dist > maxdist) maxdist = dist;
        }
        if(inputBlocks.size() < 25 && maxdist < 5) return Level.BEGINNER;
        if(inputBlocks.size() < 70 && maxdist < 10) return Level.INTERMEDIATE;
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

    public void discard(Block block) {
        if(inputBlocks.contains(block)) inputBlocks.remove(block);
    }

    public void discardAll() {
        inputBlocks.clear();
    }

    public Song confirm(String name) throws NameException {
        if(name == null || name.equals("")){
            throw new NameException();
        }
        else {
            buildedSong = new Song();
            buildedSong.setTones(transform());
            buildedSong.setLevel(calcLevel(buildedSong.getTones()));
            if(songPersitinator.nameAccepted(name)) {
                buildedSong.setName(name);
            }
            else throw new NameException();
            songPersitinator.saveData(buildedSong);
            return buildedSong;
        }
    }
}