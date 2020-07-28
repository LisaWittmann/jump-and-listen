package de.hsrm.mi.eibo.business.tone;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.eibo.business.gamelogic.Level;
import de.hsrm.mi.eibo.persistence.SongPersitinator;

public class IchBinZuTestzwecken {
    static ToneMaker tm = new ToneMaker();
    static SongPersitinator songPers = new SongPersitinator();

    public static void main(String[] args){
        List<Song> songs = new ArrayList<Song>();
        tm.setVolume(100);
        Song freudeSchoenerGoetterfunken = new Song(Level.INTERMEDIATE, "e e f g g f e d c c d e e d d e e f g g f e d c c d e d c c d d e c d e f e c d e f e d c d e e e f g g f e d c c d e d c c");
        Song alleMeineEntchen = new Song(Level.BEGINNER, "c d e f g g a a a a g a a a a g f f f f e e d d d d c");
        songs.add(freudeSchoenerGoetterfunken);
        songs.add(alleMeineEntchen);
        songPers.saveData(songs);
    }

}