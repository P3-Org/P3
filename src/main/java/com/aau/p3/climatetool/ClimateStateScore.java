package com.aau.p3.climatetool;

public class ClimateStateScore {
    private int score;

    public void ClimateRisk(int score){
        this.score = score;
    }

    public int computeScore() {
        return this.score;
    }

    public int getScore(){return this.score; }
}
