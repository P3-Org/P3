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