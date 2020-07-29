package de.hsrm.mi.eibo.business.tone;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.util.List;

public class ToneMaker {

    private int volume;

    public ToneMaker() {
        volume = 80;
    }



    public void createTone(int Hertz, int volume)
            throws LineUnavailableException {

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

    public void createTone(Tone tone) {
        Runnable runnable = () -> {
            try {
                createTone(tone.getFrequenz(), volume);
            } catch (LineUnavailableException e) {
                System.out.println(e.getMessage());
            }
        };
        Thread tut = new Thread(runnable);
        tut.start();


    }

    public void fallingTone() {
        Runnable runnable = () -> {
            try {
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
                int startHertz = 450;
                double hertz = startHertz;
                for (int i = 0; i <rate * 1; i++) {
                    hertz -= (startHertz - 300) / (rate * 1);
                    double angle = (i / rate) * hertz * 2.0 * Math.PI;
                    buf[0] = (byte) (Math.sin(angle) * volume);
                    sourceDL.write(buf, 0, 1);
                }

                sourceDL.drain();
                sourceDL.stop();
                sourceDL.close();
            } catch (LineUnavailableException e) {
                System.out.println(e.getMessage());
            }
        };
        Thread tut = new Thread(runnable);
        tut.start();
    }

    public void playList(List<Tone> tones) {
        for (Tone tone : tones) {
            try {
                createTone(tone.getFrequenz(), volume);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        if (volume < 0 || volume > 100) throw new IllegalArgumentException("Volume has to be between 0 and 100 but was " + volume);
        this.volume = volume;
    }
}
