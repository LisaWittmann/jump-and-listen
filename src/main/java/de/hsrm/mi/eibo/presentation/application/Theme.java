package de.hsrm.mi.eibo.presentation.application;

/**
 * Theme mit zugehörigem Stylesheet
 * 
 * @author pwieg001, lwitt001, lgers001
 */
public enum Theme {

    LIGHT, DARK;

    private String stylesheet;

    private Theme() {
        stylesheet = getClass().getResource("/stylesheets/" + name().toLowerCase() + "Theme.css").toExternalForm();
    }

    public String getStylesheet() {
        return stylesheet;
    }

    public String toString() {
        return name().toLowerCase();
    }

}