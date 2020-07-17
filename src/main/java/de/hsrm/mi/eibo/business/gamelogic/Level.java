package de.hsrm.mi.eibo.business.gamelogic;

import de.hsrm.mi.eibo.business.tone.Song;

public enum Level {

    //TODO: Songs einbinden
    BEGINNER(), INTERMEDIATE(), EXPERT();
    
    private Song [] songs;

    private Level(Song ... songs){
        this.songs = songs;
    }

    public Song [] getSongs(){
        return songs;
    }

    public Song getRandomSong(){
        int index = (int) Math.random() * songs.length;
        return songs[index];
    }

    @Override 
    public String toString(){
        return name().toLowerCase();
    }
    
}