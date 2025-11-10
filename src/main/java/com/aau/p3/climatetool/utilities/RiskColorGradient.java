package com.aau.p3.climatetool.utilities;

public class RiskColorGradient {
    /**
     * Method that is responsible for returning the correct colors between the threshold values from the database and the measurement value it receives.
     * @param measurementValue The value calculated from each Tiff file after having applied a measurement strategy
     * @param thresholds double array containing two values. 1st index: when does the color switch to yellow 2nd index: when does to switch to green
     * @return returns a double array specifying an RGB value.
     */
    public static double[] assignColors(double measurementValue, double[] thresholds) {
        /* Case 1: RED to YELLOW */
        if (measurementValue <= thresholds[0]) {
            double green = measurementValue / thresholds[0] * 255;
            return new double[] {255, green, 0};
        /* Case 2: YELLOW to GREEN */
        } else if (measurementValue > thresholds[0] && measurementValue <= thresholds[1]) {
            double red = mapRange(measurementValue, thresholds[0], thresholds[1]);
            return new double[] {red, 255, 0};
        /* Case 3: GREEN */    
        } else {
            return new double[] {0, 255, 0};
        }
    }

    /**
     * Helper method for the else-if case in {@code assignColors()}. It is used as the threshold corresponding to the climate risks
     * aren't in the same interval. Example: Cloudburst {10, 20} threshold and coastal erosion {2, 30}
     * @param value corresponds to the measurement value that {@code assignColors()} receives
     * @param thresMin from the example thresMin = 2
     * @param thresMax from the example thresMax = 30
     * @return
     */
    private static double mapRange(double value, double thresMin, double thresMax) {
        return (value - thresMin) * ((double) 0 - (double) 255) / (thresMax - thresMin) + (double) 255;
    }
}