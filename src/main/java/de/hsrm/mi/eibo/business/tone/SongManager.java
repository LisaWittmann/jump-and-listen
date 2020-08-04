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
 * greift auf Song-Datenbank zu und kann Manipulationen durchführen
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class SongManager {

    private SongPersitinator songPersitinator;
    private ObservableList<Song> savedSongs;
    
    private Song buildedSong;
    private Song editSong;

    private ObservableList<Block> inputBlocks;
    private Block emptyBlock;

    private double counter = 100;
    private double distance = 50;
    private double height = 0;

    public SongManager() {
        buildedSong = null;
        editSong = null;
        emptyBlock = null;

        songPersitinator = new SongPersitinator();

        inputBlocks = FXCollections.observableArrayList();
        savedSongs = FXCollections.observableArrayList();
        savedSongs.addAll(songPersitinator.loadAll());
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public ObservableList<Block> getInputBlocks() {
        return inputBlocks;
    }

    public void initBlockPosition() {
        counter = 100;
        for(Block block : inputBlocks) {
            block.setPosY(height - block.getHeight());
            block.setPosX(counter);
            counter += block.getWidth() + distance;
        }
    }

    public void addLast() {
        Block block = new Block(false);
        block.setPosX(counter);
        block.setPosY(height-block.getHeight());
        counter += block.getWidth() + distance;
        inputBlocks.add(block);
        emptyBlock = block;
    }

    public void discard(Block block) {
        if(inputBlocks.contains(block)) inputBlocks.remove(block);
        initBlockPosition();
    }

    public void discardAll() {
        counter = 100;
        inputBlocks.clear();
    }

    public Song confirm(String name) throws NameException {
        if(name == null || name.equals("")){
            throw new NameException();
        }
        else {
            inputBlocks.remove(emptyBlock);
            buildedSong = new Song();
            buildedSong.setTones(convertToTones());
            buildedSong.setLevel(calcLevel(buildedSong.getTones()));
            buildedSong.setEditable(true);

            if(songPersitinator.nameAccepted(name)) {
                buildedSong.setName(name);
                addSong(buildedSong);
            }

            else if(editSong != null) {
                savedSongs.clear();
                buildedSong.setName(editSong.getName());
                savedSongs.addAll(songPersitinator.overrideData(buildedSong));
                editSong = null;
            }

            else throw new NameException();
            
            discardAll();
            return buildedSong;
        }
    }

    public Song getBuildedSong() {
        return buildedSong;
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

    public List<Tone> convertToTones() {
        List<Tone> tones = new LinkedList<>();
        for(Block block : inputBlocks) {
            block.isInitialized().set(false);
            block.setTone(); 
            tones.add(block.getTone());
        }
        return tones;
    }

    public List<Block> convertToBlocks(List<Tone> tones) {
        List<Block> blocks = new LinkedList<>();
        for(Tone tone : tones) {
            blocks.add(new Block(tone));
        }
        return blocks;
    }  

    public void addSong(Song song) {
        songPersitinator.saveData(buildedSong);
        savedSongs.clear();
        savedSongs.addAll(songPersitinator.loadAll());
    }

    public void removeSong(Song song) {
        if(!song.isEditable()) return;
        savedSongs.clear();
        savedSongs.addAll(songPersitinator.removeData(song));
    }

    public void editSong(Song song) { 
        discardAll();
        editSong = song;
        inputBlocks.addAll(convertToBlocks(song.getTones()));
        initBlockPosition();
    }

    public Song getEditSong() {
        return editSong;
    }

    public ObservableList<Song> getSavedSongs() {
        return savedSongs;
    }

    public Song getSongByName(String name) {
        for(Song s : savedSongs) {
            if(s.getName().equals(name)) return s;
        }
        return null;
    }

    public List<Song> getSongByLevel(Level level) {
        List<Song> levelSongs = new ArrayList<>();
        for(Song s : savedSongs) {
            if(s.getLevel().equals(level)) {
                levelSongs.add(s);
            }
        }
        return levelSongs;
    }
}