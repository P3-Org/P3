package com.aau.p3.climatetool.utilities;

import java.util.List;

/**
 * Interface for how to measure values - adds processValues() method
 */
public interface MeasurementStrategy {
    double processValues(List<Double> samples);
}