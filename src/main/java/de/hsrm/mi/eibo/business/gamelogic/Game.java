package de.hsrm.mi.eibo.business.gamelogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.hsrm.mi.eibo.business.tone.*;
import de.hsrm.mi.eibo.persistence.highscore.*;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Spiellogik
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class Game {

    private Player player;
    private Level level;

    private Song song;
    private ToneMaker tonemaker;
    private LinkedList<Block> blocks;

    private boolean running;

    private int point;
    private SimpleIntegerProperty score;
    private SongManager songManager;
    private HighscorePersistinator highscorePersistinator;

    private SimpleBooleanProperty initialized;
    private SimpleBooleanProperty ended;

    // Einstellungen:
    private double speedFactor = 1;
    private double distance = 100;
    final int FPS = 20;
    final double FORCE_MULTI = 70;
    final double G_FORCE = -9.8066 * FORCE_MULTI;
    final double JUMP_FORCE = G_FORCE * (-0.60);
    final double BOOST_MULTI = 1.7;
    private double playerMinX = 0;
    private double playerMaxX = Double.POSITIVE_INFINITY;
    private boolean tutorial = false;
    private int falldepthGameOver = 1000;
    private double height = 0;

    public Game() {
        level = null;
        song = null;

        blocks = new LinkedList<>();
        player = new Player();
        tonemaker = new ToneMaker();
        songManager = new SongManager();

        highscorePersistinator = new HighscorePersistinator();
        if (highscorePersistinator.loadAll().isEmpty())
            tutorial = true;

        score = new SimpleIntegerProperty(0);
        initialized = new SimpleBooleanProperty(false);
        ended = new SimpleBooleanProperty(false);

        running = false;
    }

    public SimpleBooleanProperty initializedProperty() {
        return initialized;
    }

    public SimpleBooleanProperty endedProperty() {
        return ended;
    }

    public SimpleIntegerProperty scoreProperty() {
        return score;
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

    public SongManager getSongManager() {
        return songManager;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public int getScore() {
        return score.get();
    }

    public boolean isRunning() {
        return running;
    }

    public boolean needsTutorial() {
        return tutorial;
    }

    public double getBlockDistanz() {
        return distance;
    }

    public double getSpeedFactor() {
        return speedFactor;
    }

    public void setTutorial(boolean tutorial) {
        this.tutorial = false;
    }

    /**
     * ??bertr??gt Konfiguration der Geschwindigkeit und berechnet neue Punktzahl pro
     * Block
     * 
     * @param speedFactor neuer Wert
     */
    public void setSpeedFactor(double speedFactor) {
        this.speedFactor = speedFactor;
        if (speedFactor < level.speedFactor) {
            point = level.point - 10;
        } else if (speedFactor > level.speedFactor) {
            point = level.point + 10;
        }
    }

    public void setHeight(double height) {
        this.height = height;
        falldepthGameOver = (int) height + 50;
        songManager.setHeight(height);
    }

    /**
     * ??bertr??gt Konfiguration der Blockdistanz und berechnet neue Punktzahl pro
     * Block
     * 
     * @param distance neuer Wert
     */
    public void setBlockDistance(double distance) {
        this.distance = distance;
        if (distance < (level.distance - 10)) {
            point = level.point - 10;
        } else if (distance > (level.distance + 10)) {
            point = level.point + 10;
        }

        if (this.initialized.get()) {
            initBlockPosition();
        }
    }

    /**
     * Nach Setzen eines Levels werden die Settings des Levels ??bernommen und ein
     * zuf??lliger Song mit passendem Level ausgew??hlt
     * 
     * @param level zu setzendes Level
     */
    public void setLevel(Level level) {
        this.level = level;
        setBlockDistance(level.distance);
        setSpeedFactor(level.speedFactor);
        point = level.point;

        List<Song> matchingSongs = songManager.getSongsByLevel(level);
        int random = (int) (Math.random() * matchingSongs.size());
        setSong(matchingSongs.get(random));
    }

    /**
     * Wenn der neue Song nicht leer ist, wird die Erzeugung des Spielfelds
     * angesto??en
     * 
     * @param song zu setzender Song
     */
    public void setSong(Song song) {
        this.song = song;
        if (song != null) {

            // Abgleichen, damit Level sicher gesetzt ist
            this.level = song.getLevel();
            this.distance = level.distance;
            this.speedFactor = level.speedFactor;
            this.point = level.point;

            initBlocks(song);
        }
    }

    /**
     * Setzen eines Songs ??ber den Songnamen Spielfeld zur??cksetzen
     * 
     * @param name Songname
     */
    public void setSongByName(String name) {
        Song song = songManager.getSongByName(name);
        if (song != null && !song.equals(this.song)) {
            this.song = song;
            this.level = song.getLevel();
            this.distance = level.distance;
            this.speedFactor = level.speedFactor;
            this.point = level.point;
            restart();
        }
    }

    /**
     * Kalkulation des Spielfelds
     * 
     * @param song Song, aus dem ein Spielfeld erzeug werden soll
     */
    public void initBlocks(Song song) {

        // Startblock
        blocks.addFirst(new Block(true));

        // Alle T??ne
        blocks.addAll(songManager.convertToBlocks(song.getTones()));

        // Endblock
        blocks.addLast(new Block(true));

        initBlockPosition();
        initialized.set(true);
    }

    public void initBlockPosition() {
        double x = 0;
        for (Block block : blocks) {
            block.setPosY(height - block.getHeight());
            block.setPosX(x);
            x += block.getWidth() + distance;
        }
        playerMaxX = blocks.getLast().getPosX() + blocks.getLast().getWidth();
    }

    public void start() {
        running = true;
    }

    public void restart() {
        setScore(0);
        player.setOnStartPosition();

        initialized.set(false);
        ended.set(false);

        running = false;

        blocks.clear();
        initBlocks(song);
    }

    public void end() {
        if (score.get() != 0)
            saveScore();
        ended.set(true);
        running = false;
    }

    public void close() {
        running = false;
    }

    /**
     * Speichert den aktuellen Score ab
     */
    public void saveScore() {
        highscorePersistinator.saveData(new Highscore(song, score.get()));
    }

    public synchronized void setScore(int score) {
        this.score.set(score);
    }

    /**
     * Ermittelt die h??chsten drei Scores f??r den Song Liest daf??r gespeicherte
     * Spielst??nde ein
     * 
     * @return Sublist mit h??chsten drei Scores oder alle bisherigen Scores, wenn
     *         weniger als drei existieren
     */
    public List<Integer> getHighScores() {
        List<Highscore> scores = highscorePersistinator.loadBySong(song);
        List<Integer> sublist = new ArrayList<>();

        Collections.sort(scores);
        Collections.reverse(scores);

        if (scores.size() > 3) {
            for (int i = 0; i < 3; i++) {
                sublist.add(scores.get(i).getScore());
            }
        } else {
            for (Highscore h : scores) {
                sublist.add(h.getScore());
            }
        }
        return sublist;
    }

    public void movePlayerLeft(Boolean move) {
        player.setOnMove(move);
        player.setLanded(false);
        player.setOnRight(false);
        player.setOnLeft(move);
    }

    public void movePlayerRight(Boolean move) {
        player.setOnMove(move);
        player.setLanded(false);
        player.setOnLeft(false);
        player.setOnRight(move);
    }

    /**
     * Simulation eines Sprungs
     */
    public void playerJump() {
        if (player.vFalling(0.0, false) == 0) {
            player.posY -= 10;
            if (player.boostProperty().get()) {
                player.vFalling(JUMP_FORCE * (BOOST_MULTI), true);
            } else {
                player.vFalling(JUMP_FORCE, true);
            }
        }
    }

    /**
     * Kalkuliert die Y Position des Spielers
     */
    public void playerYCalculation() {
        if (checkBlockUnderPlayer()) {
            player.vFalling(0, true);
            player.setOnJump(false);
            player.setOnDrop(false);
            player.setLanded(false);
            if (player.vFalling(0, false) > 0) {
                player.posY -= (player.vFalling(0, false) / FPS) * speedFactor;
            }
        } else {
            player.setOnDrop(true);
            player.vFalling(player.vFalling(0, false) + G_FORCE / FPS, true);
            if (!checkPlayerLanding())
                player.posY -= (player.vFalling(0, false) / FPS) * speedFactor;
            if (player.posY > falldepthGameOver) {
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
            if (player.posY + 100 == block.getPosY() && player.posX + 58 > block.getPosX()
                    && player.posX + 24 < block.getPosX() + block.getWidth()) {
                return true;
            }
        }
        return false;
    }

    /**
     * ??berpr??fung, ob Player auf einem Block ladet Berechnung der neuen Punktzahl
     * Block bei Landung auf intersected setzen und Ton des Blocks abspielen
     * 
     * @return true, wenn ein ein Block ber??hrt wird, false wenn nicht
     */
    private boolean checkPlayerLanding() {
        for (Block block : blocks) {
            if (player.posX + 58 > block.getPosX() && player.posX + 24 < block.getPosX() + block.getWidth()) {
                if (player.posY + 100 < block.getPosY()
                        && player.posY + 100 - (player.vFalling(0, false) / FPS) * speedFactor > block.getPosY()) {
                    player.posY = block.getPosY() - 100;
                    player.vFalling(0, true);
                    player.setLanded(false);
                    player.setOnDrop(false);
                    player.setOnJump(false);

                    if (!block.equals(blocks.getFirst())) {
                        if (block.isIntersected().get()) {
                            setScore(getScore() - 10);
                        } else {
                            setScore(getScore() + point);
                        }
                    }
                    block.isIntersected().set(true);
                    if (block.equals(blocks.getLast())) {
                        end();
                    }
                    if (!block.equals(blocks.getFirst()) && !block.equals(blocks.getLast())) {
                        tonemaker.createTone(block.getTone());
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Berechnung der neuen Koordinaten des Spielers
     */
    public void activateMovement() {
        if (running) {
            playerYCalculation();
            if (player.leftProperty().get()) {
                player.posX = ((player.posX - speedFactor * 10) < playerMinX) ? player.posX
                        : (player.posX - speedFactor * 10);
            }
            if (player.rightProperty().get()) {
                player.posX = ((player.posX + speedFactor * 10) > playerMaxX) ? player.posX
                        : (player.posX + speedFactor * 10);
            }

            player.moveTo(player.getPosX(), player.getPosY());
        }
    }

}