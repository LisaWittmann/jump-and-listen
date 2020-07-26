package de.hsrm.mi.eibo.business.gamelogic;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hsrm.mi.eibo.persistence.HighscorePersistinator;

import javafx.beans.property.SimpleBooleanProperty;

/**
 * Spieler bzw User
 * Schnittstelle für Persistence Schicht zum Speichern und Laden persönlicher Highscores
 * 
 * @author pwieg001, lwitt001, lger001
 */
public class Player {

    private HighscorePersistinator highscorePers;
    protected int score;

    private SimpleBooleanProperty jump, boost, drop;
    protected double posX, posY;

    public PropertyChangeSupport changes;

    public Player(){
        highscorePers = new HighscorePersistinator();
        score = 0;

        jump = new SimpleBooleanProperty(false);
        boost = new SimpleBooleanProperty(false);
        drop = new SimpleBooleanProperty(false);

        changes = new PropertyChangeSupport(this.getClass());
    }

    public SimpleBooleanProperty getJumpProperty() {
        return jump;
    }

    public SimpleBooleanProperty getBoostProperty() {
        return boost;
    }

    public SimpleBooleanProperty getDropProperty() {
        return drop;
    }

    public int getScore() {
        return score;
    }

    public void setJump(boolean jump) {
        this.jump.set(jump);
    }

    public void setBoost(boolean boost) {
        this.boost.set(boost);
    }

    public void setDrop(boolean drop) {
        this.drop.set(drop);
    }

    /**
     * Speichert den aktuellen Score ab und setzt ihn danach wieder auf 0
     */
    public void saveScore() {
        highscorePers.saveData(score);
        score = 0;
    }
    
    /**
     * Ermittelt die höchsten drei Scores des Spielers
     * Liest dafür gespeicherte Spielstände ein
     * @return Sublist mit höchsten drei Scores oder alle bisherigen Scores, wenn weniger als drei Scores existieren
     */
    public List<Integer> getHighScores(){ 
        List<Integer> scores = highscorePers.loadData();
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
    
}