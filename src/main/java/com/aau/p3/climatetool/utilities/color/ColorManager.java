package com.aau.p3.climatetool.utilities.color;

public class ColorManager {

    /**
     * Calculates the RGB value for a given measurement value, between a given threshold
     * @param measurementValue The measured value, to find the RGB value for
     * @param thresholds A double array with 2 elements: First element is the lower - second element is the upper threshold
     * @return double array with 3 elements: Red value, green value, blue value
     */
    public static double[] getRGBValues(double measurementValue, double[] thresholds) {
        PolynomialInterface red = new RedPolynomial(NormalizeSample.minMaxNormalization(measurementValue, thresholds));
        PolynomialInterface green = new GreenPolynomial(NormalizeSample.minMaxNormalization(measurementValue, thresholds));
        PolynomialInterface blue = new BluePolynomial(NormalizeSample.minMaxNormalization(measurementValue, thresholds));

        return new double[]{red.getColorValue(), green.getColorValue(), blue.getColorValue()};
    }
}