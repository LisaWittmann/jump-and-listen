package de.hsrm.mi.eibo.business.gamelogic;

public enum Level {

    BEGINNER(30, 0.7, 80), INTERMEDIATE(50, 1.0, 100), EXPERT(80, 1.5, 120);

    protected int point;
    protected double speedFactor;
    protected double distance;
    
    private Level(int point, double speedFactor, double distance) {
        this.point = point;
        this.speedFactor = speedFactor;
        this.distance = distance;
    }
    
    @Override 
    public String toString(){
        return name().toLowerCase();
    }
    
    public Level getNextLevel() {
        switch(this) {
            case BEGINNER: return INTERMEDIATE;
            case INTERMEDIATE: return EXPERT;
            case EXPERT: return EXPERT;
            default: return BEGINNER;
        }
    }
}