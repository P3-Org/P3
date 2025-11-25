package com.aau.p3.climatetool.utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/***
 * NormalizeMaxMinBoundaries tests that the normalized boundaries we set for the sliders correspond to the boundaries we would have using normal values.
 */
class NormalizeMaxMinBoundaries {
    double[] mockThreshold = {10,30};

    @Test
    void testUpperLimit(){
        // Arrange
        double expected = 1.5;
        double upperlimit = 40; // the Value we would expect to define the upper limit for normal values

        // Act
        double maxLimit = NormalizeSample.minMaxNormalization(upperlimit, mockThreshold);

        // Assert
        assertEquals(expected, maxLimit);
    }

    @Test
    void testlowerLimit(){
        // Arrange
        double expected = -0.5;
        double lowerlimit = 0; // the Value we would expect to define the lower limit for normal values

        // Act
        double minLimit = NormalizeSample.minMaxNormalization(lowerlimit, mockThreshold);

        // Assert
        assertEquals(expected, minLimit);
    }
}