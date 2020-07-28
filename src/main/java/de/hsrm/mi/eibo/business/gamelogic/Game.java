package de.hsrm.mi.eibo.business.gamelogic;

import java.beans.PropertyChangeSupport;
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

/**
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class Game {
 
    private Player player;
    private Level level;  

    private Song song;
    private ToneMaker tonemaker;
    private LinkedList<Block> blocks;

    private double widthFactor;
    private double speedFactor;

    private boolean paused, running;

    private int score;
    private HighscorePersistinator highscorePersistinator;
    private SongPersitinator songPersitinator;

    private SimpleBooleanProperty initialized;
    private SimpleBooleanProperty ended;

    //Einstellungen:
    final int FPS = 20;
    final double FORCE_MULTI = 50;
    final double G_FORCE = -9.8066 * FORCE_MULTI;
    final double JUMP_FORCE = G_FORCE * (-0.65);
    private boolean movementActive = false;

    public PropertyChangeSupport changes;

    public Game() {
        System.out.println("Game wird Konstruktoriert");
        level = null;
        song = null;
        score = 0;

        blocks = new LinkedList<>();
        player = new Player();
        tonemaker = new ToneMaker();

        highscorePersistinator = new HighscorePersistinator();
        songPersitinator = new SongPersitinator();

        initialized = new SimpleBooleanProperty(false);
        ended = new SimpleBooleanProperty(false);

        paused = false;
        running = false;

        widthFactor = 1;
        speedFactor = 1;

        changes = new PropertyChangeSupport(getClass());
    }

    public SimpleBooleanProperty isInitialized() {
        return initialized;
    }

    public SimpleBooleanProperty gameEnded() {
        return ended;
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
        blocks.addFirst(new Block(true)); //Start
        for(Tone tone : song.getTones()) {
            blocks.addLast(new Block(tone, tonemaker));
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
        ended.set(true);
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
        System.out.print("Und ich ...");
        if (player.vFalling(0.0, false) == 0) {
            player.posY -= 10;
            if(player.getBoostProperty().get()) {
                player.vFalling(JUMP_FORCE * (2), true);
                System.out.print(" SPRINGE!!!\n");
            } else {
                player.vFalling(JUMP_FORCE, true);
                System.out.print(" springe!\n");
            }
        }
        System.out.println("VF=" + player.vFalling(0.0, false));
    }

    public void playerYCalculation() {
        if(checkBlockUnderPlayer()) {
            //player.vFalling(0, true);
            player.setOnJump(false);
            player.setOnDrop(false);
            if (player.vFalling(0, false) > 0) {
                player.posY -= player.vFalling(0, false)/FPS;
            }
        } else {
            player.setOnDrop(true);
            player.vFalling(player.vFalling(0, false) + G_FORCE/FPS, true);
            if (!checkPlayerLanding())
                player.posY -= player.vFalling(0, false)/FPS;
        }
    }

    /**
     * Speichert den aktuellen Score ab und setzt ihn danach wieder auf 0
     */
    public void saveScore() {
        highscorePersistinator.saveData(score);
        score = 0;
    }
    
    public void setScore(int score) {
        int oldValue = this.score;
        this.score = score;
        changes.firePropertyChange("score", oldValue, this.score);
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
        List<Song> possabilities = new ArrayList<>();
        for(Song song : songs) {
            if(song.getLevel().equals(level)) possabilities.add(song);
        }
        if(possabilities.size() < 1) return null;
        
        int random = (int) Math.random() * possabilities.size();
        return possabilities.get(random);
    }

    /**
     * Kontrolliert, ob der Spieler sich auf einem Block befindet.
     */
    private boolean checkBlockUnderPlayer() {
        for (Block block : blocks) {
            if (player.posY + 100 == block.getPosY()
            && player.posX + 58 > block.getPosX()
            && player.posX + 24 < block.getPosX() + block.getWidth()) {
                block.isIntersected().set(true);

                if(!block.equals(blocks.getFirst())) {
                    setScore(score+50); //TODO: hier was sinnvolles implenetieren
                }
                if(block.equals(blocks.getLast())) {
                    end();
                }
                return true;
            }
        }
        return false;
    }

    private boolean checkPlayerLanding() {
        for (Block block: blocks) {
            if (player.posX + 58 > block.getPosX()
                    && player.posX + 24 < block.getPosX() + block.getWidth()) {
                if (player.posY + 100 < block.getPosY() && player.posY + 100 - player.vFalling(0, false)/FPS > block.getPosY()) {
                    player.posY = block.getPosY() - 100;
                    player.vFalling(0, true);
                    player.setOnDrop(false);
                    player.setOnJump(false);
                    try {
                        tonemaker.createTone(block.getTone());
                    } catch (NullPointerException np) {
                        //In dem fall ist man auf Start oder Ende
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private void activateMovement() {
        if (!movementActive) {
            Runnable runnable = () -> {
                int check = 0; //TODO: Später entfernen
                while (running) {
                    check++;//TODO: Später entfernen
                    try {
                        Thread.sleep((int) 1000 / FPS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    playerYCalculation();
                    if (player.getLeftProperty().get()) {
                        player.posX -= speedFactor * 5;
                    }
                    if (player.getMRightProperty().get()) {
                        player.posX += speedFactor * 5;
                    }

                    player.moveTo(player.getPosX(), player.getPosY());
                    if (check % 20 == 0) {//TODO: Später entfernen
                        System.out.println("\n\nPlayer at:\nx: " + player.posX + "\ny: " + player.posY + "\nvFalling: " + player.vFalling(0, false));//TODO: Später entfernen
                    }//TODO: Später entfernen
                }
            };
            Thread movement = new Thread(runnable, "movement");
            System.out.println("Starting Movement...");
            movement.start();
            movementActive = true;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        running = false;
    }

}