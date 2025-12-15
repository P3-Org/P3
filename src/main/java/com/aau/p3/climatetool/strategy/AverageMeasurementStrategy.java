package com.aau.p3.climatetool.strategy;

import com.aau.p3.climatetool.utilities.MeasurementStrategy;

import java.util.List;

/**
 * Class that implements "MeasurementStrategy" and finds the average measurement.
 */
public class AverageMeasurementStrategy implements MeasurementStrategy {

    /**
     * Finds the average measurement.
     * @param samples List of samples as doubles.
     * @return the average measurement value.
     */
    @Override
    public double processValues(List<Double> samples) {
        // Double::sum references to the method sum in Double Class using :: operator.
        double sum = samples.stream().reduce(0.0, Double::sum);

        // Divide by size to get average.
        return sum / samples.size();
    }
}
