package com.aau.p3.climatetool.utilities.color;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for checking whether the color values of red match the expected.
 */
class RedPolynomialTest {

    @Test
    void getColorValueLowerThreshold() {
        // Arrange: Initialize normalized threshold and expected value.
        double lowerNormalizedThreshold = 0;
        double expectedValue = 250;

        // Act: Create object and getColorValue is performed.
        PolynomialInterface red = new RedPolynomial(lowerNormalizedThreshold);
        double actualValue = red.getColorValue();

        // Assert: Is the expected value identical to the actual value.
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void getColorValueHigherThreshold() {
        // Arrange: Initialize normalized threshold and expected value.
        double higherNormalizedThreshold = 1;
        double expectedValue = 50;

        // Act: Create object and getColorValue is performed.
        PolynomialInterface red = new RedPolynomial(higherNormalizedThreshold);
        double actualValue = red.getColorValue();

        // Assert: Is the expected value identical to the actual value.
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void getColorValueOne() {
        // Arrange: Initialize normalized threshold and expected value.
        double normalizedThreshold = 0.75;
        double expectedValue = 183;

        // Act: Create object and getColorValue is performed.
        PolynomialInterface red = new RedPolynomial(normalizedThreshold);
        double actualValue = red.getColorValue();

        // Assert: Is the expected value identical to the actual value.
        Assertions.assertEquals(expectedValue, actualValue, 5); // Delta value of 5, as the RGB value for the normalized measured value can vary with about 5
    }

    @Test
    void getColorValueTwo() {
        // Arrange: Initialize normalized threshold and expected value.
        double normalizedThreshold = 0.61;
        double expectedValue = 230;

        // Act: Create object and getColorValue is performed.
        PolynomialInterface red = new RedPolynomial(normalizedThreshold);
        double actualValue = red.getColorValue();

        // Assert: Is the expected value identical to the actual value.
        Assertions.assertEquals(expectedValue, actualValue, 5); // Delta value of 5, as the RGB value for the normalized measured value can vary with about 5
    }

    @Test
    void getColorValueOutOfBounds() {
        // Arrange: Initialize normalized threshold and expected value.
        double higherNormalizedThreshold = -1;
        double expectedValue = 130;

        // Act: Create object and getColorValue is performed.
        PolynomialInterface red = new RedPolynomial(higherNormalizedThreshold);
        double actualValue = red.getColorValue();

        // Assert: Is the expected value identical to the actual value.
        Assertions.assertEquals(expectedValue, actualValue);
    }
}