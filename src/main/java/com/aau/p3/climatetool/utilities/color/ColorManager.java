package com.aau.p3.climatetool.utilities.color;

public class ColorManager {

    /**
     * Calculates the RGB value for a given normalized measurement value
     * @param normalizedMeasurement The normalized measured value, to find the RGB value for
     * @return double array with 3 elements: Red value, green value, blue value
     */
    public static double[] getRGBValues(double normalizedMeasurement) {
        PolynomialInterface red = new RedPolynomial(normalizedMeasurement);
        PolynomialInterface green = new GreenPolynomial(normalizedMeasurement);
        PolynomialInterface blue = new BluePolynomial(normalizedMeasurement);

        System.out.println("Red: " + red.getColorValue() + " green: " + green.getColorValue() + " blue: " + blue.getColorValue());

        return new double[]{red.getColorValue(), green.getColorValue(), blue.getColorValue()};
    }
}