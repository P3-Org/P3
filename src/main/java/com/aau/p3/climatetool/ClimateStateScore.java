package com.aau.p3.climatetool;

import com.aau.p3.climatetool.utilities.RiskAssessment;

import java.util.List;

public class ClimateStateScore {
    public static int computeOverallClimateScore(List<RiskAssessment> riskAssessment) {
        int overallClimateScore = 5;

        for (RiskAssessment risks : riskAssessment) {
            if (risks.getNormalizedValue() <= 0) {
                overallClimateScore -= 2;
            } else if (risks.getNormalizedValue() < 1) {
                overallClimateScore -= 1;
            }
        }

        if (overallClimateScore < 1) {
            overallClimateScore = 1;
        }
        return overallClimateScore;
    }
}