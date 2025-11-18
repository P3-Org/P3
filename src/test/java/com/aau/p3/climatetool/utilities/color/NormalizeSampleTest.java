package com.aau.p3.climatetool.utilities.color;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import static org.junit.jupiter.api.Assertions.*;

class NormalizeSampleTest {
    private double[] threshold;

    @BeforeEach
    void setUp() {
        threshold = new double[] {10, 30};
    }

    @Test
    void minMaxNormalizationLowerBound() {
        // Arrange
        double measurementValue = 10;
        double expectedResult = 0;

        // Act
        double result = NormalizeSample.minMaxNormalization(measurementValue, threshold);

        // Assert
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void minMaxNormalizationUpperBound() {
        // Arrange
        double measurementValue = 30;
        double expectedResult = 1;

        // Act
        double result = NormalizeSample.minMaxNormalization(measurementValue, threshold);

        // Assert
        Assertions.assertEquals(expectedResult, result);
    }
    

    @Test
    void minMaxNormalizationOutOfBounds() {
        // Arrange
        double measurementValue = 35;
        double expectedResult = 1.25;

        // Act
        double restult = NormalizeSample.minMaxNormalization(measurementValue, threshold);

        // Assert
        Assertions.assertEquals(expectedResult, restult);
    }
}