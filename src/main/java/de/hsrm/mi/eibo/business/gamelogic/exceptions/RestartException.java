package de.hsrm.mi.eibo.business.gamelogic.exceptions;

public class RestartException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private String message;

    public RestartException(){
        message = "";
    }
    
    public RestartException(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
    
}