package com.aau.p3.climatetool.utilities.color;

/**
 * Class for the green polynomial
 */
public class GreenPolynomial implements PolynomialInterface {
    private final double normalizedMeasurement;

    public GreenPolynomial(double normalizedMeasurement) {
        this.normalizedMeasurement = normalizedMeasurement;
    }

    /**
     * Calculates the GREEN value from RGB of the normalized measurement
     * @return the GREEN value
     */
    @Override
    public double getColorValue() {
        // If the measurement is more than 25% over the upper threshold
        if (normalizedMeasurement > 1.25) {
            return 130;
        }

        // If the measurement is over the threshold
        if (normalizedMeasurement > 1) {
            return 250 - 480 * (normalizedMeasurement - 1);
        }

        // If the measurement is under the lower threshold
        if(normalizedMeasurement < 0) {
            return 50;
        }

        // If the measurement is over the middle of the two thresholds
        else if (normalizedMeasurement > 0.5) {
            return 250;
        }

        // If the measurement is between the lower threshold and the middle of the two thresholds
        else {
         return -560 * Math.pow(normalizedMeasurement, 2) + 680 * normalizedMeasurement + 50;
        }
    }
}
