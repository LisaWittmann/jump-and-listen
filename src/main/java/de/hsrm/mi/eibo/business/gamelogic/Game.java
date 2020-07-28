package de.hsrm.mi.eibo.business.gamelogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.hsrm.mi.eibo.business.tone.Song;
import de.hsrm.mi.eibo.business.tone.Tone;
import de.hsrm.mi.eibo.business.tone.ToneMaker;
import de.hsrm.mi.eibo.persistence.HighscorePersistinator;
import de.hsrm.mi.eibo.persistence.SongPersitinator;

import javafx.beans.property.SimpleBooleanProperty;
import javax.sound.sampled.LineUnavailableException;

/**
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class Game {
 
    private Player player;
    private Level level;  

    private Song song;
    private ToneMaker tonemaker;
    private List<Block> blocks;

    private double widthFactor;
    private double speedFactor;

    private boolean paused, running;

    private int score;
    private HighscorePersistinator highscorePersistinator;
    private SongPersitinator songPersitinator;

    private SimpleBooleanProperty initialized;

    //Einstellungen:
    final int FPS = 20;
    final double G_FORCE = -9.8066 * 15;
    final double JUMP_FORCE = 20;

    public Game() {
        System.out.println("Game wird Konstruktoriert");
        level = null;
        song = null;
        score = 0;

        blocks = new ArrayList<>();

        player = new Player();
        tonemaker = new ToneMaker();

        highscorePersistinator = new HighscorePersistinator();
        songPersitinator = new SongPersitinator();

        initialized = new SimpleBooleanProperty(false);

        paused = false;
        running = false;

        widthFactor = 1;
        speedFactor = 1;
    }

    public SimpleBooleanProperty isInitialized() {
        return initialized;
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
        setSong(loadSongByLevel());
    }

    public void setSong(Song song) {
        this.song = song;
        if(song != null) initBlocks(song);
    }

    public void setSpeed(double speedFactor) {
        this.speedFactor = speedFactor;
    }

    public void setWidth(double widthFactor) {
        this.widthFactor = widthFactor;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void initBlocks(Song song) {
        blocks.clear();
        blocks.add(new Block(true)); //Start
        for(Tone tone : song.getTones()) {
            blocks.add(new Block(tone, tonemaker));
        }
        blocks.add(new Block(true)); //Ende
        initialized.set(true);
    }

    //Hier müsste man vielleicht nochmal aussortieren
    public void restart() {
        score = 0;
        //TODO
    }

    public void start() {
        running = true;
        activateMovement();
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
    public void movePlayerLeft(Boolean move) {
        player.setOnMove(move);
        player.setOnRight(false);
        player.setOnLeft(move);
    }

    public void movePlayerRight(Boolean move) {
        player.setOnMove(move);
        player.setOnLeft(false);
        player.setOnRight(move);
    }

    public void playerJump() {
        if (checkBlockUnderPlayer()) {
            if(player.getBoostProperty().get()) {
                player.setVFalling(JUMP_FORCE * (-2));
            } else {
                player.setVFalling(JUMP_FORCE * (-1));
            }
        }
    }

    public void playerDrop() {
        if(checkBlockUnderPlayer()) {
            player.setVFalling(0);
            player.setOnDrop(false);
        } else {
            player.setOnDrop(true);
            player.setVFalling(player.getVFalling() + G_FORCE/FPS);
            if (!checkPlayerLanding())
                player.posY -= player.getVFalling()/FPS;
        }
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
        
        int random = (int) Math.random() * songs.size();
        return songs.get(random);
    }

    /**
     * Kontrolliert, ob der Spieler sich auf einem Block befindet.
     */
    private boolean checkBlockUnderPlayer() {
        for (Block block : blocks) {
            if (player.posY + 100 == block.getPosY()
            && player.posX + 100 > block.getPosX()
            && player.posX < block.getPosX() + block.getWidth()) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPlayerLanding() {
        for (Block block: blocks) {
            if (player.posX + 100 > block.getPosX()
                    && player.posX < block.getPosX() + block.getWidth()) {
                if (player.posY < block.getPosY() && player.posY + player.getVFalling() * FPS > block.getPosY()) {
                    player.posY = block.getPosY();
                    try {
                        tonemaker.createTone(block.getTone());
                    } catch (LineUnavailableException e) {
                        System.out.println(e.getMessage());
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private void activateMovement() {
        Runnable runnable = () -> {
            int check = 0;
            while (running) {
                check++;
                try {
                    Thread.sleep((int) 1000/FPS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                playerDrop();
                if (player.getLeftProperty().get()) {
                    player.posX -= speedFactor * 5;
                }
                if (player.getMRightProperty().get()) {
                    player.posX += speedFactor * 5;
                }

                player.moveTo(player.getPosX(), player.getPosY());
                if (check % 20 == 0) {
                    System.out.println("\n\nPlayer at:\nx: " + player.posX + "\ny: " + player.posY);
                }
            }
        };
        Thread movement = new Thread(runnable, "movement");
        System.out.println("Starting Movement...");
        movement.start();
    }

    @Override
    protected void finalize() throws Throwable {
        running = false;
    }

}