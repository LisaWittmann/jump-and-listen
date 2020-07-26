package de.hsrm.mi.eibo.presentation.application;

public enum Theme {
    LIGHT, DARK;

    private String url;

    private Theme() {
        url = getClass().getResource("/stylesheets/"+ name().toLowerCase() +"Theme.css").toExternalForm();
    }

    public String getUrl() {
        return url;
    }
    
}