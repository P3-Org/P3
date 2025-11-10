package com.aau.p3.climatetool.strategy;

import com.aau.p3.climatetool.utilities.MeasurementStrategy;

import java.util.List;

public class MinMeasurementStrategy implements MeasurementStrategy {
    @Override
    public double processValues(List<Double> samples) {
        return samples.stream().min(Double::compare).orElse(Double.NaN);
    }
}
