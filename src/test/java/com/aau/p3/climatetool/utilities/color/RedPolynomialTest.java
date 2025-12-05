package com.aau.p3.climatetool.utilities.color;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RedPolynomialTest {

    @Test
    void getColorValueLowerThreshold() {
        // Arrange
        double lowerNormalizedThreshold = 0;
        double expectedValue = 250;

        // Act
        PolynomialInterface red = new RedPolynomial(lowerNormalizedThreshold);

        // Assert
        Assertions.assertEquals(expectedValue, red.getColorValue());
    }

    @Test
    void getColorValueHigherThreshold() {
        // Arrange
        double higherNormalizedThreshold = 1;
        double expectedValue = 50;

        // Act
        PolynomialInterface red = new RedPolynomial(higherNormalizedThreshold);

        // Assert
        Assertions.assertEquals(expectedValue, red.getColorValue());
    }

    @Test
    void getColorValueOne() {
        // Arrange
        double normalizedThreshold = 0.75;
        double expectedValue = 183;

        // Act
        PolynomialInterface red = new RedPolynomial(normalizedThreshold);

        // Assert
        Assertions.assertEquals(expectedValue, red.getColorValue(), 5); // Delta value of 5, as the RGB value for the normalized measured value can vary with about 5
    }

    @Test
    void getColorValueTwo() {
        // Arrange
        double normalizedThreshold = 0.61;
        double expectedValue = 230;

        // Act
        PolynomialInterface red = new RedPolynomial(normalizedThreshold);

        // Assert
        Assertions.assertEquals(expectedValue, red.getColorValue(), 5); // Delta value of 5, as the RGB value for the normalized measured value can vary with about 5
    }

    @Test
    void getColorValueOutOfBounds() {
        // Arrange
        double higherNormalizedThreshold = -1;
        double expectedValue = 130;

        // Act
        PolynomialInterface red = new RedPolynomial(higherNormalizedThreshold);

        // Assert
        Assertions.assertEquals(expectedValue, red.getColorValue());
    }
}