package com.aau.p3.climatetool.utilities.color;

import com.aau.p3.climatetool.utilities.NormalizeSample;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ColorManagerTest {
    private double[] threshold;

    @BeforeEach
    void setUp(){
         threshold = new double[] { 10, 30 };
    }

    @Test
    void getRGBValuesTestLowerThreshold() {
        // Arrange
        double measurementValue = 10;
        double[] expectedRGBValue = new double[] {250, 50, 50};
        double normalizedValue = NormalizeSample.minMaxNormalization(measurementValue, threshold);

        // Act
        double[] response = ColorManager.getRGBValues(normalizedValue);

        // Assert
        Assertions.assertArrayEquals(expectedRGBValue, response);
    }

    @Test
    void getRGBValuesTestHigherThreshold() {
        // Arrange
        double measurementValue = 30;
        double[] threshold = new double[] { 10, 30 };
        double[] expectedRGBValue = new double[] {50, 250, 50};
        double normalizedValue = NormalizeSample.minMaxNormalization(measurementValue, threshold);

        // Act
        double[] response = ColorManager.getRGBValues(normalizedValue);

        // Assert
        Assertions.assertArrayEquals(expectedRGBValue, response);
    }

    @Test
    void getRGBValuesTestOutOfBounds() {
        // Arrange
        double measurementValue = 35;
        double[] threshold = new double[] { 10, 30 };
        double[] expectedRGBValue = new double[] {50, 130, 50};
        double normalizedValue = NormalizeSample.minMaxNormalization(measurementValue, threshold);

        // Act
        double[] response = ColorManager.getRGBValues(normalizedValue);

        // Assert
        Assertions.assertArrayEquals(expectedRGBValue, response);
    }
}