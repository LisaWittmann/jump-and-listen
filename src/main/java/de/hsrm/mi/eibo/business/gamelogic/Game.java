package de.hsrm.mi.eibo.business.gamelogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hsrm.mi.eibo.business.tone.Song;
import de.hsrm.mi.eibo.business.tone.ToneMaker;
import de.hsrm.mi.eibo.persistence.HighscorePersistinator;
import de.hsrm.mi.eibo.persistence.SongPersitinator;

/**
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class Game {
 
    private Player player;
    private Level level;  

    private Song song;
    private ToneMaker tonemaker;

    private double widthFactor;
    private double speedFactor;

    private boolean paused, running;

    private int score;
    private HighscorePersistinator highscorePersistinator;
    private SongPersitinator songPersitinator;

    public Game() {
        level = null;
        song = null;
        score = 0;

        player = new Player();
        tonemaker = new ToneMaker();

        highscorePersistinator = new HighscorePersistinator();
        songPersitinator = new SongPersitinator();

        paused = false;
        running = false; 

        widthFactor = 1;
        speedFactor = 1;
    }

    public ToneMaker getToneMaker() {
        return tonemaker;
    }

    public Level getLevel() {
        return level;
    }

    public Song getSong() {
        return song;
    }

    public Player getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public double getSpeed() {
        return speedFactor;
    }

    public double getWidth() {
        return widthFactor;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isRunning() {
        return running;
    }

    public void setLevel(Level level) {
        this.level = level;
        //this.song = loadSongByLevel();
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public void setSpeed(double speedFactor) {
        this.speedFactor = speedFactor;
    }

    public void setWidth(double widthFactor) {
        this.widthFactor = widthFactor;
    }

    //Hier müsste man vielleicht nochmal aussortieren
    public void restart() {
        score = 0;
        //TODO
    }

    public void start() {
        running = true;
        //TODO
    }

    public void pause() {
        paused = true;
        //TODO    
    }

    public void cont() {
        paused = false;
    }

    public void end() {
        running = false;
        saveScore();
        //TODO
    }

    //SpeedFactor: Player schneller oder langsamer bewegen (liegt immer zwischen 0.1 und 2, Default 1)
    //werden bei Tastendruck aufgerufen (kann ich aber auch wieder ändern)
    public void movePlayerLeft() {
        //TODO: Philipp
    }

    public void movePlayerRight() {
        //TODO: Philipp
    }

    public void playerJump() {
        //TODO: Philipp
        //BoostJump hier mit rein?
    }

    public void playerDrop() {
        //TODO: Philipp
        //Schwerkraft
    }

    /**
     * Speichert den aktuellen Score ab und setzt ihn danach wieder auf 0
     */
    public void saveScore() {
        highscorePersistinator.saveData(score);
        score = 0;
    }
    
    /**
     * Ermittelt die höchsten drei Scores des Spielers
     * Liest dafür gespeicherte Spielstände ein
     * @return Sublist mit höchsten drei Scores oder alle bisherigen Scores, wenn weniger als drei existieren
     */
    public List<Integer> getHighScores() { 
        List<Integer> scores = highscorePersistinator.loadData();
        List<Integer> sublist = new ArrayList<>();

        Collections.sort(scores);
        Collections.reverse(scores);

        if(scores.size() > 3){
            for(int i = 0; i < 3; i++){
                sublist.add(scores.get(i));
            }
        } else sublist.addAll(scores);

        return sublist;
    }

    /**
     * Sucht aus Datei mit gespeicherten Songs einen Song mit ausgewähltem Level
     * @return zufälligen Song mit passendem Level
     */
    public Song loadSongByLevel() {
        List<Song> songs = songPersitinator.loadData();
        for(Song song : songs) {
            if(!song.getLevel().equals(level)) songs.remove(song);
        }
        if(songs.size() < 1) return null;
        
        int random = (int) Math.random() * songs.size() + 1;
        return songs.get(random);
    }

}