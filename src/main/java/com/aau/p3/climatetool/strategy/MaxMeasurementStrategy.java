package com.aau.p3.climatetool.strategy;

import com.aau.p3.climatetool.utilities.MeasurementStrategy;

import java.util.List;

/**
 * Class that implements "MeasurementStrategy" and finds the maximum measurement
 */
public class MaxMeasurementStrategy implements MeasurementStrategy {

    /**
     * Finds the maximum value of the measurement within the polygon defining a property.
     * @param samples List of samples as doubles
     * @return the maximum measurement value
     */
    @Override
    public double processValues(List<Double> samples) {
        // Creates a stream from the list of samples. compares each element and return the largest
        return samples.stream().max(Double::compare).orElse(Double.NaN);
    }
}
