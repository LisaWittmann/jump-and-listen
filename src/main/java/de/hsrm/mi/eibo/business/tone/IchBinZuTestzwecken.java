package de.hsrm.mi.eibo.business.tone;

import de.hsrm.mi.eibo.business.gamelogic.Level;
import de.hsrm.mi.eibo.persistence.SongPersitinator;

public class IchBinZuTestzwecken {
    static ToneMaker tm = new ToneMaker();
    static SongPersitinator songPers = new SongPersitinator();

    public static void main(String[] args){
        tm.setVolume(100);
        Song freudeSchoenerGoetterfunken = new Song(Level.BEGINNER, "e e f g g f e d c c d e e d d e e f g g f e d c c d e d c c d d e c d e f e c d e f e d c d e e e f g g f e d c c d e d c c");
        songPers.saveData(freudeSchoenerGoetterfunken);
    }

}