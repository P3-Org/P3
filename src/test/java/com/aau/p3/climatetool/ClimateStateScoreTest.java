package com.aau.p3.climatetool;

import com.aau.p3.climatetool.risk.CloudburstRisk;
import com.aau.p3.climatetool.risk.CoastalErosionRisk;
import com.aau.p3.climatetool.risk.GroundwaterRisk;
import com.aau.p3.climatetool.risk.StormSurgeRisk;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.ArrayList;
import java.util.List;

import static com.aau.p3.climatetool.ClimateStateScore.computeOverallClimateScore;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Test that checks whether the logic of computeOverallClimateScore returns the expected value
 */
class ClimateStateScoreTest {
    List<RiskAssessment> riskList = new ArrayList<>();

    @BeforeEach
    void setup() {
        // Arrange: Create RiskAssessments for each risk with mockito and add them to riskList.
        RiskAssessment cloudBurst = Mockito.mock(CloudburstRisk.class);
        RiskAssessment coastalErosion = Mockito.mock(CoastalErosionRisk.class);
        RiskAssessment groundWater = Mockito.mock(GroundwaterRisk.class);
        RiskAssessment stormSurge = Mockito.mock(StormSurgeRisk.class);
        riskList.add(cloudBurst);
        riskList.add(coastalErosion);
        riskList.add(groundWater);
        riskList.add(stormSurge);
    }

    @Test
    void computeOverallClimateScoreTestTwo() {
        // Arrange: Give fixed values for each risk
        when(riskList.get(0).getNormalizedValue()).thenReturn(1.2);
        when(riskList.get(1).getNormalizedValue()).thenReturn(0.6);
        when(riskList.get(2).getNormalizedValue()).thenReturn(-0.1);
        when(riskList.get(3).getNormalizedValue()).thenReturn(1.0);
        int expectedScore = 2;

        // Act: Compute the climate score from the riskList.
        int resultScore = computeOverallClimateScore(riskList);

        // Assert: Does it calculate the expected climate score.
        assertEquals(expectedScore, resultScore);
    }

    @Test
    void computeOverallClimateScoreTestFive() {
        // Arrange: Give fixed values for each risk
        when(riskList.get(0).getNormalizedValue()).thenReturn(2.2);
        when(riskList.get(1).getNormalizedValue()).thenReturn(1.6);
        when(riskList.get(2).getNormalizedValue()).thenReturn(1.1);
        when(riskList.get(3).getNormalizedValue()).thenReturn(5.0);
        int expectedScore = 5;

        // Act: Compute the climate score from the riskList.
        int resultScore = computeOverallClimateScore(riskList);

        // Assert: Does it calculate the expected climate score.
        assertEquals(expectedScore, resultScore);
    }

    @Test
    void computeOverallClimateScoreTestOne() {
        // Arrange: Give fixed values for each risk
        when(riskList.get(0).getNormalizedValue()).thenReturn(-0.5);
        when(riskList.get(1).getNormalizedValue()).thenReturn(-0.5);
        when(riskList.get(2).getNormalizedValue()).thenReturn(-0.5);
        when(riskList.get(3).getNormalizedValue()).thenReturn(-5.0);
        int expectedScore = 1;

        // Act: Compute the climate score from the riskList.
        int resultScore = computeOverallClimateScore(riskList);

        // Assert: Does it calculate the expected climate score.
        assertEquals(expectedScore, resultScore);
    }

}