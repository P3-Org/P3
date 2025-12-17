package com.aau.p3.climatetool.utilities.color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for checking whether the color values of blue match the expected.
 */
class BluePolynomialTest {
    double expectedResult;

    // Initialize expectedResult before each test.
    @BeforeEach
    void setup() {
        expectedResult = 50;
    }

    @Test
    void getColorValueLowerThreshold() {
        // Arrange: Initialize lower measurement.
        double lowerMeasurement = 0;

        // Act: Create object and getColorValue is performed.
        PolynomialInterface blue = new BluePolynomial(lowerMeasurement);
        double actualValue = blue.getColorValue();

        // Assert: Is the expected value identical to the actual value.
        assertEquals(expectedResult, actualValue);
    }

    @Test
    void getColorValueHigherThreshold() {
        // Arrange: Initialize higher measurement.
        double higherMeasurement = 1;

        // Act: Create object and getColorValue is performed.
        PolynomialInterface blue = new BluePolynomial(higherMeasurement);
        double actualValue = blue.getColorValue();

        // Assert: Is the expected value identical to the actual value.
        assertEquals(expectedResult, actualValue);
    }

    @Test
    void getColorValueOutOfBounds() {
        // Arrange: Initialize out of bounds measurement
        double outOfBoundsMeasurement = 2;

        // Act: Create object and getColorValue is performed.
        PolynomialInterface blue = new BluePolynomial(outOfBoundsMeasurement);
        double actualValue = blue.getColorValue();

        // Assert: Is the expected value identical to the actual value.
        assertEquals(expectedResult, actualValue);
    }
}