package de.hsrm.mi.eibo.business.tone;

/**
 * Wird geworfen, wenn der eingegebene Name zur Abspeicherung eines Songs
 * ungültig ist
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public class NameException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NameException() {
        super("Name ungueltig");
    }
}