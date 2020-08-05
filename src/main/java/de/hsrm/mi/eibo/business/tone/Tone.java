package de.hsrm.mi.eibo.business.tone;

/**
 * Definierte Töne mit Frequenz
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public enum Tone {
    C(262), CS(277), D(294), DS(311), E(330), ES(339), F(349), FS(370), G(392), GS(415), A(440), AS(466), H(494);

    private int freq;

    private Tone(int freq) {
        this.freq = freq;
    }

    public int getFrequenz() {
        return freq;
    }

    public int getHigh() {
        return freq * 2;
    }

    public int getLow() {
        return (int) freq / 2;
    }

    public boolean isHalbton() {
        if (this.name().length() > 1)
            return true;
        else
            return false;
    }

}
