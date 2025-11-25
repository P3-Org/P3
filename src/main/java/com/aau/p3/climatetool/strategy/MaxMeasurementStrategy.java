package com.aau.p3.climatetool.strategy;

import com.aau.p3.climatetool.utilities.MeasurementStrategy;

import java.util.List;

/**
 * Class that implements "MeasurementStrategy" and finds the maximum measurement
 */
public class MaxMeasurementStrategy implements MeasurementStrategy {
    @Override
    public double processValues(List<Double> samples) {
        return samples.stream().max(Double::compare).orElse(Double.NaN);
    }
}
