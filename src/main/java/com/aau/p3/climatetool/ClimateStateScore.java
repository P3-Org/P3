package com.aau.p3.climatetool;

import com.aau.p3.climatetool.utilities.RiskAssessment;
import org.geotools.api.referencing.operation.TransformException;

import java.util.List;

/**
 * Class that computes the overall climate score. Starts from 5 and counts down according to different hydrological
 * ratings and their severity.
 * @Author Batman
 */
public class ClimateStateScore {

    /**
     * Method for constructing the different calls necessary to gather sample values from a property within a grid.
     * @param riskAssessment The list of risk assessments for a property
     * @return The overall climate score as an integer from 1-5
     */
    public static int computeOverallClimateScore(List<RiskAssessment> riskAssessment) {
        int overallClimateScore = 5;

        // For each risk, subtract either 2 if it is red or 1 if it is yellow
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