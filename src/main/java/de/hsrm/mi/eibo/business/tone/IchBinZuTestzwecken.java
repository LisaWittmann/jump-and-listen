package de.hsrm.mi.eibo.business.tone;

import de.hsrm.mi.eibo.tone.Tone;

import javax.sound.sampled.LineUnavailableException;
import java.util.LinkedList;
import java.util.List;

public class IchBinZuTestzwecken {
    static ToneMaker tm = new ToneMaker();

    public static void main(String[] args){
        tm.setVolume(100);
        List<Tone> toene = new LinkedList<>();
        Song freudeSchoenerGoetterfunken = new Song("e e f g g f e d c c d e e d d e e f g g f e d c c d e d c c d d e c d e f e c d e f e d c d e e e f g g f e d c c d e d c c");
        tm.playList(freudeSchoenerGoetterfunken.getTones());
    }

}