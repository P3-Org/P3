package com.aau.p3.climatetool.utilities;

import java.util.List;

public interface MeasurementStrategy {
    double processValues(List<Double> samples);
}