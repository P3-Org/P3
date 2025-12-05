package com.aau.p3.climatetool.utilities.color;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GreenPolynomialTest {
    @Test
    void getColorValueLowerThreshold() {
        // Arrange
        double lowerMeasurement = 0;
        double expectedResult = 50;

        // Act
        PolynomialInterface green = new GreenPolynomial(lowerMeasurement);

        // Assert
        assertEquals(expectedResult, green.getColorValue());
    }

    @Test
    void getColorValueHigherThreshold() {
        // Arrange
        double higherMeasurement = 1;
        double expectedResult = 250;

        // Act
        PolynomialInterface green = new GreenPolynomial(higherMeasurement);

        // Assert
        assertEquals(expectedResult, green.getColorValue());
    }

    @Test
    void getColorValueOne() {
        // Arrange
        double normalizedThreshold = 0.21;
        double expectedValue = 170;

        // Act
        PolynomialInterface red = new GreenPolynomial(normalizedThreshold);

        // Assert
        Assertions.assertEquals(expectedValue, red.getColorValue(), 5); // Delta value of 5, as the RGB value for the normalized measured value can vary with about 5
    }

    @Test
    void getColorValueTwo() {
        // Arrange
        double normalizedThreshold = 0.4;
        double expectedValue = 231;

        // Act
        PolynomialInterface red = new GreenPolynomial(normalizedThreshold);

        // Assert
        Assertions.assertEquals(expectedValue, red.getColorValue(), 5); // Delta value of 5, as the RGB value for the normalized measured value can vary with about 5
    }


    @Test
    void getColorValueOutOfBounds() {
        // Arrange
        double outOfBoundsMeasurement = 2;
        double expectedResult = 130;

        // Act
        PolynomialInterface green = new GreenPolynomial(outOfBoundsMeasurement);

        // Assert
        assertEquals(expectedResult, green.getColorValue());
    }
}