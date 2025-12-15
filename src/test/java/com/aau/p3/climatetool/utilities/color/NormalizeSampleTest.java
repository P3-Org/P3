package com.aau.p3.climatetool.utilities.color;

import com.aau.p3.climatetool.utilities.NormalizeSample;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test that checks whether minMaxNormalization successfully normalizes measurements.
 */
class NormalizeSampleTest {
    private double[] threshold;

    // Initialize thresholds before each test.
    @BeforeEach
    void setup() {
        threshold = new double[] {10, 30};
    }

    @Test
    void minMaxNormalizationLowerBound() {
        // Arrange: Initialize measurement and expected result.
        double measurementValue = 10;
        double expectedResult = 0;

        // Act: Perform minMaxNormalization on the measurement value with the thresholds.
        double result = NormalizeSample.minMaxNormalization(measurementValue, threshold);

        // Assert: Check whether the result matches the expected.
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void minMaxNormalizationUpperBound() {
        // Arrange: Initialize measurement and expected result.
        double measurementValue = 30;
        double expectedResult = 1;

        // Act: Perform minMaxNormalization on the measurement value with the thresholds.
        double result = NormalizeSample.minMaxNormalization(measurementValue, threshold);

        // Assert: Check whether the result matches the expected.
        Assertions.assertEquals(expectedResult, result);
    }
    

    @Test
    void minMaxNormalizationOutOfBounds() {
        // Arrange: Initialize measurement and expected result.
        double measurementValue = 35;
        double expectedResult = 1.25;

        // Act: Perform minMaxNormalization on the measurement value with the thresholds.
        double result = NormalizeSample.minMaxNormalization(measurementValue, threshold);

        // Assert: Check whether the result matches the expected.
        Assertions.assertEquals(expectedResult, result);
    }
}