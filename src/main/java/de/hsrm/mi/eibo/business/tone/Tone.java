package de.hsrm.mi.eibo.tone;

public enum Tone {
    C(264),
    D(297),
    E(330),
    F(352),
    G(396),
    A(440),
    H(495);

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
