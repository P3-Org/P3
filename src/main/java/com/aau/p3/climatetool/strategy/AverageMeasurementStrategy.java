package com.aau.p3.climatetool.strategy;

import com.aau.p3.climatetool.utilities.MeasurementStrategy;

import java.util.List;

/**
 * Class that implements "MeasurementStrategy" and finds the average measurement
 * @Author Batman
 */
public class AverageMeasurementStrategy implements MeasurementStrategy {
    @Override
    public double processValues(List<Double> samples) {
        double sum = samples.stream().reduce(0.0, Double::sum); // Double::sum references to the metho sum in Double Class using :: operator.
        return sum / samples.size();
    }
}
