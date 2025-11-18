package com.aau.p3.climatetool.utilities.color;

public class NormalizeSample {
    /**
     * Min-max normalization. Maps the sample to a 0-1 range, based on the thresholds
     * @param measurementValue The measured value to normalize
     * @param thresholds A double array with 2 elements: First element is the lower - second element is the upper threshold
     * @return The min–max normalized measurement. The result is in the 0–1 interval when the measurement is
     * within the minThreshold,maxThreshold range. Otherwise, the result falls outside of this interval
     */
    public static double minMaxNormalization(double measurementValue, double[] thresholds) {
        if (thresholds.length != 2) {
            throw new IllegalArgumentException("max and min threshold must differ");
        }
        return (measurementValue - thresholds[0]) / (thresholds[1] - thresholds[0]);
    }
}