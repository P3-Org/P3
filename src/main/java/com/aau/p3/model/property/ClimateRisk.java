package com.aau.p3.model.property;

public class ClimateRisk {
    private int score;

    public void ClimateRisk(int score){
        this.score = score;
    }

    public int computeScore() {
        return this.score;
    }

    public int getScore(){return this.score; }
}
