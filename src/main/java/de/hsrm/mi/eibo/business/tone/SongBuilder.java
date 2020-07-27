package de.hsrm.mi.eibo.business.tone;

import java.util.LinkedList;
import java.util.List;

import de.hsrm.mi.eibo.business.gamelogic.Level;
import de.hsrm.mi.eibo.persistence.SongPersitinator;

public class SongBuilder {

    private SongPersitinator songPersitinator;
    private List<Tone> tones;
    private Level level;

    public SongBuilder() {
        tones = new LinkedList<>();
        level = null;
    }

    public void setLevel(Level level){
        this.level = level;
    }

    public Level calcLevel() {
        //TODO: was schönes ausdenken
        return null;
    }

    public List<Tone> getTones() {
        return tones;
    }

    public void discard(Tone tone) {
        if(tones.contains(tone)) tones.remove(tone);
    }

    public void discardAll() {
        tones.clear();
    }
    
    public void confirm() {
        if(level == null) level = calcLevel();
        Song buildedSong = new Song(level, tones);
        songPersitinator.saveData(buildedSong);
    }
}