package de.hsrm.mi.eibo.business.tone;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class Song {

    private final String SEPERATOR = " ";

    private List<Tone> tones;

    public Song() {
        super();
        tones = new LinkedList<>();
    }
    
    public Song(String string) {
        this();
        string = string.toUpperCase();
        String[] notes = string.split(SEPERATOR);
        for (String note: notes) {
            for (Tone tone : Tone.values()) {
                if (note.equals(tone.name())) {
                    tones.add(tone);
                    continue;
                }
            }
        }
    }

    @Override
    public String toString() {
        String erg = "";
        for (Tone tone : tones) {
            erg = erg + tone.name() + SEPERATOR;
        }
        return erg;
    }

    public String getSEPERATOR() {
        return SEPERATOR;
    }

    public List<Tone> getTones() {
        return tones;
    }
}
