package de.hsrm.mi.eibo.business.tone;

/**
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public enum Tone {
    /*
    C(264),
    D(297),
    E(330),
    F(352),
    G(396),
    A(440),
    H(495);
    */
    C(262),
    CS(277),
    D(294),
    DS(311),
    E(330),
    F(349),
    FS(370),
    G(392),
    GS(415),
    A(440),
    AS(466),
    H(494);

    private int freq;
    private Tone(int freq) {
        this.freq = freq;
    }

    public int getFrequenz() {
        return freq;
    }

    public int getHigh() {
        return freq*2;
    }

    public int getLow() {
        return (int) freq/2;
    }


}
