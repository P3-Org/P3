package com.aau.p3.climatetool.utilities.color;

public class BluePolynomial implements PolynomialInterface {
    // The measurement is not yet used
    private final double normalizedMeasurement;

    public BluePolynomial(double normalizedMeasurement) {
        this.normalizedMeasurement = normalizedMeasurement;
    }

    /**
     * Calculates the BLUE value from RGB of the normalized measurement
     * @return the BLUE value
     */
    @Override
    public double getColorValue() {
        // Always return 50 - to add hue to all the colors.
        return 50;
    }
}