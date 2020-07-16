package de.hsrm.mi.eibo.business.tone;

import de.hsrm.mi.eibo.business.tone.Tone;

import javax.sound.sampled.LineUnavailableException;
import java.util.LinkedList;
import java.util.List;

public class Test{
    static ToneMaker tm = new ToneMaker();

    public static void main(String[] args){
        tm.setVolume(100);
        List<Tone> toene = new LinkedList<>();
        toene.add(Tone.E);
        toene.add(Tone.E);
        toene.add(Tone.F);
        toene.add(Tone.G);
        toene.add(Tone.G);
        toene.add(Tone.F);
        toene.add(Tone.E);
        toene.add(Tone.D);
        toene.add(Tone.C);
        toene.add(Tone.C);
        toene.add(Tone.D);
        toene.add(Tone.E);
        toene.add(Tone.E);
        toene.add(Tone.D);
        toene.add(Tone.D);

        toene.add(Tone.E);
        toene.add(Tone.E);
        toene.add(Tone.F);
        toene.add(Tone.G);
        toene.add(Tone.G);
        toene.add(Tone.F);
        toene.add(Tone.E);
        toene.add(Tone.D);
        toene.add(Tone.C);
        toene.add(Tone.C);
        toene.add(Tone.D);
        toene.add(Tone.E);
        toene.add(Tone.D);
        toene.add(Tone.C);
        toene.add(Tone.C);

        tm.playList(toene);
    }

    /** parameters are frequency in Hertz and volume
     **/

}
