package com.aau.p3.climatetool.utilities;

public interface ThresholdRepositoryInterface {
    double[] getThreshold(String riskType);
    void updateThreshold(String riskType, double min, double max);
}
