package de.hsrm.mi.eibo.business.tone;

import java.util.LinkedList;
import java.util.List;

import de.hsrm.mi.eibo.business.gamelogic.Level;

/**
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class Song {

    private static final String TONSEPERATOR = " ";
    private static final String NAMESEPERATOR = ":";
    private static final String LEVELSEPERATOR = "-";

    private List<Tone> tones;
    private Level level;
    private String name;

    public Song() {
        super();
        level = Level.BEGINNER;
        tones = new LinkedList<>();
        name = "unknown";
    }
    
    public Song(String string) {
        this();
        string = string.toUpperCase();
        String [] parts = string.split(LEVELSEPERATOR);
        level = Level.valueOf(parts[0]);
        String [] sndparts = parts[1].split(NAMESEPERATOR);
        setName(sndparts[0]);
        String [] notes = sndparts[1].split(TONSEPERATOR);
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
        String [] notes = string.split(TONSEPERATOR);
        for (String note: notes) {
            for(Tone tone : Tone.values()) {
                if (note.equals(tone.name())) {
                    tones.add(tone);
                    continue;
                }
            }
        }
    }

    @Override
    public String toString() {
        String erg = level.toString() + LEVELSEPERATOR;
        erg += name + NAMESEPERATOR;
        for (Tone tone : tones) {
            erg = erg + tone.name() + TONSEPERATOR;
        }
        return erg;
    }

    public String getName() {
        return name;
    }

    public Level getLevel() {
        return level;
    }

    public static String getNameSeperator() {
        return NAMESEPERATOR;
    }

    public static String getLevelSeperator() {
        return LEVELSEPERATOR;
    }
    
    public List<Tone> getTones() {
        return tones;
    }

    protected void setTones(List<Tone> tones) {
        this.tones = tones;
    }

    protected void setLevel(Level level) {
        this.level = level;
    }

    public void setName(String name){
        this.name = name.toLowerCase();
    } 
}
