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

    /**
     * Konstruktor
     * Lädt alle gespeicherten Songs aus der Datenbank
     */
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

    /** 
     * Initialisiert Koordinaten aller Blöcke
     */
    public void initBlockPosition() {
        counter = 100;
        for(Block block : inputBlocks) {
            block.setPosY(height - block.getHeight());
            block.setPosX(counter);
            counter += block.getWidth() + distance;
        }
    }

    /**
     * Fügt am Ende der Liste von Blöcken ein leeres Element ein
     */
    public void addLast() {
        Block block = new Block(false);
        block.setPosX(counter);
        block.setPosY(height-block.getHeight());
        counter += block.getWidth() + distance;
        inputBlocks.add(block);
        emptyBlock = block;
    }

    /**
     * Block wird aus der Liste entfernt und Koordinaten aller Blöcke neu berechnet
     * @param block zu entfernender Block
     */
    public void discard(Block block) {
        if(inputBlocks.contains(block)) inputBlocks.remove(block);
        initBlockPosition();
    }

    /** 
     * Alle Blöcke verwerfen
     * Counter zum Errechnen der Koordinaten auf Anfangszustand setzen
     */
    public void discardAll() {
        counter = 100;
        inputBlocks.clear();
    }

    /**
     * Konvertierung eines Songs bestehend aus Blöcken in eine Song aus Tönen
     * Anschließend Abspeichgerung des Songs und Zurücksetzen der Blöcke
     * @param name Name unter dem Song abgespeichert werden soll
     * @return abgespeicherter Song
     * @throws NameException Eingegebener Name kann nicht akzeptiert werden
     */
    public Song confirm(String name) throws NameException {
        if(name == null || name.equals("")){
            // wenn Name leer ist, Fehler werfen
            throw new NameException();
        }
        else {
            // Konvertierung
            inputBlocks.remove(emptyBlock);
            buildedSong = new Song();
            buildedSong.setTones(convertToTones());
            buildedSong.setLevel(calcLevel(buildedSong.getTones()));
            buildedSong.setEditable(true);

            if(songPersitinator.nameAccepted(name)) {
                buildedSong.setName(name);
                addSong(buildedSong);
            }

            // bestehenden Song überschrieben, wenn im Editiermodus
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

    /**
     * Errechnet das Level für einen erstellten Song anhand der Distanz und Anzahl der Töne 
     * @param tones konvertierte Töne
     * @return Level des erstellten Songs
     */
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

    /**
     * Fügt einen Song in Datei ein und aktualisiert die Liste mit verwalteten Songs
     * @param song Song, der der Datei hinzugefügt werden soll
     */
    public void addSong(Song song) {
        songPersitinator.saveData(buildedSong);
        savedSongs.clear();
        savedSongs.addAll(songPersitinator.loadAll());
    }

    /**
     * Entfernt einen Song aus Datei und aktualisiert die Liste mit verwalteten Songs
     * @param song Song, der aus der Datei entfetrnt werden soll
     */
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

    public List<Song> getSongsByLevel(Level level) {
        List<Song> levelSongs = new ArrayList<>();
        for(Song s : savedSongs) {
            if(s.getLevel().equals(level)) {
                levelSongs.add(s);
            }
        }
        return levelSongs;
    }

    /**
     * Ermittelt, ob Nutzer bereits eigene Songs erstellt hat
     * @return true, wenn eigene Songs vorliegen, sonst false
     */
    public boolean hasBuildedSongs() {
        List<Song> songs = getSavedSongs();
        for (Song song : songs)  {
            if (song.isEditable()) {
                return true;
            }
        }
        return false;
    }
}