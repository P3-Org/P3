package com.aau.p3.climatetool.utilities.color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BluePolynomialTest {
    double expectedResult;
    @BeforeEach
    void setup(){
        expectedResult = 50;
    }

    @Test
    void getColorValueLowerThreshold() {
        // Arrange
        double lowerMeasurement = 0;
        // Act
        PolynomialInterface blue = new BluePolynomial(lowerMeasurement);
        // Assert
        assertEquals(expectedResult, blue.getColorValue());
    }

    @Test
    void getColorValueHigherThreshold() {
        // Arrange
        double higherMeasurement = 1;
        // Act
        PolynomialInterface blue = new BluePolynomial(higherMeasurement);
        // Assert
        assertEquals(expectedResult, blue.getColorValue());
    }

    @Test
    void getColorValueOutOfBounds() {
        // Arrange
        double outOfBoundsMeasurement = 2;
        // Act
        PolynomialInterface blue = new BluePolynomial(outOfBoundsMeasurement);
        // Assert
        assertEquals(expectedResult, blue.getColorValue());
    }
}