package de.hsrm.mi.eibo.persistence.highscore;

import de.hsrm.mi.eibo.business.tone.Song;
import de.hsrm.mi.eibo.persistence.song.SongPersitinator;

/** 
 * Datenformat zur Speicherung eines Highscores für einen Song
 * Implementierung des Comparable Interface um Highscores nach Wert sortieren zu können
 * @author pwieg001, lwitt001, lgers001
 */
public class Highscore implements Comparable<Highscore> {

    protected Song song;
    protected int score;

    private SongPersitinator songPersitinator;

    final String SEPERATOR = ";";
    
    /**
     * Konstruktor
     * @param song Song, für den ein Highscore erzeugt werden soll
     * @param score Highscore als Integer
     */
    public Highscore(Song song, int score) {
        this.song = song;
        this.score = score;
        songPersitinator = new SongPersitinator();
    }

    /**
     * Konstruktor zur Erzeugug eines Highscores aus einem String
     * @param line Format: "name;score"
     */
    public Highscore(String line){
        songPersitinator = new SongPersitinator();
        String [] parts = line.split(SEPERATOR);
        setSong(songPersitinator.loadByName(parts[0]));
        setScore(Integer.parseInt(parts[1]));
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Song getSong() {
        return song;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return song.getName() + SEPERATOR + String.valueOf(score);
    }

    @Override
    public int compareTo(Highscore highscore) {
        int compare = (score > highscore.getScore()) ? 1 : 0;
        if(compare == 0) {
            compare = (score == highscore.getScore()) ? 0 : -1;
        }
        return compare;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + score;
        result = prime * result + ((song == null) ? 0 : song.hashCode());
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
        Highscore other = (Highscore) obj;
        if (score != other.score)
            return false;
        if (song == null) {
            if (other.song != null)
                return false;
        } else if (!song.equals(other.song))
            return false;
        return true;
    }
}