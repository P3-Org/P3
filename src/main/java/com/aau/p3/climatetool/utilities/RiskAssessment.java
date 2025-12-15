package com.aau.p3.climatetool.utilities;

/**
 * Interface that allows for each risk to get handled appropriately, as they are formated different
 */
public interface RiskAssessment {
    String getRiskType();
    double getNormalizedValue();
    double getMeasurementValue();
    void setDescription();
    String getDescription();
    double[] getRGB();
    double[] getThresholds();
    void computeRiskMetrics(double[][] coordinates);
}