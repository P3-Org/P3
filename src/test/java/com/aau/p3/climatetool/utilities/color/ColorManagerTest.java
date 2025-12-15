package com.aau.p3.climatetool.utilities.color;

import com.aau.p3.climatetool.utilities.NormalizeSample;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test to check whether the getRGBValues returns the correct result
 */
class ColorManagerTest {
    private double[] threshold;

    // Set up threshold array before each test.
    @BeforeEach
    void setup(){
         threshold = new double[] { 10, 30 };
    }

    @Test
    void getRGBValuesTestLowerThreshold() {
        // Arrange: Initialize values.
        double measurementValue = 10;
        double[] expectedRGBValue = new double[] {250, 50, 50};
        double normalizedValue = NormalizeSample.minMaxNormalization(measurementValue, threshold);

        // Act: Perform the getRGBValues on the normalizedValue.
        double[] response = ColorManager.getRGBValues(normalizedValue);

        // Assert: Check whether the result matches the expected.
        Assertions.assertArrayEquals(expectedRGBValue, response);
    }

    @Test
    void getRGBValuesTestHigherThreshold() {
        // Arrange: Initialize values.
        double measurementValue = 30;
        double[] threshold = new double[] { 10, 30 };
        double[] expectedRGBValue = new double[] {50, 250, 50};
        double normalizedValue = NormalizeSample.minMaxNormalization(measurementValue, threshold);

        // Act: Perform the getRGBValues on the normalizedValue.
        double[] response = ColorManager.getRGBValues(normalizedValue);

        // Assert: Check whether the result matches the expected.
        Assertions.assertArrayEquals(expectedRGBValue, response);
    }

    @Test
    void getRGBValuesTestOutOfBounds() {
        // Arrange: Initialize values.
        double measurementValue = 35;
        double[] threshold = new double[] { 10, 30 };
        double[] expectedRGBValue = new double[] {50, 130, 50};
        double normalizedValue = NormalizeSample.minMaxNormalization(measurementValue, threshold);

        // Act: Perform the getRGBValues on the normalizedValue.
        double[] response = ColorManager.getRGBValues(normalizedValue);

        // Assert: Check whether the result matches the expected.
        Assertions.assertArrayEquals(expectedRGBValue, response);
    }
}