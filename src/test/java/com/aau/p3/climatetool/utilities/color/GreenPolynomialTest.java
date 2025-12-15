package com.aau.p3.climatetool.utilities.color;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for checking whether the color values of red match the expected.
 */
class GreenPolynomialTest {
    @Test
    void getColorValueLowerThreshold() {
        // Arrange: Initialize normalized threshold and expected value.
        double lowerMeasurement = 0;
        double expectedResult = 50;

        // Act: Create object and getColorValue is performed.
        PolynomialInterface green = new GreenPolynomial(lowerMeasurement);
        double actualValue = green.getColorValue();

        // Assert: Is the expected value identical to the actual value.
        assertEquals(expectedResult, actualValue);
    }

    @Test
    void getColorValueHigherThreshold() {
        // Arrange: Initialize normalized threshold and expected value.
        double higherMeasurement = 1;
        double expectedResult = 250;

        // Act: Create object and getColorValue is performed.
        PolynomialInterface green = new GreenPolynomial(higherMeasurement);
        double actualValue = green.getColorValue();

        // Assert: Is the expected value identical to the actual value.
        assertEquals(expectedResult, actualValue);
    }

    @Test
    void getColorValueOne() {
        // Arrange: Initialize normalized threshold and expected value.
        double normalizedThreshold = 0.21;
        double expectedValue = 170;

        // Act: Create object and getColorValue is performed.
        PolynomialInterface green = new GreenPolynomial(normalizedThreshold);
        double actualValue = green.getColorValue();

        // Assert: Is the expected value identical to the actual value.
        Assertions.assertEquals(expectedValue, actualValue, 5); // Delta value of 5, as the RGB value for the normalized measured value can vary with about 5
    }

    @Test
    void getColorValueTwo() {
        // Arrange: Initialize normalized threshold and expected value.
        double normalizedThreshold = 0.4;
        double expectedValue = 231;

        // Act: Create object and getColorValue is performed.
        PolynomialInterface green = new GreenPolynomial(normalizedThreshold);
        double actualValue = green.getColorValue();

        // Assert: Is the expected value identical to the actual value.
        Assertions.assertEquals(expectedValue, actualValue, 5); // Delta value of 5, as the RGB value for the normalized measured value can vary with about 5
    }


    @Test
    void getColorValueOutOfBounds() {
        // Arrange: Initialize normalized threshold and expected value.
        double outOfBoundsMeasurement = 2;
        double expectedResult = 130;

        // Act: Create object and getColorValue is performed.
        PolynomialInterface green = new GreenPolynomial(outOfBoundsMeasurement);
        double actualValue = green.getColorValue();

        // Assert
        assertEquals(expectedResult, actualValue);
    }
}