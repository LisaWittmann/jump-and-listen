package de.hsrm.mi.eibo.business.gamelogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public List<Integer> getHighScores(){

        //Die besten 3 Ergebisse an den Anfang der Liste holen
        Collections.sort(allScores);
        Collections.reverse(allScores);

        List<Integer> sublist = new ArrayList<>();

        //Dann die ersten 3 Scores in eine Sublist übertragen
        if(allScores.size() > 3){
            for(int i = 0; i < 3; i++){
                sublist.add(allScores.get(i));
            }
        } 
        //Oder alle übertragen, wenn es nicht mehr als 3 Scores gibt
        else sublist.addAll(allScores);

        return sublist;
    }

    private void initTestValues(){
        //Zu Testzwecken
        score = 8420;

        allScores.add(score);
        allScores.add(5430);
        allScores.add(7630);
        allScores.add(1000);
        allScores.add(670); 
        allScores.add(6300);
    }
    
}