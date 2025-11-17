package com.aau.p3.climatetool.utilities.color;

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