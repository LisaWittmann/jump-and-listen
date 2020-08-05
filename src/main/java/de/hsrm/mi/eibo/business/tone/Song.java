package de.hsrm.mi.eibo.business.tone;

import java.util.LinkedList;
import java.util.List;

import de.hsrm.mi.eibo.business.gamelogic.Level;

/**
 * verwaltung von Tönen als Song
 * @author pwieg001, lwitt001, lgers001
 */
public class Song {

    private static final String TONSEPERATOR = " ";
    private static final String NAMESEPERATOR = ":";
    private static final String LEVELSEPERATOR = "-";
    private static final String EDITSEPERATOR = ";";

    private List<Tone> tones;
    private Level level;
    private String name;
    private boolean editable;

    public Song() {
        level = Level.BEGINNER;
        tones = new LinkedList<>();
        name = "unknown";
        editable = true;
    }
    
    /**
     * @param string Format: "level-edit;name: ton ton ton "
     */
    public Song(String string) {
        this();
        string = string.toUpperCase();
        String [] first = string.split(LEVELSEPERATOR);
        level = Level.valueOf(first[0]);
        String [] second = first[1].split(EDITSEPERATOR);
        editable = (second[0].equals("TRUE")) ? true : false;
        String[] third = second[1].split(NAMESEPERATOR);
        setName(third[0]);
        String [] notes = third[1].split(TONSEPERATOR);
        for (String note: notes) {
            for (Tone tone : Tone.values()) {
                if (note.equals(tone.name())) {
                    tones.add(tone);
                    continue;
                }
            }
        }
    }

    protected void setTones(List<Tone> tones) {
        this.tones = tones;
    }

    protected void setLevel(Level level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    } 

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isEditable() {
        return editable;
    }

    public String getName() {
        return name;
    }

    public Level getLevel() {
        return level;
    }

    public List<Tone> getTones() {
        return tones;
    }

    public static String getNameSeperator() {
        return NAMESEPERATOR;
    }

    public static String getLevelSeperator() {
        return LEVELSEPERATOR;
    }

    public static String getEditSeperator() {
        return EDITSEPERATOR;
    }

    /**
     * @return Format: "level-edit;name: ton ton ton "
     */
    @Override
    public String toString() {
        String erg = level.toString() + LEVELSEPERATOR;
        erg += editable + EDITSEPERATOR;
        erg += name + NAMESEPERATOR;
        for (Tone tone : tones) {
            erg = erg + tone.name() + TONSEPERATOR;
        }
        return erg;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Song other = (Song) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
