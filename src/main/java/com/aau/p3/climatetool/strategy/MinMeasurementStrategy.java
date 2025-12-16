package com.aau.p3.climatetool.strategy;

import com.aau.p3.climatetool.utilities.MeasurementStrategy;

import java.util.List;

/**
 * Class that implements "MeasurementStrategy" and finds the minimum measurement.
 */
public class MinMeasurementStrategy implements MeasurementStrategy {

    /**
     * Finds the minimum measurement based on sample values.
     * @param samples List of samples as doubles.
     * @return the minimum measurement value.
     */
    @Override
    public double processValues(List<Double> samples) {
        return samples.stream().min(Double::compare).orElse(Double.NaN);
    }
}