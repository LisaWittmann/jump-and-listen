package de.hsrm.mi.eibo.business.gamelogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Spieler in Form der Spielfigur im Spiel
 * enthält außerdem Sessiondaten wie alle persönlichen Spielstände
 * 
 * @author pwieg001, lwitt001, lger001
 */
public class Player {

    protected int score;
    protected List<Integer> allScores;

    public Player(){
        score = 0;
        allScores = new ArrayList<>();

        initTestValues();
    }

    public int getScore(){
        return score;
    }
    
    /**
     * Ermittelt die höchsten drei Scores des Spielers
     * @return Sublist mit höchsten drei Scores oder alle bisherigen Scores, wenn weniger als drei Scores existieren
     */
    public List<Integer> getHighScores(){ 
        List<Integer> sublist = new ArrayList<>();

        Collections.sort(allScores);
        Collections.reverse(allScores);

        if(allScores.size() > 3){
            for(int i = 0; i < 3; i++){
                sublist.add(allScores.get(i));
            }
        } else sublist.addAll(allScores);

        return sublist;
    }

    //TODO: Methode entfernen sobald Klasse vollständig ist
    private void initTestValues(){
        score = 8420;
        allScores.add(score);
        allScores.add(5430);
        allScores.add(7630);
        allScores.add(1000);
        allScores.add(670); 
        allScores.add(6300);
    }
    
}