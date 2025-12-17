package com.aau.p3.climatetool.utilities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * NormalizeMaxMinBoundaries tests that the normalized boundaries we set for the sliders
 * correspond to the boundaries we would have using normal values.
 */
class NormalizeMaxMinBoundaries {
    double[] mockThreshold = {10,30};

    @Test
    void testUpperLimit() {
        // Arrange: Initialize expected value and upper limit.
        double expected = 1.5;
        double upperlimit = 40;

        // Act: Perform minMaxNormalization on the limit and mockThreshold.
        double maxLimit = NormalizeSample.minMaxNormalization(upperlimit, mockThreshold);

        // Assert:
        assertEquals(expected, maxLimit);
    }

    @Test
    void testLowerLimit() {
        // Arrange: Initialize expected value and lower limit.
        double expected = -0.5;
        double lowerlimit = 0;

        // Act: Perform minMaxNormalization on the limit and mockThreshold.
        double minLimit = NormalizeSample.minMaxNormalization(lowerlimit, mockThreshold);

        // Assert
        assertEquals(expected, minLimit);
    }
}