package de.hsrm.mi.eibo.tone;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.util.List;

public class ToneMaker {



    public void createTone(int Hertz, int volume)
            throws LineUnavailableException {
        /** Exception is thrown when line cannot be opened */

        float rate = 44100;
        byte[] buf;
        AudioFormat audioF;

        buf = new byte[1];
        audioF = new AudioFormat(rate,8,1,true,false);
        //sampleRate, sampleSizeInBits,channels,signed,bigEndian

        SourceDataLine sourceDL = AudioSystem.getSourceDataLine(audioF);
        sourceDL = AudioSystem.getSourceDataLine(audioF);
        sourceDL.open(audioF);
        sourceDL.start();

        for(int i=0; i<rate/3; i++){
            double angle = (i/rate)*Hertz*2.0*Math.PI;
            buf[0]=(byte)(Math.sin(angle)*volume);
            sourceDL.write(buf,0,1);
        }

        sourceDL.drain();
        sourceDL.stop();
        sourceDL.close();
    }

    public void playList(List<Tone> tones) {
        for (Tone tone : tones) {
            try {
                createTone(tone.getFrequenz(), 100);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

}
