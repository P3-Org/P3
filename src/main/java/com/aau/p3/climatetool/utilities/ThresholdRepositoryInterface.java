package com.aau.p3.climatetool.utilities;

/**
 * Interface that implements a threshold repository and methods for getting and updating them.
 */
public interface ThresholdRepositoryInterface  {
    double[] getThreshold(String riskType);
    void updateThreshold(String riskType, double min, double max);
}