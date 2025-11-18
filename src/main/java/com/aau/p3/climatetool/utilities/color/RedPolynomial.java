package com.aau.p3.climatetool.utilities.color;

public class RedPolynomial implements PolynomialInterface {
    private final double normalizedMeasurement;

    public RedPolynomial(double normalizedMeasurement) {
        this.normalizedMeasurement = normalizedMeasurement;
    }

    /**
     * Calculates the RED value from RGB of the normalized measurement
     * @return the RED value
     */
    @Override
    public double getColorValue() {
        // If the measurement is under 25% below lower threshold
        if (normalizedMeasurement < -0.25) {
            return 130;
        }
        // If the measurement is under the lower threshold
        if (normalizedMeasurement < 0) {
            return 250 + 480 * normalizedMeasurement;
        }
        // If the measurement is lower or right in the middle the two thresholds
        if (normalizedMeasurement <= 0.5) {
            return 250;
        }
        // If the measurement is over the middle of the thresholds and under the upper threshold
        else if (normalizedMeasurement > 0.5 && normalizedMeasurement < 1) {
            return 560 * Math.pow((normalizedMeasurement - 0.5), 2) - 680 * (normalizedMeasurement - 0.5) + 250;
        }
        // If the measurement is over the upper threshold
        else {
            return 50;
        }
    }
}