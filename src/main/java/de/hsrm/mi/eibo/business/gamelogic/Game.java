package de.hsrm.mi.eibo.business.gamelogic;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.hsrm.mi.eibo.business.tone.Song;
import de.hsrm.mi.eibo.business.tone.Tone;
import de.hsrm.mi.eibo.business.tone.ToneMaker;
import de.hsrm.mi.eibo.persistence.Highscore;
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

    private boolean paused, running;

    private int score;
    private HighscorePersistinator highscorePersistinator;
    private SongPersitinator songPersitinator;

    private SimpleBooleanProperty initialized;
    private SimpleBooleanProperty ended;

    //Einstellungen:
    private double speedFactor = 1;
    private double blockDistanz = 100;
    final int FPS = 20;
    final double FORCE_MULTI = 70;
    final double G_FORCE = -9.8066 * FORCE_MULTI;
    final double JUMP_FORCE = G_FORCE * (-0.60);
    final double BOOST_MULTI = 1.5;
    private boolean movementActive = false;
    private int falldepthGameOver = 1000;
    private double sceneHeight = 0;
    private boolean tutorial;

    public PropertyChangeSupport changes;

    public Game() {
        level = null;
        song = null;
        score = 0;

        blocks = new LinkedList<>();
        player = new Player();
        tonemaker = new ToneMaker();

        highscorePersistinator = new HighscorePersistinator();
        songPersitinator = new SongPersitinator();

        tutorial = false;
        if(highscorePersistinator.loadListData().isEmpty()) tutorial = true;

        initialized = new SimpleBooleanProperty(false);
        ended = new SimpleBooleanProperty(false);

        paused = false;
        running = false;

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

    public List<Block> getBlocks() {
        return blocks;
    }

    public int getScore() {
        return score;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean needsTutorial() {
        return tutorial;
    }
    
    public double getBlockDistanz() {
        return blockDistanz;
    }

    public double getSpeedFactor() {
        return speedFactor;
    }

    public void setSpeedFactor(double speedFactor) {
        this.speedFactor = speedFactor;
    }

    public void setSceneHeight(double sceneHeight){
        this.sceneHeight = sceneHeight;
        this.falldepthGameOver = (int) this.sceneHeight + 50;
    }

    public void setBlockDistanz(double blockDistanz) {
        this.blockDistanz = blockDistanz;
        if (this.initialized.get()) {
            initBlockPosition();
        }
    }

    public void setLevel(Level level) {
        this.level = level;
        setBlockDistanz(level.distance);
        setSpeedFactor(level.speedFactor);
        List<Song> matchingSongs = songsForLevel();
        int random = (int) Math.random() * matchingSongs.size();
        setSong(matchingSongs.get(random));
    }

    public void setSong(Song song) {
        this.song = song;
        initBlocks(song);
    }

    public void setSong(String name) {
        Song song = songPersitinator.loadByName(name);
        if(!song.equals(this.song)) {
            this.song = song;
            restart();
        }
    }

    public void initBlocks(Song song) {
        blocks.addFirst(new Block(true)); 
        for(Tone tone : song.getTones()) {
            blocks.addLast(new Block(tone, tonemaker));
        }
        blocks.add(new Block(true));
        initBlockPosition();
        initialized.set(true);
    }

    public void initBlockPosition() {
        double x = 0;
        for(Block block : blocks) {
            block.setPosY(sceneHeight - block.getHeight());
            block.setPosX(x);
            x += block.getWidth() + blockDistanz;
        }
    }

    public void restart() {
        setScore(0);
        player.setOnStartPosition();
        movementActive = false;

        initialized.set(false);
        ended.set(false);

        paused = false;
        running = false;

        blocks.clear();
        initBlocks(song);
    }

    public void start() {
        running = true;
        activateMovement();
    }

    public void end() {
        tutorial = false;
        if(score != 0) saveScore();
        ended.set(true);
        running = false;  
    }

    public void close() {
        running = false;
    }

    /**
     * Speichert den aktuellen Score ab und setzt ihn danach wieder auf 0
     */
    public void saveScore() {
        highscorePersistinator.saveData(new Highscore(song,score));
    }
    
    public synchronized void setScore(int score) {
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
        List<Highscore> scores = highscorePersistinator.loadBySong(song);
        List<Integer> sublist = new ArrayList<>();

        Collections.sort(scores);
        Collections.reverse(scores);

        if(scores.size() > 3){
            for(int i = 0; i < 3; i++){
                sublist.add(scores.get(i).getScore());
            }
        } else {
            for(Highscore h : scores) {
                sublist.add(h.getScore());
            }
        }
        return sublist;
    }

    public List<Song> songsForLevel() {
        return songPersitinator.loadByLevel(level);
    }

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
        if (player.vFalling(0.0, false) == 0) {
            player.posY -= 10;
            if(player.getBoostProperty().get()) {
                player.vFalling(JUMP_FORCE * (BOOST_MULTI), true);
            } else {
                player.vFalling(JUMP_FORCE, true);
            }
        }
    }

    public void playerYCalculation() {
        if(checkBlockUnderPlayer()) {
            //player.vFalling(0, true);
            player.setOnJump(false);
            player.setOnDrop(false);
            if (player.vFalling(0, false) > 0) {
                player.posY -= (player.vFalling(0, false)/FPS) * speedFactor;
            }
        } else {
            player.setOnDrop(true);
            player.vFalling(player.vFalling(0, false) + G_FORCE/FPS, true);
            if (!checkPlayerLanding())
                player.posY -= (player.vFalling(0, false)/FPS) * speedFactor;
            if(player.posY > falldepthGameOver) {
                tonemaker.fallingTone();
                end();
            }
        }
    }

    /**
     * Kontrolliert, ob der Spieler sich auf einem Block befindet.
     */
    private boolean checkBlockUnderPlayer() {
        for (Block block : blocks) {
            if (player.posY + 100 == block.getPosY()
            && player.posX + 58 > block.getPosX()
            && player.posX + 24 < block.getPosX() + block.getWidth()) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPlayerLanding() {
        for (Block block: blocks) {
            if (player.posX + 58 > block.getPosX()
                    && player.posX + 24 < block.getPosX() + block.getWidth()) {
                if (player.posY + 100 < block.getPosY() && player.posY + 100 - (player.vFalling(0, false)/FPS) * speedFactor > block.getPosY()) {
                    player.posY = block.getPosY() - 100;
                    player.vFalling(0, true);
                    player.setOnDrop(false);
                    player.setOnJump(false);

                    if(!block.equals(blocks.getFirst())) {
                        if (block.isIntersected().get()) {
                            setScore(getScore() - 10);
                        } else {
                            setScore(getScore() + level.point);
                        }
                    }
                    block.isIntersected().set(true);
                    if(block.equals(blocks.getLast())) {
                        end();
                    }
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
                while (running) {
                    try {
                        Thread.sleep((int) 1000 / FPS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    playerYCalculation();
                    if (player.getLeftProperty().get()) {
                        player.posX -= speedFactor * 10;
                    }
                    if (player.getMRightProperty().get()) {
                        player.posX += speedFactor * 10;
                    }

                    player.moveTo(player.getPosX(), player.getPosY());
                }
            };
            Thread movement = new Thread(runnable, "movement");
            movement.start();
            movementActive = true;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        running = false;
    }

}