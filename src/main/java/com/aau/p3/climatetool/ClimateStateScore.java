package com.aau.p3.climatetool;

import com.aau.p3.climatetool.utilities.RiskAssessment;
import java.util.List;

/**
 * Class that computes the overall climate score. Starts from 5 and counts down according to different hydrological
 * ratings and their severity.
 */
public class ClimateStateScore {

    /**
     * Method for constructing the different calls necessary to gather sample values from a property within a grid.
     * @param riskAssessment The list of risk assessments for a property.
     * @return The overall climate score as an integer from 1-5
     */
    public static int computeOverallClimateScore(List<RiskAssessment> riskAssessment) {
        // The starting point for each property
        int overallClimateScore = 5;

        // For each risk, subtract either 2 if it is red or 1 if it is yellow.
        for (RiskAssessment risks : riskAssessment) {
            if (risks.getNormalizedValue() <= 0) { // Below the min threshold thus, in the red zone.
                overallClimateScore -= 2;
            } else if (risks.getNormalizedValue() < 1) { // Above the min threshold and below the max threshold thus, in the yellow zone.
                overallClimateScore -= 1;
            }
        }

        // Fail-safe making sure the score can't fall below 1
        if (overallClimateScore < 1) {
            overallClimateScore = 1;
        }

        return overallClimateScore;
    }
}