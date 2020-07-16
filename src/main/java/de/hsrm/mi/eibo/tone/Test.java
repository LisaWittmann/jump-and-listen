package de.hsrm.mi.eibo.tone;

import de.hsrm.mi.eibo.tone.Tone;
import de.hsrm.mi.eibo.tone.ToneMaker;

import javax.sound.sampled.LineUnavailableException;
import java.util.LinkedList;
import java.util.List;

public class Test{

    final static int VOL = 100;
    static ToneMaker tm = new ToneMaker();

    public static void main(String[] args){
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
