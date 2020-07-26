package de.hsrm.mi.eibo.business.gamelogic;

import de.hsrm.mi.eibo.business.gamelogic.exceptions.RestartException;
import de.hsrm.mi.eibo.business.tone.Song;
import de.hsrm.mi.eibo.business.tone.ToneMaker;
import de.hsrm.mi.eibo.persistence.HighscorePersistinator;
import de.hsrm.mi.eibo.persistence.SongPersitinator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class Game {
 
    private Player player;
    private Level level;  

    private Song song;
    private ToneMaker tonemaker;

    private ObservableList<Block> blocks;

    private double widthFactor;
    private double speedFactor;

    private boolean paused, running;

    private int score;
    private HighscorePersistinator highscorePersistinator;
    private SongPersitinator songPersitinator;

    public Game(){
        level = null;
        song = null;

        player = new Player();
        tonemaker = new ToneMaker();

        highscorePersistinator = new HighscorePersistinator();
        songPersitinator = new SongPersitinator();
        
        blocks = FXCollections.observableArrayList(); //raus

        paused = false; //raus 
        running = false; //raus

        widthFactor = 1;
        speedFactor = 1;
    }

    public ToneMaker getToneMaker() {
        return tonemaker;
    }

    public ObservableList<Block> getBlocks() {
        return blocks;
    }

    public Level getLevel() {
        return level;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isRunning() {
        return running;
    }

    public double getSpeed() {
        return speedFactor;
    }

    public double getWidth() {
        return widthFactor;
    }

    public void setSpeed(double speedFactor) {
        this.speedFactor = speedFactor;
    }

    public void setWidth(double widthFactor) {
        this.widthFactor = widthFactor;
        for(Block block : blocks){
            block.resize(widthFactor);
        }
    }

    public void setLevel(Level level) throws RestartException {
        this.level = level;
        initBlocks();
    }
    
    private void initBlocks() {
        if(level == null) return;

        blocks.add(new Block()); //StartBlock
        //Song song = level.getRandomSong();
        //for(Tone tone : song.getTones()){
        //    blocks.add(new Block(tone, tonemaker));
        //}
        blocks.add(new Block()); //Endblock
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
        player.saveScore();
        //TODO
    }

}