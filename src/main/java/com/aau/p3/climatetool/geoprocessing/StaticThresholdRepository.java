package com.aau.p3.climatetool.geoprocessing;

import com.aau.p3.climatetool.utilities.ThresholdRepository;

public class StaticThresholdRepository implements ThresholdRepository {
    @Override
    public double[] getThreshold(String riskType) {
        // Switch that finds which risk that was sent in the parameters
        return new double[] {15.0, 30.0};
    }
}