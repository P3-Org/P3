package com.aau.p3.climatetool.utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RiskColorGradientTest {
    double processedMockValue = 20;
    double[] thresholdValues = {20, 40};
    double[] expectedArray = {255,255,0};

    @Test
    void assignColors() {
        double[] calculatedRGB = RiskColorGradient.assignColors(processedMockValue, thresholdValues);
        Assertions.assertArrayEquals(expectedArray, calculatedRGB);
    }
}