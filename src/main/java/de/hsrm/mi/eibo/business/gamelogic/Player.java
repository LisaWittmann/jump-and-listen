package de.hsrm.mi.eibo.business.gamelogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hsrm.mi.eibo.persistence.HighscorePersistinator;

/**
 * Spieler in Form der Spielfigur im Spiel
 * enthält außerdem Sessiondaten wie alle persönlichen Spielstände
 * 
 * @author pwieg001, lwitt001, lger001
 */
public class Player {

    private HighscorePersistinator highscorePers;

    protected int score;

    public Player(){
        highscorePers = new HighscorePersistinator();
        score = 0;

        initTestValues();
    }

    public int getScore() {
        return score;
    }

    public void saveScore() {
        highscorePers.saveValue(score);
        score = 0;
    }
    
    /**
     * Ermittelt die höchsten drei Scores des Spielers
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

    //TODO: Methode entfernen sobald Klasse vollständig ist
    private void initTestValues(){
        score = 8420;
        
        List<Integer> testList = new ArrayList<>();
        testList.add(score);
        testList.add(5430);
        testList.add(7630);
        testList.add(1000);
        testList.add(670); 
        testList.add(6300);

        highscorePers.saveData(testList);

    }
    
}