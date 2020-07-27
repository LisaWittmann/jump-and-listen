package de.hsrm.mi.eibo.business.tone;

import java.util.LinkedList;
import java.util.List;

import de.hsrm.mi.eibo.business.gamelogic.Level;

/**
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class Song {

    private final String SEPERATOR = " ";

    private List<Tone> tones;
    private Level level;

    public Song() {
        super();
        level = Level.BEGINNER;
        tones = new LinkedList<>();
    }
    
    public Song(String string) {
        this();
        string = string.toUpperCase();
        String [] parts = string.split(":");
        level = Level.valueOf(parts[0]);
        String[] notes = parts[1].split(SEPERATOR);
        for (String note: notes) {
            for (Tone tone : Tone.values()) {
                if (note.equals(tone.name())) {
                    tones.add(tone);
                    continue;
                }
            }
        }
    }

    public Song(Level level, String string) {
        this();
        this.level = level;
        string = string.toUpperCase();
        String [] notes = string.split(SEPERATOR);
        for (String note: notes) {
            for(Tone tone : Tone.values()) {
                if (note.equals(tone.name())) {
                    tones.add(tone);
                    continue;
                }
            }
        }
    }

    public Song(Level level, List<Tone> tones){
        this.tones = tones;
        this.level = level;
    }

    @Override
    public String toString() {
        String erg = level.toString() + ":";
        for (Tone tone : tones) {
            erg = erg + tone.name() + SEPERATOR;
        }
        return erg;
    }

    public Level getLevel() {
        return level;
    }

    public String getSEPERATOR() {
        return SEPERATOR;
    }

    public List<Tone> getTones() {
        return tones;
    }
}
